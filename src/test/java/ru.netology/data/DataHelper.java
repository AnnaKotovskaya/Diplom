package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    private DataHelper() {
    }


    public static String getValidCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getInValidCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getValidCardStatus() {
        return "APPROVED";
    }

    public static String getInValidCardStatus() {
        return "DECLINED";
    }

    public static String getRandomCardNumber() {
        return fakerEn.business().creditCardNumber();
    }


    public static String getClearCardNumber() {
        return "";
    }


    public static String get15DigitsCardNumber() {
        return "4444 4444 4444 444";
    }

    public static String getZeroCardNumber() {
        return "0000 0000 0000 0000";
    }


    public static String get1DigitCardNumber() {
        return fakerEn.number().digits(1);
    }


    public static String getSpecialCharactersCardNumber() {
        return "#$@^&()!!?";
    }

    public static String getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return validMonth;
    }


    public static String getClearMonth() {
        return "";
    }

    public static String getInvalidFormatMonth() {
        return fakerEn.number().digit();
    }


    public static String getMore12Month() {
        return "13";
    }

    public static String getZeroMonth() {
        return "00";
    }


    public static String getSpecialCharactersMonth() {
        return "!)";
    }

    public static String getValidYear() {
        String validYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return validYear;
    }


    public static String getClearYear() {
        return "";
    }

    public static String getInvalidFormatYear() {
        return fakerEn.number().digit();
    }


    public static String getSpecialCharactersYear() {
        return "%^";
    }

    public static String getValidOwner() {
        return fakerEn.name().firstName() + " " + fakerEn.name().lastName();
    }


    public static String getClearOwner() {

        return "";
    }


    public static String getCyrillicOwner() {
        return fakerRu.name().fullName();
    }


    public static String getDigitsOwner() {
        return fakerEn.number().digits(9);
    }


    public static String getSpecialCharactersOwner() {
        return "^*!$#";
    }

    public static String getValidCvc() {
        return fakerEn.number().digits(3);
    }

    public static String getClearCvc() {
        return "";
    }

    public static String getZeroCvc() {
        return "000";
    }


    public static String getInvalidFormatCvc() {
        return fakerEn.number().digits(1);
    }

    public static String getSpecialCharactersCvc() {
        return "^*#";
    }

}

