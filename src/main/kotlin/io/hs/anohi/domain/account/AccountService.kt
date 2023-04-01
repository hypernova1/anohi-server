package io.hs.anohi.domain.account

import io.hs.anohi.core.ErrorCode
import io.hs.anohi.core.exception.ConflictException
import io.hs.anohi.core.exception.NotFoundException
import io.hs.anohi.domain.account.payload.AccountDetail
import io.hs.anohi.domain.account.payload.AccountJoinForm
import io.hs.anohi.domain.account.payload.AccountUpdateForm
import io.hs.anohi.domain.auth.RoleRepository
import io.hs.anohi.domain.auth.constant.RoleName
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
    fun create(
        email: String,
        password: String,
        name: String?,
        pictureUrl: String?
    ) {
        val hashedPassword = passwordEncoder.encode(password)
        val account = Account.from(name = name, email = email, password = hashedPassword, profileImagePath = pictureUrl)

        val role = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ROLE) }

        account.addRole(role)
        accountRepository.save(account)
    }

    @Transactional
    fun create(accountJoinForm: AccountJoinForm): Account {
        val existsEmail = accountRepository.existsByEmail(accountJoinForm.email)
        if (existsEmail) {
            throw ConflictException(ErrorCode.CONFLICT_EMAIL)
        }

        accountJoinForm.password = passwordEncoder.encode(accountJoinForm.password)

        val account = Account.from(email = accountJoinForm.email, password = accountJoinForm.password, name = accountJoinForm.name, profileImagePath = "")

        val role = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ROLE) }

        account.addRole(role)

        return this.accountRepository.save(account)
    }


    fun findAll(page: Int, size: Int): List<Account> {
        return accountRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "id")).content
    }

    fun findById(id: Long): AccountDetail {

        val account = accountRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        return AccountDetail(account.id, account.email, account.name)
    }

    @Transactional
    fun update(account: Account, request: AccountUpdateForm) {
        if (request.password != null) {
            account.password = passwordEncoder.encode(request.password)
        }
        account.update(request)
    }

    @Transactional
    fun delete(id: Long) {
        val account = accountRepository.findById(id)
            .orElseThrow { NotFoundException(ErrorCode.CANNOT_FOUND_ACCOUNT) }

        accountRepository.delete(account)
    }

    fun existsEmail(email: String): Boolean {
        return accountRepository.existsByEmail(email)
    }

}