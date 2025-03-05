package com.jocata.oms.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GenericResponsePayload<T> {
    private String requestId;
    private String timeStamp;
    private Integer statusCode;
    private String statusMessage;
    T data;
}
