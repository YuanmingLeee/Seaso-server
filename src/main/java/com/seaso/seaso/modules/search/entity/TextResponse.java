package com.seaso.seaso.modules.search.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.seaso.seaso.modules.sys.utils.JsonResponseBody;

public class TextResponse {

    private static final TypeReference<JsonResponseBody<TextResponse>> TextResponseType =
            new TypeReference<JsonResponseBody<TextResponse>>() {
            };
    private String description;

    public static TypeReference<JsonResponseBody<TextResponse>> getTextResponseType() {
        return TextResponseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
