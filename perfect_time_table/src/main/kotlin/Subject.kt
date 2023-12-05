
data class Subject(
    val name: String,
    val courseCode:String,
    val startTime: Int,
    val day: String,
    val endTime: Int,
    val credit: String,
    val series: String
)

data class SubjectList( //교양 계열
    val title: String,
    var wantCredit:Int, //듣고 싶은 학점
    var isChoice:Boolean //무시
)