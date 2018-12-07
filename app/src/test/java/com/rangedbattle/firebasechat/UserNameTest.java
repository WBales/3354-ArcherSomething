package com.rangedbattle.firebasechat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserNameTest {

    private RegisterActivity tester;
    //public String input;

    @Before
    public void setUp() throws Exception {
        tester = new RegisterActivity();
    }

    @Test
    public void testIsUserNameValid1() {
        assertEquals(false , tester.isUserNameValid("fff"));
    }

    @Test
    public void testIsUserNameValid2() {
        assertEquals(false , tester.isUserNameValid("Wesley1"));
    }

    @Test
    public void testIsUserNameValid3() {
        assertEquals(false , tester.isUserNameValid("1234567"));
    }

    @Test
    public void testIsUserNameValid4() {
        assertEquals(true , tester.isUserNameValid("Wesley"));
    }

    @Test
    public void testIsUserNameValid5() {
        assertEquals(false , tester.isUserNameValid("Wesley!!"));
    }
}