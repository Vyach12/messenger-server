package com.chatty.authentication.util.exceptions.user;

import com.chatty.authentication.util.dto.errors.logic.ErrorCode;
import com.chatty.authentication.util.exceptions.BaseException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserIsNotEnabledException extends BaseException {
    @Builder
    public UserIsNotEnabledException(LocalDateTime errorDate, String errorMessage, ErrorCode errorCode, Object dataCausedError) {
        super(errorDate, errorMessage, errorCode, dataCausedError);
    }
}