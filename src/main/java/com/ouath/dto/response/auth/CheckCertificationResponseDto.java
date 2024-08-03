package com.ouath.dto.response.auth;

import com.ouath.common.ResponseCode;
import com.ouath.common.ResponseMessage;
import com.ouath.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.*;

@Getter
public class CheckCertificationResponseDto extends ResponseDto {

    private CheckCertificationResponseDto() {
        super();
    }

    public static ResponseEntity<CheckCertificationResponseDto> success() {
        CheckCertificationResponseDto responseBody = new CheckCertificationResponseDto();
        return ResponseEntity.status(OK)
                .body(responseBody);
    }

    public static ResponseEntity<ResponseDto> certificationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(UNAUTHORIZED)
                .body(responseBody);
    }

}
