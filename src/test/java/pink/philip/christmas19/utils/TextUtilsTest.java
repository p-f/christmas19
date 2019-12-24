package pink.philip.christmas19.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextUtilsTest {

    @Test
    public void testBoxed() {
        assertEquals("@@@@@@@@#@      @#@ test @#@      @#@@@@@@@@",
                TextUtils.boxed("test", '@', "#"));
        assertEquals("@@@@#@  @#@  @#@  @#@@@@", TextUtils.boxed("", '@', "#"));
    }
}
