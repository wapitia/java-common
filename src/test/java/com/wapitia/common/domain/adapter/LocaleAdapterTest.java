package com.wapitia.common.domain.adapter;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class LocaleAdapterTest {

    /** Test the locale name truncation of language tags according
     *  to the specification
     *  <a href="https://tools.ietf.org/html/bcp47#section-4.4.2">bcp47</a>
     *        Tag to truncate:
     *          0. zh-Latn-CN-variant1-a-extend1-x-wadegile-private1
     *          1. zh-Latn-CN-variant1-a-extend1-x-wadegile
     *          2. zh-Latn-CN-variant1-a-extend1
     *          3. zh-Latn-CN-variant1
     *          4. zh-Latn-CN
     *          5. zh-Latn
     *          6. zh
     */
    @Test
    public void testTruncation() {
        final String tag = "zh-Latn-CN-variant1-a-extend1-x-wadegile-private1";
        assertTrunc("zh-Latn-CN-variant1-a-extend1-x-wadegile-private1", 49, 55, tag);
        assertTrunc("zh-Latn-CN-variant1-a-extend1-x-wadegile", 40, 49, tag);
        assertTrunc("zh-Latn-CN-variant1-a-extend1", 29, 40, tag);
        assertTrunc("zh-Latn-CN-variant1", 19, 29, tag);
        assertTrunc("zh-Latn-CN", 10, 19, tag);
        assertTrunc("zh-Latn", 7, 10, tag);
        assertTrunc("zh", 2, 7, tag);
        assertTrunc("z", 1, 2, tag);
    }

    void assertTrunc(String expected, int from, int to, String tag) {
        Function<Integer,String> trunc = (maxLength) -> LocaleAdapter.trunc(tag, maxLength);
        for (int len = from; len < to; len++)
            Assert.assertEquals(expected, trunc.apply(len));
    }
}
