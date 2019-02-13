package com.fullsail.android.unittestingstarter;

import com.fullsail.android.unittestingstarter.util.PersonFormatUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PersonFormatTests {

    @Test
    public void formatName(){
        assertEquals(PersonFormatUtil.formatName(PersonFormatUtil.FORMAT_FIRST_LAST, "John", "Doe"), "John Doe");
        assertEquals(PersonFormatUtil.formatName(PersonFormatUtil.FORMAT_FIRST_LAST, "David", "Smith"), "David Smith");

        assertEquals(PersonFormatUtil.formatName(PersonFormatUtil.FORMAT_LAST_FIRST, "John", "Doe"), "Doe, John");
        assertEquals(PersonFormatUtil.formatName(PersonFormatUtil.FORMAT_LAST_FIRST, "David", "Smith"), "Smith, David");
    }


    @Test
    public void formatPhoneNumber(){
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_ALL_DASHES, "4075550123"), "407-555-0123");
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_ALL_DASHES, "407 555 0123"), "407-555-0123");
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_ALL_DASHES, "(407)555-0123"), "407-555-0123");
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_ALL_DASHES, "407-555-0123"), "407-555-0123");

        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_WITH_PARENS, "4075550123"), "(407)555-0123");
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_WITH_PARENS, "407 555 0123"), "(407)555-0123");
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_WITH_PARENS, "(407)555-0123"), "(407)555-0123");
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_WITH_PARENS, "407-555-0123"), "(407)555-0123");

        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_WITH_SPACES, "4075550123"), "407 555 0123");
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_WITH_SPACES, "407 555 0123"), "407 555 0123");
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_WITH_SPACES, "(407)555-0123"), "407 555 0123");
        assertEquals(PersonFormatUtil.formatPhoneNumber(PersonFormatUtil.FORMAT_WITH_SPACES, "407-555-0123"), "407 555 0123");
    }

    @Test
    public void unformatPhoneNumber(){
        assertEquals(PersonFormatUtil.unformatPhoneNumber("4075550123"), "4075550123");
        assertEquals(PersonFormatUtil.unformatPhoneNumber("407 555 0123"), "4075550123");
        assertEquals(PersonFormatUtil.unformatPhoneNumber("(407)555-0123"), "4075550123");
        assertEquals(PersonFormatUtil.unformatPhoneNumber("407-555-0123"), "4075550123");
    }

    @Test
    public void isPhoneNumberValid(){
        assertTrue(PersonFormatUtil.isPhoneNumberValid("4075550123"));
        assertTrue(PersonFormatUtil.isPhoneNumberValid("407 555 0123"));
        assertTrue(PersonFormatUtil.isPhoneNumberValid("(407)555-0123"));
        assertTrue(PersonFormatUtil.isPhoneNumberValid("407-555-0123"));

        assertFalse(PersonFormatUtil.isPhoneNumberValid("1-407-555-0123"));
        assertFalse(PersonFormatUtil.isPhoneNumberValid("40755501234"));
        assertFalse(PersonFormatUtil.isPhoneNumberValid("407555012"));
    }

}