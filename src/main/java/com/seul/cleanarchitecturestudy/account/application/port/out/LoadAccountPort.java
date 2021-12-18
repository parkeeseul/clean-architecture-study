package com.seul.cleanarchitecturestudy.account.application.port.out;

import com.seul.cleanarchitecturestudy.account.domain.Account;
import com.seul.cleanarchitecturestudy.account.domain.AccountId;

import java.time.LocalDateTime;

public interface LoadAccountPort {
    Account loadAccount(AccountId accountId, LocalDateTime now);
}
