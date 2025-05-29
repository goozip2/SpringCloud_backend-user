package com.welab.backend_user.common.exception;

//public class BadParameter extends ClientError{
//    public BadParameter(String errorMessage) {
//        this.errorCode = "BadParameter";
//        this.errorMessage = errorMessage;
//    }
//}

public class BadParameter extends ClientError {
    public BadParameter(String message) {
        super("BadParameter", message);
    }
}
