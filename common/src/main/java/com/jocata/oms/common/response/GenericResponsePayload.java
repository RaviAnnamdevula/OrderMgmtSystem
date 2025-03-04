package com.jocata.oms.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponsePayload<T> {
    private String requestId;
    private String timeStamp;
    private Integer statusCode;
    private String statusMessage;
    T data;
}
