package com.seaso.seaso;

import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

public class UtilTest {
    @Test
    public void testUserUtilUserPreference() {
        String string = "123456,03272019042400+0800;" +
                "654321,03272019172000+0800";
        String string1 = "";

        Map<String, Date> map = UserUtils.decodeUserAnswerPreference(string);
        System.out.println(map);
        Assert.assertEquals(map.toString(), "{123456=Wed Mar 27 04:24:00 SRET 2019, 654321=Wed Mar 27 17:20:00 SRET 2019}");

        String newString = UserUtils.encodeUserAnswerPreference(map);
        System.out.println(newString);
        Assert.assertEquals(newString, string);

        map = UserUtils.decodeUserAnswerPreference(string1);
        System.out.println(map);
        Assert.assertEquals(map.toString(), "{}");

        newString = UserUtils.encodeUserAnswerPreference(map);
        System.out.println(newString);
        Assert.assertEquals(newString, "");
    }
}
