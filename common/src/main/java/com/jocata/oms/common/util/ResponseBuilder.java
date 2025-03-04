package com.jocata.oms.common.util;

import com.jocata.oms.common.response.GenericResponsePayload;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseBuilder {

    public static <T> GenericResponsePayload<T> buildResponse(T data, String message, HttpStatus status) {
        GenericResponsePayload<T> response = new GenericResponsePayload<>();
        response.setRequestId(UUID.randomUUID().toString());
        response.setTimeStamp(LocalDateTime.now().toString());
        response.setStatusCode(status.value());
        response.setStatusMessage(message);
        response.setData(data);
        return response;
    }
}
