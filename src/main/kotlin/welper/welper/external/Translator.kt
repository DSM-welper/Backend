package welper.welper.external

interface Translator {
    fun translation(english: String): String?
    suspend fun translationAll(englishList: List<String>): List<String?>
}