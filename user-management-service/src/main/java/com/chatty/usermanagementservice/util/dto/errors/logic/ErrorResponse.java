package com.chatty.usermanagementservice.util.dto.errors.logic;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private List<ErrorEntity> errors;
}