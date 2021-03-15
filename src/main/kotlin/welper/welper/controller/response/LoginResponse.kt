package welper.welper.controller.response

data class LoginResponse(
        val accessToken: String,
        val refreshToken: String,
)