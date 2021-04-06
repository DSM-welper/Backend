package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class NonExistCategoryDetailException (
) : CommonException(
        code = "NON_EXIST_CATEGORY_DETAIL",
        message = "허용하지 않는 카테고리입니다. ",
        status = HttpStatus.BAD_REQUEST,
)