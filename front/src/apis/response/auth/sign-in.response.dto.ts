import ResponseDto from "../response.dto";

export declare interface SignInInResponseDto extends ResponseDto{

    token: string,
    expirationTime: number
}