package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class NonExistMarryException (
        marry: String
) : CommonException(
        code = "NON_EXIST_FLOOR",
        message = "허용하지 않는 현재 결혼상태입니다. [marry = ${marry}]",
        status = HttpStatus.BAD_REQUEST,
)