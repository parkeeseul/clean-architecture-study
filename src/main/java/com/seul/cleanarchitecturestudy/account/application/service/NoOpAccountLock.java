package com.seul.cleanarchitecturestudy.account.application.service;

import com.seul.cleanarchitecturestudy.account.application.port.out.AccountLock;
import com.seul.cleanarchitecturestudy.account.domain.AccountId;
import org.springframework.stereotype.Component;

@Component
class NoOpAccountLock implements AccountLock {

    @Override
    public void lockAccount(AccountId accountId) {
        // do nothing
    }

    @Override
    public void releaseAccount(AccountId accountId) {
        // do nothing
    }

}
