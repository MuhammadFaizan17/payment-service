package com.rak.payment.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Arrays.stream;

@Getter
@NoArgsConstructor
public enum CardScheme {
    MASTER_CARD("MASTER CARD", ""),
    VISA("VISA", "");

    String scheme;
    String logoURL;

    CardScheme(String scheme, String logoURL) {
        this.scheme = scheme;
        this.logoURL = logoURL;
    }

    public static boolean isValid(String grade) {
        return stream(values()).anyMatch(type -> type.getScheme().equalsIgnoreCase(grade));
    }

}

