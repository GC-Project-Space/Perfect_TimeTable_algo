import Subject

class Execute {

    fun changeHour(hour: Int): Int {
        return hour - 8
    }

    fun main(inp: List<SubjectList>): List<Subject> {
        val result = mutableListOf<Subject>()
        val db = Choice()
        for (i in inp) {
            db.choiceSubjectList(i, "2")

            for (chosenSubjects in Chosen.chosens) {
                for (j in chosenSubjects) {
                    if(j==chosenSubjects[chosenSubjects.size-1]){
                        result.add(
                            Subject(
                                j.name,
                                j.courseCode,
                                changeHour(j.startTime),
                                j.day,
                                changeHour(j.endTime),
                                j.credit,
                                j.series
                            )
                        )
                    }
                }
            }

            for (i in result) {
                println("${i.name} ${i.startTime} ${i.endTime} ${i.day}")
            }
        }
        return result
    }
}