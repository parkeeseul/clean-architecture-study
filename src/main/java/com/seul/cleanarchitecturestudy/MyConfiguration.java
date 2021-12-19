package com.seul.cleanarchitecturestudy;

import com.seul.cleanarchitecturestudy.account.application.service.MoneyTransferProperties;
import com.seul.cleanarchitecturestudy.account.domain.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Value("${buckpal.transfer-threshold}")
    private long transferThreshold;

    @Bean
    public MoneyTransferProperties moneyTransferProperties() {
        return new MoneyTransferProperties(Money.of(transferThreshold));
    }
}
