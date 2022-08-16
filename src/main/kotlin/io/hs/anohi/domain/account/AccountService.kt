package io.hs.anohi.domain.account

import io.hs.anohi.domain.account.payload.AccountDetail
import io.hs.anohi.domain.account.payload.AccountJoinForm
import io.hs.anohi.domain.account.payload.AccountSummary
import io.hs.anohi.domain.account.payload.AccountUpdateForm
import io.hs.anohi.domain.auth.RoleName
import io.hs.anohi.domain.auth.RoleRepository
import io.hs.anohi.infra.exception.AccountNotFoundException
import io.hs.anohi.infra.exception.RoleNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Transactional
@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun create(accountJoinForm: AccountJoinForm) {
        accountJoinForm.password = passwordEncoder.encode(accountJoinForm.password)
        val account = Account.from(accountJoinForm)
        val role = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow { RoleNotFoundException() }
        account.setRole(role)

        this.accountRepository.save(account)
    }
    fun findAll(page: Int, size: Int): List<AccountSummary> {
        val userList =
            accountRepository.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "id")).content

        return userList.map { u -> AccountSummary(u.id, u.email, u.name) }
    }

    fun findById(id: Long): AccountDetail {

        val account = accountRepository.findById(id)
            .orElseThrow { AccountNotFoundException(id) }

        return AccountDetail(account.id, account.email, account.name)
    }

    @Transactional
    fun updateInfo(id: Long, request: AccountUpdateForm) {

        val account = accountRepository.findById(id)
            .orElseThrow { AccountNotFoundException(id) }

        account.password = passwordEncoder.encode(account.password)
        account.update(request)
    }

    fun delete(id: Long) {
        val account = accountRepository.findById(id)
            .orElseThrow { AccountNotFoundException(id) }

        accountRepository.delete(account)
    }

}