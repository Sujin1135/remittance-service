package io.dflowers.remittanceservice;

import io.dflowers.remittanceservice.domain.Bank;
import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.service.bank.CreateBankAccount;
import io.dflowers.remittanceservice.service.bank.FindBankById;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {
    private final FindBankById findBankById;
    private final CreateBankAccount createBankAccount;

    public BankAccountController(
        FindBankById findBankById,
        CreateBankAccount createBankAccount
    ) {
        this.findBankById = findBankById;
        this.createBankAccount = createBankAccount;
    }

    @GetMapping("/accounts")
    BankAccount getAccounts() {
        return findBankById.apply(5L);
    }

    @PostMapping("/accounts")
    BankAccount createAccount() {
        return createBankAccount.apply(
            BankAccount.of(
                5,
                "일반예금",
                Bank.KAKAO,
                "756002-00-014380",
                BigDecimal.ZERO
            )
        );
    }
}
