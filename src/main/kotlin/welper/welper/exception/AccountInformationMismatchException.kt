package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class AccountInformationMismatchException (
        requestInformation: String,
        findInformation: String,
) : CommonException(
        code = "ACCOUNT_INFORMATION_MISMATCH",
        message = "계정 정보가 불일치합니다. [$requestInformation != $findInformation]",
        status = HttpStatus.BAD_REQUEST,
)