package com.chatty.usermanagementservice.util.exceptions.message;

import com.chatty.usermanagementservice.util.dto.errors.logic.ErrorCode;
import com.chatty.usermanagementservice.util.exceptions.BaseException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageNotFoundException extends BaseException {
    @Builder
    public MessageNotFoundException(LocalDateTime errorDate, String errorMessage, ErrorCode errorCode, Object dataCausedError) {
        super(errorDate, errorMessage, errorCode, dataCausedError);
    }
}
