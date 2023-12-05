import javax.security.auth.Subject

fun main(args: Array<String>) {

    val execute = Execute()
    val subjectList = mutableListOf<SubjectList>()
    subjectList.add(SubjectList("전공", 2, true))
    subjectList.add(SubjectList("기초교양", 2, true))
    subjectList.add(SubjectList("일반교양", 4, true))

    execute.main(subjectList)
}

//C:\GitFolder\Perfect_TimeTable_algo\perfect_time_table\src\main\kotlin\major.json