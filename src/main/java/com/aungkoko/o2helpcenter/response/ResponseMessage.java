package com.aungkoko.o2helpcenter.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage<T> {

    private boolean con;
    private String message;
    private T info;

    public ResponseMessage(boolean con,String message, T info) {
        this.con = con;
        this.message = message;
        this.info = info;
    }
}
