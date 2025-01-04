package io.dflowers.remittanceservice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AccountDateId implements Serializable {
    long accountId;
    LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDateId that = (AccountDateId) o;
        return accountId == that.accountId && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, date);
    }
}
