package com.ouath.controller;

import com.ouath.dto.request.auth.EmailCertificationRequestDto;
import com.ouath.dto.request.auth.IdCheckRequestDto;
import com.ouath.dto.response.auth.EmailCertificationResponseDto;
import com.ouath.dto.response.auth.IdCheckResponseDto;
import com.ouath.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/id-check")
    public ResponseEntity<? super IdCheckResponseDto> idCheck(
            @RequestBody @Valid IdCheckRequestDto requestBody
            ) {
        return authService.idCheck(requestBody);
    }

    @PostMapping("/email-certification")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCheck(
            @RequestBody @Valid EmailCertificationRequestDto requestBody
            ) {
        return authService.emailCertification(requestBody);
    }
}
