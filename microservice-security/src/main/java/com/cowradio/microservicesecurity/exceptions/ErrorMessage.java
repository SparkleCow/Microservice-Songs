package com.cowradio.microservicesecurity.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
    String code;
    String message;
}
