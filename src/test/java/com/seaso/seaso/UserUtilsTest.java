package com.seaso.seaso;

import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

public class UserUtilsTest {
    @Test
    public void testUserUtilUserPreference() {
        String string = "123456,1553832000000;" +
                "654321,1553842000000";
        String string1 = "";

        Map<String, Date> map = UserUtils.decodeUserAnswerPreference(string);
        System.out.println(map);
        Assert.assertEquals(map.toString(), "{123456=Fri Mar 29 12:00:00 SRET 2019, 654321=Fri Mar 29 14:46:40 SRET 2019}");

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
