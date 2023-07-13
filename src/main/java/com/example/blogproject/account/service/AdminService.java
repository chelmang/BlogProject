package com.example.blogproject.account.service;

import com.example.blogproject.account.dto.AccountReqDto;
import com.example.blogproject.account.entity.Account;
import com.example.blogproject.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class AdminService {
    private final AccountRepository accountRepository;

    public List<AccountReqDto> getAllUsers() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountReqDto> accountDtos = new ArrayList<>(accounts.size());
        for(Account account: accounts){
            accountDtos.add(new AccountReqDto(account));
        }
        return accountDtos;
    }

    public AccountReqDto getAccountDtoByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                ()->new RuntimeException("Invalid accountId: "+accountId)
        );
        return new AccountReqDto(account);
    }

    public void assignAdminRole(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                ()->new RuntimeException("Invalid accountId: "+accountId)
        );
    }
}
