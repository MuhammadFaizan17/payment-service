package com.rak.payment.util;

import com.rak.payment.enums.CardScheme;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@UtilityClass
public class Utility {

    private Integer iteration = 1;

    public static long generateRandom12DigitNumber() {
        iteration = iteration + 1;
        Random random = new Random();
        return 1_000_000_000_000_000L + (long) (random.nextDouble() * 9_000_000_000_000L);
    }

    private static String formatAsUuid(long number) {
        String numberString = String.valueOf(number);
        return String.format(
                "%s-%s-%s-%s",
                numberString.substring(0, 4),
                numberString.substring(4, 8),
                numberString.substring(8, 12),
                numberString.substring(12)
        );
    }

    public static String generateFormattedRandomNumber() {
        long random12DigitNumber = generateRandom12DigitNumber();
        return formatAsUuid(random12DigitNumber);
    }

    public static String detectCardType(String cardNumber) {
        String visaRegex = "^4[0-9]{6,}$";
        String mastercardRegex = "^5[1-5][0-9]{5,}|222[1-9][0-9]{3,}|22[3-9][0-9]{4,}|2[3-6][0-9]{5,}|27[01][0-9]{4,}|2720[0-9]{3,}$";
        String amexRegex = "^3[47][0-9]{5,}$";
        String dinersRegex = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";

        Pattern visaPattern = Pattern.compile(visaRegex);
        Matcher visaMatcher = visaPattern.matcher(cardNumber);

        Pattern mastercardPattern = Pattern.compile(mastercardRegex);
        Matcher mastercardMatcher = mastercardPattern.matcher(cardNumber);

        Pattern amexPattern = Pattern.compile(amexRegex);
        Matcher amexMatcher = amexPattern.matcher(cardNumber);

        Pattern dinersPattern = Pattern.compile(dinersRegex);
        Matcher dinersMatcher = dinersPattern.matcher(cardNumber);

        if (visaMatcher.matches()) {
            return "Visa";
        } else if (mastercardMatcher.matches()) {
            return "Mastercard";
        } else if (amexMatcher.matches()) {
            return "American Express";
        } else if (dinersMatcher.matches()) {
            return "Diners Club";
        } else {
            return "Unknown";
        }
    }

    public static boolean areAllFieldsNull(Object obj) {
        if (obj == null) {
            return true;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                if (field.get(obj) != null) {
                    return false; // If any field is not null, return false
                }
            } catch (IllegalAccessException e) {
                // Handle exception if needed
                e.printStackTrace();
            }
        }

        return true; // All fields are null
    }

    public static CardScheme getCardType() {
        if (iteration % 2 == 0)
            return CardScheme.MASTERCARD;
        return CardScheme.VISA;

    }

    public static String formatCardNumber(String cardNumber) {
        // Remove any existing hyphens or spaces
        String cleanedNumber = cardNumber.replaceAll("[\\s-]+", "");

        // Check if the cleaned number is a valid 16-digit number
        if (cleanedNumber.length() != 16 || !cleanedNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid card number format");
        }

        // Insert hyphens after every 4 digits
        StringBuilder formattedNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if (i > 0 && i % 4 == 0) {
                formattedNumber.append("-");
            }
            formattedNumber.append(cleanedNumber.charAt(i));
        }

        return formattedNumber.toString();
    }

}
