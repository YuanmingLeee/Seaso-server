package com.seaso.seaso;

import com.seaso.seaso.modules.sys.utils.UserPreference;
import com.seaso.seaso.modules.sys.utils.UserUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class UserUtilsTest {
    @Test
    public void testUserUtilUserPreference() {
        String string = "123456:1553832000000,1;" +
                "654321:1553842000000,0";
        String string1 = "";

        Map<Long, UserPreference> map = UserUtils.decodeUserPreference(string);
        System.out.println(map);
        Assert.assertEquals("{123456=1553832000000,1, 654321=1553842000000,0}", map.toString());

        String newString = UserUtils.encryptUserPreference(map);
        System.out.println(newString);
        Assert.assertEquals(newString, string);

        map = UserUtils.decodeUserPreference(string1);
        System.out.println(map);
        Assert.assertEquals(map.toString(), "{}");

        newString = UserUtils.encryptUserPreference(map);
        System.out.println(newString);
        Assert.assertEquals(newString, "");
    }
}
