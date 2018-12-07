package com.rangedbattle.firebasechat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordTest {

    private RegisterActivity tester;
    public String input;

    @Before
    public void setUp() throws Exception {
        tester = new RegisterActivity();
    }

    @Test
    public void testIsPasswordValid() {

        assertEquals(true, tester.isPasswordValid("Test!","Test!"));
    }
    @Test
    public void testIsPasswordValid2(){
        input = "t@est";
        assertEquals(true,tester.isPasswordValid(input,input));
    }
    @Test
    public void testIsPasswordValid3(){
        input= "tes#$";
        assertEquals(true,tester.isPasswordValid(input,input));
    }
    @Test
    public void testIsPasswordValid4(){
        input= "test";
        assertEquals(false,tester.isPasswordValid(input,input));
    }
    @Test
    public void testIsPasswordValid5(){
        input= "%^&";
        assertEquals(false,tester.isPasswordValid(input,input));
    }
}