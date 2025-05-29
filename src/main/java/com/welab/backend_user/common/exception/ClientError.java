package com.welab.backend_user.common.exception;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class ClientError extends ApiError{
//    public ClientError(String message) {
//        this.errorCode = "NotFound";
//        this.errorMessage = message;
//    }
//}

@Getter
public abstract class ClientError extends ApiError {
    public ClientError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
