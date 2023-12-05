import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class DataBase {
    var major: List<Map<String, Any>>
    var elective: List<Map<String, Any>>
    private val gson = Gson()

    init {
        val majorJson = File("C:\\GitFolder\\Perfect_TimeTable_algo\\perfect_time_table\\src\\main\\kotlin\\major.json").readText(Charsets.UTF_8)
        val electiveJson = File("C:\\GitFolder\\Perfect_TimeTable_algo\\perfect_time_table\\src\\main\\kotlin\\elective.json").readText(Charsets.UTF_8)

        val mapType = object : TypeToken<List<Map<String, Any>>>() {}.type
        major = gson.fromJson(majorJson, mapType)
        elective = gson.fromJson(electiveJson, mapType)

        // 데이터 출력
        println("Loaded major data: $major")
        println("Loaded elective data: $elective")
    }

    fun getCreditAreaData(credit: Int, area: String, day: String): List<Subject> {
        val resultData = elective.filter {
            it["영역"] == area && it["학점"].toString().toDouble()== credit.toDouble() && it["요일"] == day
        }

        // 데이터 출력
        println("Credit Area Data for Credit resultData: $credit, Area: $area, Day: $day -> $resultData")


        val result = mutableListOf<Subject>()
        for (i in resultData) {
            result.add(
                Subject(
                    i["교과목명"].toString(),
                    i["학수번호"].toString(), // Assuming "학수번호" is equivalent to "과목코드"
                    i["시작 시간"].toString().toDouble().toInt(), // Adjust key name to match JSON data
                    i["요일"].toString(),
                    i["끝 시간"].toString().toDouble().toInt(), // Adjust key name to match JSON data
                    i["학점"].toString(),
                    i["영역"].toString() // Assuming "영역" is equivalent to "시리즈"
                )
            )
        }

        // 데이터 출력
        println("Credit Area Data for Credit Subjext: $credit, Area: $area, Day: $day -> $result")

        return result
    }

    // get_major 메서드
    fun getMajor(grade: Int): List<Subject> {
        val resultData = major.filter { item -> item["학년"].toString().toDouble() == grade.toDouble() }

        val result = mutableListOf<Subject>()
        for (i in resultData) {
            val temp = i.toMutableMap() // Convert Map<String, Any> to MutableMap<String, Any>
            temp.remove("학년") // Remove unnecessary "학년" field
            val subject = Subject(
                temp["교과목명"].toString(),
                temp["학수번호"].toString(), // Assuming "학수번호" is equivalent to "과목코드"
                temp["시작 시간"].toString().toDouble().toInt(), // Adjust key name to match JSON data
                temp["요일"].toString(),
                temp["끝 시간"].toString().toDouble().toInt(), // Adjust key name to match JSON data
                temp["학점"].toString(),
                temp["영역"].toString() // Assuming "영역" is equivalent to "시리즈"
            )
            result.add(subject)
        }

        // 데이터 출력
        println("Major for Grade: $grade -> $result")

        return result
    }

    // 다른 메서드들 추가...
}