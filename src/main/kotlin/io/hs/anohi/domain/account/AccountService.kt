package io.hs.anohi.domain.account

import com.google.firebase.auth.FirebaseAuth
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.ConflictException
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.domain.account.payload.AccountDetail
import io.hs.anohi.domain.account.payload.AccountUpdateForm
import io.hs.anohi.domain.auth.RoleRepository
import io.hs.anohi.domain.auth.constant.RoleName
import io.hs.anohi.domain.post.repository.FavoritePostRepository
import io.hs.anohi.domain.post.repository.PostRepository
import io.hs.anohi.infra.security.CustomUserDetailsService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
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
    fun create(
        email: String,
        name: String?,
        pictureUrl: String?
    ) {
        val account = Account.from(name = name, email = email, profileImagePath = pictureUrl)

        val role = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ROLE) }

        account.addRole(role)
        accountRepository.save(account)
    }

    @Transactional
    fun create(token: String): Account {
        val decodedToken = this.firebaseAuth.verifyIdToken(token.split(" ")[1]);

        println(decodedToken);

        val existsEmail = accountRepository.existsByUid(decodedToken.uid)
        if (existsEmail) {
            throw ConflictException(ErrorCode.CONFLICT_EMAIL)
        }

        val account = Account.from(email = decodedToken.email, name = decodedToken.name, profileImagePath = decodedToken.picture)

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

    fun existsByUid(uid: String): Boolean {
        return accountRepository.existsByUid(uid)
    }

}