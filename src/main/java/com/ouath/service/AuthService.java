package com.ouath.service;

import com.ouath.dto.request.auth.*;
import com.ouath.dto.response.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);

    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);

    ResponseEntity<? super SignUpResponseDto> singUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> singIn(SignInRequestDto dto);
}
