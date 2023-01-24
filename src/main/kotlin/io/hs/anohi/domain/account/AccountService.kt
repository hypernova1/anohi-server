package io.hs.anohi.domain.account

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.domain.account.payload.AccountDetail
import io.hs.anohi.domain.account.payload.AccountJoinForm
import io.hs.anohi.domain.account.payload.AccountUpdateForm
import io.hs.anohi.domain.auth.constant.RoleName
import io.hs.anohi.domain.auth.RoleRepository
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.core.exception.ConflictException
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
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun create(accountJoinForm: AccountJoinForm): Account {
        val existsEmail = accountRepository.existsByEmail(accountJoinForm.email)
        if (existsEmail) {
            throw ConflictException(ErrorCode.CONFLICT_EMAIL)
        }

        accountJoinForm.password = passwordEncoder.encode(accountJoinForm.password)

        val account = Account.from(accountJoinForm)

        val role = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ROLE) }

        account.addRole(role)

        return this.accountRepository.save(account)
    }


    fun findAll(page: Int, size: Int): List<Account> {
        val userList =
            accountRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "id")).content

        return userList
    }

    fun findById(id: Long): AccountDetail {

        val account = accountRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        return AccountDetail(account.id, account.email, account.name)
    }

    @Transactional
    fun update(id: Long, request: AccountUpdateForm) {
        val account = accountRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        account.password = passwordEncoder.encode(account.password)
        account.update(request)
    }

    @Transactional
    fun delete(id: Long) {
        val account = accountRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        accountRepository.delete(account)
    }

    fun existsEmail(email: String) {
        val existsAccount = accountRepository.existsByEmail(email)
        if (!existsAccount) {
            throw NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT)
        }
    }

}