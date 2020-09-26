package com.poly.petcare.domain.exceptions;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ValidDetails {
    private String message;
    private Map<String, String> data;
    private Integer errorCode;
    private String error;
    private String path;
    private Date timestamp;
}
