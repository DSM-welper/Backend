package welper.welper.external

import com.fasterxml.jackson.annotation.JsonProperty

class WelfareResponse (
    @JsonProperty("translated_text")
    val translatedText: List<List<String>>

)