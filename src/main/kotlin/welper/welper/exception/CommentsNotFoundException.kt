package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class CommentsNotFoundException(
        id:Int
) : CommonException(
        code = "COMMENTS_NOTFOUND",
        message = "일치하는 댓글을 찾을 수 없습니다. [Not found comments = [id:$id]",
        status = HttpStatus.BAD_REQUEST,
)