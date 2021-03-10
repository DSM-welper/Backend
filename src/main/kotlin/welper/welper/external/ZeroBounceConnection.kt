//package welper.welper.external
//
//import retrofit2.Call
//import retrofit2.http.GET
//import retrofit2.http.Header
//import retrofit2.http.Headers
//import retrofit2.http.Query
//
//interface ZeroBounceConnection {
//
//    @Headers(value = ["accept: application/json", "content-type: application/json"])
//    @GET("/v2/validate")
//    fun sendMail(
//        @Header("X-RapidAPI-Key") apiKey: String,
//        @Header("X-RapidAPI-Host") apiHost: String,
//        @Query("ip_address") ipAddress: String,
//        @Query("email") email: String,
//    ): Call<>
//
//    // ?ip_address=replace_the_IP_address_the_email_signed_up_from&email=ahn479512%40gmail.com
//}