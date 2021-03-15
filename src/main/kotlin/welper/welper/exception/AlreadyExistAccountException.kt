package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class AlreadyExistAccountException(
    email:String,
): CommonException(
        code="ALREADY_EXIST_ACCOUNT",
        message="이미 존재하는 계정입니다. [Id = $email]",
        status = HttpStatus.BAD_REQUEST
)