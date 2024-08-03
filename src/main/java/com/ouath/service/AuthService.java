package com.ouath.service;

import com.ouath.dto.request.auth.CheckCertificationRequestDto;
import com.ouath.dto.request.auth.EmailCertificationRequestDto;
import com.ouath.dto.request.auth.IdCheckRequestDto;
import com.ouath.dto.request.auth.SignUpRequestDto;
import com.ouath.dto.response.auth.CheckCertificationResponseDto;
import com.ouath.dto.response.auth.EmailCertificationResponseDto;
import com.ouath.dto.response.auth.IdCheckResponseDto;
import com.ouath.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);

    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);

    ResponseEntity<? super SignUpResponseDto> singUp(SignUpRequestDto dto);
}
