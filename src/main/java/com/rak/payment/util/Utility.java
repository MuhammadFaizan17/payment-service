package com.rak.payment.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.Random;


@UtilityClass
public class Utility {
    public static long generateRandom12DigitNumber() {
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

}
