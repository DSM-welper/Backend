package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class NonNumOfPageOutOfBoundsException
    : CommonException(
            code = "NON_EXIST_PAGE",
            message = "없는 page입니다.",
            status = HttpStatus.BAD_REQUEST,
    )
