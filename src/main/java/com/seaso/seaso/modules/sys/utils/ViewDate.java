package com.seaso.seaso.modules.sys.utils;

import com.seaso.seaso.common.utils.encryption.EncryptData;

import java.util.Date;

public class ViewDate extends EncryptData {

    public ViewDate() {
        createDate = new Date();
    }

    @Override
    public void decode(String string) {
        createDate = new Date(Long.parseLong(string));
    }

    @Override
    public String encrypt() {
        return this.toString();
    }

    @Override
    public String toString() {
        return Long.toString(createDate.getTime());
    }
}
