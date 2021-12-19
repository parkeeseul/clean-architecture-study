package com.seul.cleanarchitecturestudy.account.application.port.out;

import com.seul.cleanarchitecturestudy.account.domain.AccountId;

public interface AccountLock {

    void lockAccount(AccountId sourceAccountId);

    void releaseAccount(AccountId accountId);
}
