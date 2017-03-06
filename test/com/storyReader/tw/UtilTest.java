package com.storyReader.tw;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testAppend() {
		String r = Util.append("aaa", "bbb", "ccc");
		assertEquals("aaabbbccc", r);
		
		r = Util.append("aaa", ", ", "ccc");
		assertEquals("aaa, ccc", r);
	}

	@Test
	public void testAppendWithSeparator() {
		String r = Util.append(',', "aaa", "bbb", "ccc");
		assertEquals("aaa,bbb,ccc", r);
		
		r = Util.append(' ', "aaa", "bbb", "ccc");
		assertEquals("aaa bbb ccc", r);
	}
}
