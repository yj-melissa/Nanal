package com.dbd.nanal.config.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class DefaultRes<T> {

    private int statusCode;
    private T data;

    public DefaultRes(final int statusCode) {
        this.statusCode = statusCode;
        this.data = null;
    }

    public static<T> DefaultRes<T> res(final int statusCode, final T t) {
        return DefaultRes.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .build();
    }


}
