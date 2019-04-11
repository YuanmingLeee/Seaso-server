package com.seaso.seaso.common.utils.encryption;

import java.util.Date;

public abstract class EncryptData {

    protected Date createDate;

    public EncryptData() {
    }

    public abstract void decode(String string);

    public abstract String encrypt();
}
