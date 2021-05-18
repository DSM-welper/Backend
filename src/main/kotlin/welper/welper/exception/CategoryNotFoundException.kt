package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class CategoryNotFoundException(
        servId: String,
) : CommonException(
        code = "CATEGORY_ID_MISMATCH",
        message = "id에 해당하는 카테고리가 없습니다. [id = $servId]",
        status = HttpStatus.BAD_REQUEST,
)