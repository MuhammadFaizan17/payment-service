package com.rak.payment.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Arrays.stream;

@Getter
@NoArgsConstructor
public enum CardScheme {
    MASTER_CARD("MASTER CARD", "https://www.transparentpng.com/thumb/mastercard/mastercard-png-8.png"),
    VISA("VISA", "https://www.visa.com.au/dam/VCOM/regional/ve/romania/blogs/hero-image/visa-logo-800x450.jpg");

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

