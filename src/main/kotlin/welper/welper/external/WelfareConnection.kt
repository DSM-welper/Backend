package welper.welper.external

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WelfareConnection {

    @Headers(value = ["accept: application/json", "content-type: application/xml"])
    @GET("/openapi/rest/gvmtWelSvc")
    fun translation(
            @Query("crtiKey") Key: String,
            @Query("callTp") callTp: String,
            @Query("pageNum") pageNum: String,
            @Query("numOfRows") numOfRows: String,
            @Query("lifeArray") lifeArray: String,
            @Query("_type") type:String,
    ): Call<WelfareResponse>
}