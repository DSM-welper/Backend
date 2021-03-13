package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class NonExistGenderException (
        gender: String
) : CommonException(
        code = "NON_EXIST_FLOOR",
        message = "허용하지 않는 성별입니다. [gender = ${gender}]",
        status = HttpStatus.BAD_REQUEST,
)