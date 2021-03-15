package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class UserNotFoundException(
        email: String,
) : CommonException(
        code = "USER_NOTFOUND",
        message = "인증번호가 불일치합니다. [Not found email = [$email]",
        status = HttpStatus.BAD_REQUEST,
)