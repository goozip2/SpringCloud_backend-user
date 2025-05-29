package com.welab.backend_user.common.exception;

//public class NotFound extends ClientError {
//    public NotFound(String errorMessage) {
//        this.errorCode = "NotFound";
//        this.errorMessage = errorMessage;
//    }
//}

public class NotFound extends ClientError {
    public NotFound(String message) {
        super("NotFound", message);
    }
}
