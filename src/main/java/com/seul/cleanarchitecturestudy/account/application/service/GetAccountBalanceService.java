package com.seul.cleanarchitecturestudy.account.application.service;

import com.seul.cleanarchitecturestudy.account.application.port.in.GetAccountBalanceQuery;
import com.seul.cleanarchitecturestudy.account.application.port.out.LoadAccountPort;
import com.seul.cleanarchitecturestudy.account.domain.AccountId;
import com.seul.cleanarchitecturestudy.account.domain.Money;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceQuery {
    private final LoadAccountPort loadAccountPort;

    @Override
    public Money getAccountBalance(AccountId accountId) {
        return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
                .calculateBalance();
    }
}
