package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class CategoryNotFoundException(
        servId: String,
) : CommonException(
        code = "AUTHENTICATION_NUMBER_MISMATCH",
        message = "인증번호가 불일치합니다. [authentication number = $servId]",
        status = HttpStatus.BAD_REQUEST,
)