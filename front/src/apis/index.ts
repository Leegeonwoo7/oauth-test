import IdCheckRequestDto from "./request/auth/id-check.request.dto";
import axios, {AxiosResponse} from "axios";
import IdCheckResponseDto from "./response/auth/id-check.response.dto";
import {EmailCertificationResponseDto, ResponseDto} from "./response";
import {EmailCertificationRequestDto} from "./request/auth";

const responseHandler = <T> (response: AxiosResponse<any, any>) => {
    const responseBody: T = response.data;
    return responseBody;
};

const errorHandler = (error: any) => {
    if (!error.response || !error.response.data) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
};

const DOMAIN = 'http://localhost:8080';
const API_DOMAIN = `${DOMAIN}/api/v1`;

const ID_CHECK_URL = () => `${API_DOMAIN}/auth/id-check`;
const EMAIL_CERTIFICATION_URL = () => `${API_DOMAIN}/auth/email-certification`;

export const idCheckRequest = async (requestBody: IdCheckRequestDto) => {
    const result = await axios.post(ID_CHECK_URL(), requestBody)
        .then(responseHandler<IdCheckResponseDto>)
        .catch(errorHandler);
    return result;
};

export const emailCertificationRequest = async (requestBody: EmailCertificationRequestDto)=> {
    const result = await axios.post(EMAIL_CERTIFICATION_URL(), requestBody)
        .then(responseHandler<EmailCertificationResponseDto>)
        .catch(errorHandler)
    return result;
}