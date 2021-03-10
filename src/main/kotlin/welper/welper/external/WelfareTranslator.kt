package welper.welper.external

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class WelfareTranslator : Translator {
    private val kakaoAuthorization: String = "KakaoAK <REST API KEY>"
    private val kakaoConnection = Retrofit.Builder()
            .baseUrl("http://www.bokjiro.go.kr")
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .build()
            .create(WelfareConnection::class.java)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun translation(english: String) =
            try {
                kakaoConnection.translation("", "L", "1", "10","001").execute().body()!!.translatedText.single().single()
            } catch (e: Exception) {
                e.printStackTrace()
                println("카카오 번역 API 연결 실패")
                null
            }

    override suspend fun translationAll(englishList: List<String>) =
            englishList.map {
                coroutineScope.async {
                    translation(it)
                }
            }.awaitAll()
}