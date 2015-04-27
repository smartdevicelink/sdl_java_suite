package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.Jingles;

import junit.framework.TestCase;

public class JinglesTests extends TestCase {
	
	public void testJingles () {
		String test = "POSTIVE_JINGLE";
		assertEquals("Positive jingle value does not match.", test, Jingles.POSTIVE_JINGLE);		
		test = "NEGATIVE_JINGLE";
		assertEquals("Negative jingle value does not match.", test, Jingles.NEGATIVE_JINGLE);		
		test = "INITIAL_JINGLE";
		assertEquals("Initial jingle value does not match.", test, Jingles.INITIAL_JINGLE);
		test = "LISTEN_JINGLE";
		assertEquals("Listen jingle value does not match.", test, Jingles.LISTEN_JINGLE);
		test = "HELP_JINGLE";
		assertEquals("Help jingle value does not match.", test, Jingles.HELP_JINGLE);
	}	
}