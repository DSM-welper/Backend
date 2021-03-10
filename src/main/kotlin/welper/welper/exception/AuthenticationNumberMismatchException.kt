package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class AuthenticationNumberMismatchException(
        authCode:String,
): CommonException(
        code = "AUTHENTICATION_NUMBER_MISMATCH",
        message = "인증번호가 불일치합니다. [authentication number = $authCode]",
        status = HttpStatus.BAD_REQUEST,
)