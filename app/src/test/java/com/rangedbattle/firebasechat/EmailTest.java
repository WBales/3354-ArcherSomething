package com.rangedbattle.firebasechat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailTest {

    private RegisterActivity tester;
    public String input;

    @Before
    public void setUp() throws Exception {
        tester = new RegisterActivity();
    }

    @Test
    public void testIsEmailValid1() {
        input = "john.doe@yahoo.com";
        assertEquals(true,tester.isEmailValid(input));
    }

    @Test
    public void testIsEmailValid2() {
        input = "john.doe@yahoo.cOm";
        assertEquals(true,tester.isEmailValid(input));
    }
    @Test
    public void testIsEmailValid3() {
        input = "john@doe@yahoo.cOm";
        assertEquals(false,tester.isEmailValid(input));
    }
    @Test
    public void testIsEmailValid4() {
        input = "john.doe@yahoo.cm";
        assertEquals(false,tester.isEmailValid(input));
    }
    @Test
    public void testIsEmailValid5() {
        input = "RussianGuy@yahoo.rus";
        assertEquals(false,tester.isEmailValid(input));
    }
    @Test
    public void testIsEmailValid6() {
        input = "a@b.com";
        assertEquals(true,tester.isEmailValid(input));
    }
}