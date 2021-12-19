package com.seul.cleanarchitecturestudy.account.adapter.out.persistence;

import com.seul.cleanarchitecturestudy.account.application.port.out.LoadAccountPort;
import com.seul.cleanarchitecturestudy.account.application.port.out.UpdateAccountStatePort;
import com.seul.cleanarchitecturestudy.account.domain.Account;
import com.seul.cleanarchitecturestudy.account.domain.AccountId;
import com.seul.cleanarchitecturestudy.account.domain.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {
        AccountJpaEntity account = accountRepository.findById(accountId.getValue())
                .orElseThrow(EntityExistsException::new);

        List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(
                accountId.getValue(),
                baselineDate);

        Long withdrawalBalance = orZero(activityRepository.getWithdrawalBalanceUntil(
                accountId.getValue(),
                baselineDate));

        Long depositBalance = orZero(activityRepository.getDepositBalanceUntil(
                accountId.getValue(),
                baselineDate));

        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance);
    }

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }

    @Override
    public void updateActivities(Account account) {
        for (Activity activity : account.getActivityWindow().getActivities()) {
            if (activity.getId() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }
}
