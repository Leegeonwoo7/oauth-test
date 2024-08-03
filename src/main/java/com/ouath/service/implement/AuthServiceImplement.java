package com.ouath.service.implement;

import com.ouath.common.CertificationNumber;
import com.ouath.dto.request.auth.CheckCertificationRequestDto;
import com.ouath.dto.request.auth.EmailCertificationRequestDto;
import com.ouath.dto.request.auth.IdCheckRequestDto;
import com.ouath.dto.request.auth.SignUpRequestDto;
import com.ouath.dto.response.ResponseDto;
import com.ouath.dto.response.auth.CheckCertificationResponseDto;
import com.ouath.dto.response.auth.EmailCertificationResponseDto;
import com.ouath.dto.response.auth.IdCheckResponseDto;
import com.ouath.dto.response.auth.SignUpResponseDto;
import com.ouath.entity.CertificationEntity;
import com.ouath.entity.UserEntity;
import com.ouath.provider.EmailProvider;
import com.ouath.repository.CertificationRepository;
import com.ouath.repository.UserRepository;
import com.ouath.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    private final EmailProvider emailProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
        try {

            String userId = dto.getId();
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            if (certificationEntity == null) {
                return CheckCertificationResponseDto.certificationFail();
            }

            boolean isMatched =
                    certificationEntity.getEmail().equals(email)
                    && certificationEntity.getCertificationNumber().equals(certificationNumber);

            if (!isMatched) {
                return CheckCertificationResponseDto.certificationFail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return CheckCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> singUp(SignUpRequestDto dto) {

        try {

            String userId = dto.getId();
            boolean isExistId = userRepository.existsById(userId);
            if (isExistId) {
                return SignUpResponseDto.duplicateId();
            }

            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            boolean isMatched = certificationEntity.getEmail()
                    .equals(email) && certificationEntity.getCertificationNumber()
                    .equals(certificationNumber);
            if (!isMatched) {
                return SignUpResponseDto.certificationFail();
            }

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

            certificationRepository.deleteByUserId(userId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();
    }
}
