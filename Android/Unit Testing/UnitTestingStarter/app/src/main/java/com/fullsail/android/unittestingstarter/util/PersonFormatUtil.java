package com.fullsail.android.unittestingstarter.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class PersonFormatUtil {

    @Retention(SOURCE)
    @IntDef({FORMAT_FIRST_LAST, FORMAT_LAST_FIRST})
    public @interface NameFormat {}

    public static final int FORMAT_FIRST_LAST = 0x01010;
    public static final int FORMAT_LAST_FIRST = 0x01011;

    @Retention(SOURCE)
    @IntDef({FORMAT_ALL_DASHES, FORMAT_WITH_PARENS, FORMAT_WITH_SPACES})
    public @interface PhoneFormat {}
    public static final int FORMAT_ALL_DASHES = 0x02010;
    public static final int FORMAT_WITH_PARENS = 0x02011;
    public static final int FORMAT_WITH_SPACES = 0x02012;


    public static String formatName(@NameFormat int _format, String _firstName, String _lastName) {

        if(_format == FORMAT_FIRST_LAST) {
            // TODO: Format person name as "FirstName LastName"
            return _firstName + " " + _lastName;
        } else if(_format == FORMAT_LAST_FIRST) {
            // TODO: Format person name as "LastName, FirstName"
            return _lastName + ", " + _firstName;
        }

        // TODO: Change this to match formatting.
        return _firstName + " " + _lastName;
    }

    public static String formatPhoneNumber(@PhoneFormat int _format, String _phone) {

        String removeSpaces = _phone.replace(" ", "");
        String removeleftparentheses = removeSpaces.replace("(", "");
        String removerightparentheses = removeleftparentheses.replace(")", "");
        String newPhone = removerightparentheses.replace("-", "");

        switch (_format) {
            case FORMAT_ALL_DASHES: {
                // TODO: Format phone number as 407-555-0123
                StringBuilder sb = new StringBuilder(newPhone)
                        .insert(3, "-")
                        .insert(7, "-");
                String output = sb.toString();
                return output;
            }
            case FORMAT_WITH_PARENS: {
                // TODO: Format phone number as (407)555-0123
                StringBuilder sb = new StringBuilder(newPhone)
                        .insert(0, "(")
                        .insert(4, ")")
                        .insert(8, "-");
                String output = sb.toString();
                return output;
            }
            case FORMAT_WITH_SPACES: {
                // TODO: Format phone number as 407 555 0123
                StringBuilder sb = new StringBuilder(newPhone)
                        .insert(3, " ")
                        .insert(7, " ");
                String output = sb.toString();
                return output;
            }
        }

        // TODO: Change this to match formatting
        return _phone;
    }

    public static String unformatPhoneNumber(String _phone) {

        // TODO: Convert any phone number string into contiguous format. (i.e. 4075550123)
        String removeSpaces = _phone.replace(" ", "");
        String removeleftparentheses = removeSpaces.replace("(", "");
        String removerightparentheses = removeleftparentheses.replace(")", "");
        String removedashes = removerightparentheses.replace("-", "");

        return removedashes;
    }

    public static boolean isPhoneNumberValid(String _phone) {

        // TODO: Determine if the entered number is a valid US phone number. (i.e. 4075550123)
        // TODO: Assume numbers can be passed in with any format.

        String removeSpaces = _phone.replace(" ", "");
        String removeleftparentheses = removeSpaces.replace("(", "");
        String removerightparentheses = removeleftparentheses.replace(")", "");
        String removedashes = removerightparentheses.replace("-", "");
        char[] plainPhone = removedashes.toCharArray();

        if(plainPhone.length != 10){
            return false;
        }

        return true;
    }
}
