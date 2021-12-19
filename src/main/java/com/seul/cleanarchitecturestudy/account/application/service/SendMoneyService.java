package com.seul.cleanarchitecturestudy.account.application.service;

import com.seul.cleanarchitecturestudy.account.application.port.in.SendMoneyCommand;
import com.seul.cleanarchitecturestudy.account.application.port.in.SendMoneyUseCase;
import com.seul.cleanarchitecturestudy.account.application.port.out.AccountLock;
import com.seul.cleanarchitecturestudy.account.application.port.out.LoadAccountPort;
import com.seul.cleanarchitecturestudy.account.application.port.out.UpdateAccountStatePort;
import com.seul.cleanarchitecturestudy.account.domain.Account;
import com.seul.cleanarchitecturestudy.account.domain.AccountId;
import com.seul.cleanarchitecturestudy.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Component
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final MoneyTransferProperties moneyTransferProperties;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {

        checkThreshold(command);

        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId(), baselineDate);

        Account targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId(), baselineDate);

        AccountId sourceAccountId = sourceAccount.getId().orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
        AccountId targetAccountId = targetAccount.getId().orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);
        return true;
    }

    private void checkThreshold(SendMoneyCommand command) {
        if (command.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
            throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.getMoney());
        }
    }
}
