package com.jocata.oms.common.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericRequestPayload<T> {
    private String requestId;
    private String timeStamp;
    T data;
}
