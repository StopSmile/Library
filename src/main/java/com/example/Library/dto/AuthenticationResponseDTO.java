package com.example.Library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class AuthenticationResponseDTO{
    private String email;
    private String token;
}
