package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class InvalidTokenException(token: String) : CommonException(
    code = "INVALID_TOKEN",
    message = "토큰이 잘못되었습니다. [token = $token]",
    status = HttpStatus.UNAUTHORIZED,
)