package com.example.sep2022javaspring.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDto {
    private List<String> messages;

    private int statusCode;
}