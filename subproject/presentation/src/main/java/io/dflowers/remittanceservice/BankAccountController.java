package io.dflowers.remittanceservice;

import io.dflowers.remittanceservice.domain.BankAccount;
import io.dflowers.remittanceservice.service.bank.FindBankById;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {
    private final FindBankById findBankById;

    public BankAccountController(FindBankById findBankById) {
        this.findBankById = findBankById;
    }

    @GetMapping("/accounts")
    BankAccount getAccounts() {
        return findBankById.apply(5L);
    }
}
