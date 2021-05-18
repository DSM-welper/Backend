package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class CategoryNotFound(
        servId:String
) : CommonException(
        code = "CATEGORY_NOTFOUND",
        message = "일치하는 게시물을 찾을 수 없습니다. [Not found comments = [id:$servId]",
        status = HttpStatus.BAD_REQUEST,
)