package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class NonExistEmailCertifyException(
        email: String
) : CommonException(
        code = "NON_EXIST_EMAILCERTIFY",
        message = "허용되지 않은 [${email}] 입니다",
        status = HttpStatus.BAD_REQUEST,
)