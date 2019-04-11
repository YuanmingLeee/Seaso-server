package com.seaso.seaso.modules.sys.utils;

import com.seaso.seaso.common.utils.encryption.EncryptData;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public final class UserPreference extends EncryptData {

    private boolean preference;

    public UserPreference() {
        super();
    }

    public UserPreference(boolean preference) {
        this.createDate = new Date();
        this.preference = preference;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getPreference() {
        return preference;
    }

    public void setPreference(Boolean preference) {
        this.preference = preference;
    }

    @Override
    public void decode(@NotNull String string) {
        String[] strings = string.split(",");
        assert strings.length == 2 : "Expecting 2 elements passed in, but got " + strings.length;

        String like = strings[1];
        assert like.equals("0") || like.equals("1") : "Expecting 2nd element be `0` or `1`, but got " + like;

        this.createDate = new Date(Long.parseLong(strings[0]));
        this.preference = like.equals("1");
    }

    @Override
    public String encrypt() {
        return this.toString();
    }

    @Override
    public String toString() {
        return createDate.getTime() + "," + (preference ? "1" : "0");
    }
}
