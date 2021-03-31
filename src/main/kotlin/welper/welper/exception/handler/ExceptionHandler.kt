package welper.welper.exception.handler

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notValidateExceptionHandler(e: MethodArgumentNotValidException) =
            ExceptionResponse(
                    code = "INVALID_REQUEST_BODY",
                    message = "클라이언트의 요청이 잘못되었습니다. [${e.bindingResult.allErrors.first().defaultMessage}]",
            )

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun notValidateExceptionHandler(e: MethodArgumentTypeMismatchException) =
            ExceptionResponse(
                    code = "INVALID_REQUEST_BODY",
                    message = "${e.mostSpecificCause.message}",
            )

    @ExceptionHandler(CommonException::class)
    fun commonExceptionHandler(e: CommonException) =
            ResponseEntity(
                    ExceptionResponse(
                            code = e.code,
                            message = e.message ?: "뜨면 안됨..",
                    ),
                    e.status,
            )

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException) {
        ExceptionResponse(
                code = "JSON_PARS_ERROR",
                message = "클라이언트의 요청이 잘못되었습니다. type을 맞춰주세요",
        )
    }

    @ExceptionHandler(InvalidFormatException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun invalidFormatExceptionHandler(e: InvalidFormatException) {
        ExceptionResponse(
                code = "INVALID_REQUEST_BODY",
                message = "클라이언트의 요청이 잘못되었습니다. type을 맞춰주세요",
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(e: RuntimeException): ResponseEntity<ExceptionResponse> {
        e.printStackTrace()
        return ResponseEntity(
                ExceptionResponse(
                        code = "INTERNAL_SERVER_ERROR",
                        message = "\"서버 에러를 총칭하는\"(catch-all) 구체적이지 않은 응답입니다.",
                ),
                HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }

}