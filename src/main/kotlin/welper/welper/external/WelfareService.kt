package welper.welper.external

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.net.URLDecoder
import java.net.URLEncoder

@Service
class WelfareService : WelfareServiceI {
    private val kakaoAuthorization: String = "KakaoAK <REST API KEY>"
    private val kakaoConnection = Retrofit.Builder()
            .baseUrl("http://www.bokjiro.go.kr")
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .build()
            .create(WelfareConnection::class.java)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val serviceKey:String = "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2bYS8v35JrIJ%2fxYDe3%2btshuX2%2b2EZg3w%3d%3d"
    private val serviceDecodeKey:String = URLDecoder.decode(serviceKey,"UTF-8")
    private val serviceEncodeKey:String = URLEncoder.encode(serviceDecodeKey,"UTF-8")
    private val callTp:String = URLEncoder.encode("L","UTF-8")
    private val pageNum:String = URLEncoder.encode("1","UTF-8")
    private val numOFRows:String = URLEncoder.encode("10","UTF-8")
    private val lifeArray:String =URLEncoder.encode("001","UTF-8")
    private val json:String = URLEncoder.encode("json","UTF-8")
    override fun translation() =
            try {
                kakaoConnection.translation(serviceEncodeKey, callTp, pageNum, numOFRows,lifeArray,json).execute().body()!!
            } catch (e: Exception) {
                e.printStackTrace()
                println("API 연결 실패")
                null
            }
}