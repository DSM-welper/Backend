package welper.welper.domain.attribute

enum class DesireArray(
        val value: String,
) {
    SAFETY("안전"),
    HEALTH("건강"),
    DAILYLIFE("일상생활유지"),
    FAMIlY("가족관계"),
    SOCIAL("사회적 관계"),
    ECONOMIC("경제"),
    EDUCATION("교육"),
    EMPLOYMENT("고용"),
    LIFE("생활환경"),
    LAW("법"),
    ELSE("기타"),
}