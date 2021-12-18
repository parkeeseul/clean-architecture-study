package com.seul.cleanarchitecturestudy.account.application.port.in;

import com.seul.cleanarchitecturestudy.account.domain.AccountId;
import com.seul.cleanarchitecturestudy.account.domain.Money;

public interface GetAccountBalanceQuery {

    Money getAccountBalance(AccountId accountId);
}
