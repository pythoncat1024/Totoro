package com.pythoncat.totoro;

import com.pythoncat.totoro.utils.DateUtil;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void transDate_isCorrect() throws Exception {
        assertEquals("aaa", DateUtil.transDate("2015-06-27"));
        //test ok [2015, 06, 27]
    }
}