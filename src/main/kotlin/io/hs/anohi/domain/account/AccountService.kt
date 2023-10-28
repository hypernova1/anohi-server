package io.hs.anohi.domain.account

import com.google.firebase.auth.FirebaseAuth
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.ConflictException
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.domain.account.contants.LoginType
import io.hs.anohi.domain.account.payload.AccountDetail
import io.hs.anohi.domain.auth.RoleRepository
import io.hs.anohi.domain.auth.constant.RoleName
import io.hs.anohi.domain.post.repository.FavoritePostRepository
import io.hs.anohi.domain.post.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val roleRepository: RoleRepository,
    private val postRepository: PostRepository,
    private val favoritePostRepository: FavoritePostRepository,
    private val firebaseAuth: FirebaseAuth,
) {

    @Transactional
    fun create(token: String): Account {
        val decodedToken = this.firebaseAuth.verifyIdToken(token.split(" ")[1])

        val firebase = decodedToken.claims["firebase"] as Map<*, *>
        val identities = firebase["identities"] as Map<*, *>

        var loginType: LoginType = LoginType.NONE;
        val identitiesKeys = identities.keys
        if (identitiesKeys.isNotEmpty()) {
            val socialType = identitiesKeys.iterator().next()
            if (socialType === "google.com") {
                loginType = LoginType.GOOGLE
            } else if (socialType === "apple.com") {
                loginType = LoginType.APPLE
            }

        }

        val existsUid = accountRepository.existsByUid(decodedToken.uid)
        if (existsUid) {
            throw ConflictException(ErrorCode.CONFLICT_UID)
        }

        val account =
            Account.from(email = decodedToken.email, loginType, name = decodedToken.name, profileImagePath = decodedToken.picture)

        val role = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ROLE) }

        account.addRole(role)

        return this.accountRepository.save(account)
    }


    fun findAll(page: Int, size: Int): List<Account> {
        return accountRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "id")).content
    }

    @Transactional
    fun findById(id: Long, isVisit: Boolean = false): AccountDetail {
        val account = accountRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        account.numberOfVisitors++

        val numberOfPosts = this.postRepository.countByAccount(account)
        val numberOfLikes = this.favoritePostRepository.countByAccount(account);
        return AccountDetail(account, numberOfPosts, numberOfLikes)
    }

    @Transactional
    fun delete(account: Account) {
        accountRepository.delete(account)
    }

}