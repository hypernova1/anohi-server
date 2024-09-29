package io.hs.anohi.account.application

import com.google.firebase.auth.FirebaseAuth
import io.hs.anohi.account.application.payload.AccountDetail
import io.hs.anohi.account.application.payload.AccountUpdateForm
import io.hs.anohi.account.domain.Account
import io.hs.anohi.account.domain.AccountRepository
import io.hs.anohi.account.domain.RoleName
import io.hs.anohi.account.domain.RoleRepository
import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.ConflictException
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.infra.firebase.FirebaseDecoder
import io.hs.anohi.post.application.PostService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val roleRepository: RoleRepository,
    private val postService: PostService,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDecoder: FirebaseDecoder
) {

    @Transactional
    fun create(token: String): Account {
        val firebaseUser = firebaseDecoder.parseToken(token)
        val existsUid = accountRepository.existsByUid(firebaseUser.uid)
        if (existsUid) {
            throw ConflictException(ErrorCode.CONFLICT_UID)
        }

        val role = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ROLE) }

        val account = Account.create(firebaseUser, role)

        return this.accountRepository.save(account)
    }

    @Transactional
    fun findById(id: Long, isVisit: Boolean = false): AccountDetail {
        val account =
            accountRepository.findByIdAndDeletedAtIsNull(id) ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT)

        account.increaseVisitor()

        val numberOfPosts = this.postService.count(account.id)
        val numberOfLikes = this.postService.countLikePost(account.id)
        return AccountDetail(account, numberOfPosts, numberOfLikes)
    }

    @Transactional
    fun delete(accountId: Long) {
        val account = this.accountRepository.findById(accountId) ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT)
        accountRepository.delete(account)
        firebaseAuth.deleteUser(account.uid)
    }

    @Transactional
    fun update(accountId: Long, updateForm: AccountUpdateForm) {
        val account =
            this.accountRepository.findById(accountId) ?: throw NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT)
        account.update(updateForm)
        this.accountRepository.save(account)
    }

    fun findOne(id: Long): Account? {
        return this.accountRepository.findById(id)
    }

}