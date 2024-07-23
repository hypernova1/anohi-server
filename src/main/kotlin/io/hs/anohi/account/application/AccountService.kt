package io.hs.anohi.account.application

import com.google.firebase.auth.FirebaseAuth
import io.hs.anohi.account.domain.*
import io.hs.anohi.account.ui.payload.AccountDetail
import io.hs.anohi.account.ui.payload.AccountUpdateForm
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.ConflictException
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.post.infra.FavoritePostJpaRepository
import io.hs.anohi.post.infra.PostJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val roleRepository: RoleRepository,
    private val postRepository: PostJpaRepository,
    private val favoritePostRepository: FavoritePostJpaRepository,
    private val firebaseAuth: FirebaseAuth,
) {

    @Transactional
    fun create(token: String): Account {
        val decodedToken = this.firebaseAuth.verifyIdToken(token)

        val existsUid = accountRepository.existsByUid(decodedToken.uid)
        if (existsUid) {
            throw ConflictException(ErrorCode.CONFLICT_UID)
        }

        val firebase = decodedToken.claims["firebase"] as Map<*, *>
        val identities = firebase["identities"] as Map<*, *>

        var loginType: SocialType = SocialType.NONE
        val identitiesKeys = identities.keys
        if (identitiesKeys.isNotEmpty()) {
            val socialType = identitiesKeys.iterator().next()
            if (socialType === "google.com") {
                loginType = SocialType.GOOGLE
            } else if (socialType === "apple.com") {
                loginType = SocialType.APPLE
            }

        }

        val account =
            Account.from(uid = decodedToken.uid ,email = decodedToken.email, loginType, name = decodedToken.name, profileImageUrl = decodedToken.picture)

        val role = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ROLE) }

        account.addRole(role)

        return this.accountRepository.save(account)
    }

    @Transactional
    fun findById(id: Long, isVisit: Boolean = false): AccountDetail {
        val account = accountRepository.findByIdAndDeletedAtIsNull(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        account.numberOfVisitors++

        val numberOfPosts = this.postRepository.countByAccount(account)
        val numberOfLikes = this.favoritePostRepository.countByAccount(account)
        return AccountDetail(account, numberOfPosts, numberOfLikes)
    }

    @Transactional
    fun delete(account: Account) {
        accountRepository.delete(account)
        firebaseAuth.deleteUser(account.uid)
    }

    @Transactional
    fun update(account: Account, updateForm: AccountUpdateForm) {
        account.update(updateForm)
        this.accountRepository.save(account)
    }

}