package com.rangedbattle.firebasechat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the method that tests the messages
 */
public class PGTest {

    private InstantMessage tester;
    public String input;

    @Before
    public void setUp() throws Exception {
        tester = new InstantMessage();
    }

    @Test
    public void testBadWordCheck1() {
        assertEquals("*@#$*ing", tester.badWordCheck("Fucking"));
    }

    @Test
    public void testBadWordCheck2() {
        assertEquals("*@#$*", tester.badWordCheck("asshole"));
    }

    @Test
    public void testBadWordCheck3() {
        assertEquals("don't be an*@#$*jerry!", tester.badWordCheck("Don't be an ass Jerry!"));
    }

    @Test
    public void testBadWordCheck4() {
        assertEquals("assume", tester.badWordCheck("assume"));
    }

    @Test
    public void testBadWordCheck5() {
        assertEquals("*@#$*in stupid *@#$*", tester.badWordCheck("fuckin stupid bitch"));
    }
}