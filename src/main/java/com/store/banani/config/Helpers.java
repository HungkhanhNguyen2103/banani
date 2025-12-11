package com.store.banani.config;

import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.Locale;

public class Helpers {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();

    public static String generateId() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static String convertMoney(float amount){
        Locale localeVN = new Locale("vi", "VN");

        // Format tiền tệ
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String formattedAmount = currencyVN.format(amount);
        return formattedAmount;
    }

    public static String convertMoney(double amount){
        Locale localeVN = new Locale("vi", "VN");

        // Format tiền tệ
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String formattedAmount = currencyVN.format(amount);
        return formattedAmount;
    }
}
