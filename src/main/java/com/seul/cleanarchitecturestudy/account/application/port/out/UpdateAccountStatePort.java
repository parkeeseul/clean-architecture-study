package com.seul.cleanarchitecturestudy.account.application.port.out;

import com.seul.cleanarchitecturestudy.account.domain.Account;

public interface UpdateAccountStatePort {
    void updateActivities(Account account);
}
