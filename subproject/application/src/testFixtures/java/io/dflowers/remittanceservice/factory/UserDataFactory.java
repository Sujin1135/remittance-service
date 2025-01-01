package io.dflowers.remittanceservice.factory;

import io.dflowers.remittanceservice.domain.User;
import java.time.OffsetDateTime;

public class UserDataFactory {
    public static User generate(String name) {
        var now = OffsetDateTime.now();
        return new User(
            0,
            name,
            now,
            now,
            null
        );
    }
}
