package com.rak.payment.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static java.util.Arrays.stream;

@Getter
@NoArgsConstructor
public enum CardScheme {
    MASTERCARD("MASTERCARD", "https://www.transparentpng.com/thumb/mastercard/mastercard-png-8.png"),
    VISA("VISA", "https://www.visa.com.au/dam/VCOM/regional/ve/romania/blogs/hero-image/visa-logo-800x450.jpg"),
    AMERICAN_EXPRESS("American Express", "https://w7.pngwing.com/pngs/382/146/png-transparent-american-express-logo-icons-logos-emojis-iconic-brands.png"),
    DINERS_CLUB("Diners Club", "https://w7.pngwing.com/pngs/60/780/png-transparent-diners-club-international-hd-logo.png");
    String scheme;
    String logoURL;

    CardScheme(String scheme, String logoURL) {
        this.scheme = scheme;
        this.logoURL = logoURL;
    }

    public static boolean isValid(String card) {
        return stream(values()).anyMatch(type -> type.getScheme().equalsIgnoreCase(card));
    }

    public static CardScheme getSchemeByString(String cardType) {
        for (CardScheme type : CardScheme.values()) {
            if (type.scheme.trim().equalsIgnoreCase(cardType)) {
                return type;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid cardType");
    }


}

