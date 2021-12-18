package com.seul.cleanarchitecturestudy.account.adapter.in.web;

import com.seul.cleanarchitecturestudy.account.application.port.in.SendMoneyCommand;
import com.seul.cleanarchitecturestudy.account.application.port.in.SendMoneyUseCase;
import com.seul.cleanarchitecturestudy.account.domain.AccountId;
import com.seul.cleanarchitecturestudy.account.domain.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    public void sendMoney(@PathVariable Long sourceAccountId,
                          @PathVariable Long targetAccountId,
                          @PathVariable Long amount) {

        SendMoneyCommand command = new SendMoneyCommand(new AccountId(sourceAccountId),
                new AccountId(targetAccountId),
                Money.of(amount));

        sendMoneyUseCase.sendMoney(command);
    }
}
