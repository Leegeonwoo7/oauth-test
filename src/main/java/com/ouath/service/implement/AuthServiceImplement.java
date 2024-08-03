package com.ouath.service.implement;

import com.ouath.common.CertificationNumber;
import com.ouath.dto.request.auth.EmailCertificationRequestDto;
import com.ouath.dto.request.auth.IdCheckRequestDto;
import com.ouath.dto.response.ResponseDto;
import com.ouath.dto.response.auth.EmailCertificationResponseDto;
import com.ouath.dto.response.auth.IdCheckResponseDto;
import com.ouath.entity.CertificationEntity;
import com.ouath.provider.EmailProvider;
import com.ouath.repository.CertificationRepository;
import com.ouath.repository.UserRepository;
import com.ouath.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final EmailProvider emailProvider;
    private final CertificationRepository certificationRepository;

    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
        try{
            String userId = dto.getId();
            boolean isExistId = userRepository.existsById(userId);
            if (isExistId) {
                return IdCheckResponseDto.duplicateId();
            }

        } catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {

        try {

            String userId = dto.getId();
            String email = dto.getEmail();

            boolean isExistId = userRepository.existsById(userId);
            if (isExistId) {
                return EmailCertificationResponseDto.duplicateId();
            }

            String certificationNumber = CertificationNumber.getCertificationNumber();

            boolean isSucceed = emailProvider.sendMail(email, certificationNumber);
            if (!isSucceed) {
                return EmailCertificationResponseDto.mailSendFail();
            }

            CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
            certificationRepository.save(certificationEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailCertificationResponseDto.success();
    }
}
