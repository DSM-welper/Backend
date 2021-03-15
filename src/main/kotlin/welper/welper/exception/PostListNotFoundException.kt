package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class PostListNotFoundException(
) : CommonException(
        code = "POST_NOTFOUND",
        message = "일치하는 게시물을 찾을 수 없습니다. ",
        status = HttpStatus.BAD_REQUEST,
)