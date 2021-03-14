package welper.welper.exception

import org.springframework.http.HttpStatus
import welper.welper.exception.handler.CommonException

class PostNotFoundException(
        email: String,id:Int
) : CommonException(
        code = "POST_NOTFOUND",
        message = "일치하는 게시물을 찾을 수 없습니다. [Not found email = [email:$email, id:$id]",
        status = HttpStatus.BAD_REQUEST,
)