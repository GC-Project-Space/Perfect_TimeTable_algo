class Choice {

    init {
        repeat(5) {
            Chosen.chosens.add(mutableListOf())
        }
    }

    fun binarySearchAndInsert(sortedDay: String, newValue: Subject) {
        val day = when (sortedDay) {
            "mon" -> 0
            "tue" -> 1
            "wed" -> 2
            "thu" -> 3
            "fri" -> 4
            else -> -1
        }

        var low = 0
        var high = Chosen.chosens[day].size - 1

        if (Chosen.chosens[day].isEmpty()) {
            Chosen.chosens[day].add(newValue)
        } else {
            while (low <= high) {
                val mid = (low + high) / 2
                when {
                    Chosen.chosens[day][mid].startTime == newValue.startTime -> return
                    Chosen.chosens[day][mid].startTime < newValue.startTime -> low = mid + 1
                    else -> high = mid - 1
                }
            }
            Chosen.chosens[day].add(low, newValue)
        }
    }

    fun choseMajor(grade: Int) {
        val db = DataBase().getMajor(Chosen.grade)

        for (i in db) {
            when (i.day) {
                "mon" -> binarySearchAndInsert("mon", i)
                "tue" -> binarySearchAndInsert("tue", i)
                "wed" -> binarySearchAndInsert("wed", i)
                "thu" -> binarySearchAndInsert("thu", i)
                "fri" -> binarySearchAndInsert("fri", i)
            }
        }
    }

    fun addSubject(newSubject: Subject) {
        when (newSubject.day) {
            "mon" -> binarySearchAndInsert("mon", newSubject)
            "tue" -> binarySearchAndInsert("tue", newSubject)
            "wed" -> binarySearchAndInsert("wed", newSubject)
            "thu" -> binarySearchAndInsert("thu", newSubject)
            "fri" -> binarySearchAndInsert("fri", newSubject)
        }
    }

    fun calculationGap(newSubject: Subject): Int {
        val array = Array(5) { mutableListOf<Subject>() }
        repeat(5) { i ->
            repeat(Chosen.chosens[i].size) { j ->
                array[i].add(Chosen.chosens[i][j])
            }
        }

        var gap = 0

        val day = when (newSubject.day) {
            "mon" -> 0
            "tue" -> 1
            "wed" -> 2
            "thu" -> 3
            "fri" -> 4
            else -> -1
        }

        var low = 0
        var high = Chosen.chosens[day].size - 1

        while (low <= high) {
            val mid = (low + high) / 2
            when {
                array[day][mid].startTime == newSubject.startTime -> return -1

                array[day][mid].endTime == newSubject.startTime -> return 0
                array[day][mid].endTime+1 == newSubject.startTime -> return 1
                array[day][mid].endTime+2 == newSubject.startTime -> return 2
                array[day][mid].endTime+3 == newSubject.startTime -> return 3
                array[day][mid].endTime+4 == newSubject.startTime -> return 4



                array[day][mid].startTime == newSubject.endTime -> return 0
                array[day][mid].startTime == newSubject.endTime+1 -> return 1
                array[day][mid].startTime == newSubject.endTime+2 -> return 2
                array[day][mid].startTime == newSubject.endTime+3 -> return 3
                array[day][mid].startTime == newSubject.endTime+4 -> return 4
                array[day][mid].startTime < newSubject.startTime -> low = mid + 1
                else -> high = mid - 1
            }
            array[day].add(low, newSubject)
        }

        for (i in array.indices) {
            for (j in array[i].indices) {
                if (j < array[i].size - 1) {
                    if (array[i][j].endTime > array[i][j + 1].startTime) return -1
                    else if (array[i][j].series != array[i][j + 1].series && array[i][j + 1].startTime - array[i][j].endTime <= 1) return -1
                }
            }
        }

        for (i in array.indices) {
            for (j in array[i].indices) {
                if (j < array[i].size - 1) {
                    gap += array[i][j + 1].startTime - array[i][j].endTime
                }
            }
        }

        return gap-1
    }

    fun choiceSubject(area: String, credit: Int, days: List<String>) {
        val db = mutableListOf<List<Subject>>()
        var mingap = 200
        var temp = Subject("", "", 0, "", 0, "", "")

        days.forEach { day ->
            db.add(DataBase().getCreditAreaData(credit, area, day))
        }

        for (i in db) {
            for (j in i) {
                if (findSameName(j.name)) {
                    continue
                }
                val n = calculationGap(j)
                if (n == -1) {
                    continue
                } else if (n < mingap) {
                    temp = j
                    mingap = calculationGap(j)
                }
            }
        }

        if (mingap < 0) { // mingap이 특정 값 이하일 때 재귀 호출 멈추기
            return
        }

        if (mingap == 200) {
            if (days.size == 5){
                println("해당 학점과 영역에 맞는 과목이 없습니다.")
                return
            }
            choiceSubject(area, credit, listOf("mon", "tue", "wed", "thu", "fri"))
        } else {
            addSubject(temp)
        }
    }

    fun findEmptyDay(): List<String> {
        val result = mutableListOf<String>()
        if (Chosen.chosens[0].isNotEmpty()) {
            result.add("mon")
        }
        if (Chosen.chosens[1].isNotEmpty()) {
            result.add("tue")
        }
        if (Chosen.chosens[2].isNotEmpty()) {
            result.add("wed")
        }
        if (Chosen.chosens[3].isNotEmpty()) {
            result.add("thu")
        }
        if (Chosen.chosens[4].isNotEmpty()) {
            result.add("fri")
        }

        return result
    }

    fun findSameName(name: String): Boolean {
        var result = false
        for (i in Chosen.chosens.indices) {
            for (j in Chosen.chosens[i]) {
                if (j.name == name) {
                    result = true
                }
            }
        }
        return result
    }
    fun choiceSubjectList(inp: SubjectList, grade: String) {
        if (inp.title == "전공") {
            choseMajor(grade.toInt())
        } else {
            val time = listOf(listOf(0), listOf(1), listOf(2), listOf(3), listOf(2, 2), listOf(3, 2), listOf(2, 2, 2))

            val day =findEmptyDay().toMutableList()

            time[inp.wantCredit].forEach { i ->
                choiceSubject(inp.title, i, day)
            }
        }
    }


}
