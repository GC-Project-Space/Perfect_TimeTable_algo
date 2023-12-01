import json


# 선택된 과목 list
Chosen = [[], [], [], [], []]


# 과목 struct
class Subject:
    def __init__(self, ori):
        self.name = ori[1]
        self.course_code = ori[0]
        self.start_time = ori[5]
        self.day = ori[4]
        self.end_time = ori[6]
        self.credit = ori[3]
        self.series = ori[2]


class DataBase:
    # 파일 일어오기
    def __init__(self):
        with open("data/major.json", "r", encoding="utf-8") as file:
            self.major = json.load(file)
        with open("data/elective.json", "r", encoding="utf-8") as file:
            self.elective = json.load(file)

    # 학점과 영역, 요일을 기준으로 과목을 들고오는 것
    def get_credit_area_data(self, credit, area, day):
        result_data = [
            item
            for item in self.elective
            if item.get("영역") == area
            and item.get("학점") == credit
            and item.get("요일") == day
        ]

        result = []
        for i in result_data:
            result.append(Subject(list(i.values())))

        return result

    # 영역을 입력하면 그 영역에 맞는 unique한 과목 list return
    def get_unique_list(self, area):
        temp = self.get_data(area)
        result = [list(item) for item in {tuple(data[1:3]) for data in temp}]

        return result

    # 학년을 입력하면 그에 맞는 전공 return
    def get_major(self, grade):
        result_data = [item for item in self.major if item.get("학년") == grade]

        result = []
        for i in result_data:
            temp = list(i.values())
            del temp[2]
            result.append(Subject(temp))

        return result


class choice:
    # 정렬해서 시간표 넣는 함수
    def binary_search_and_insert(self, sorted_day, new_value):
        if sorted_day == "mon":
            day = 0
        elif sorted_day == "tue":
            day = 1
        elif sorted_day == "wed":
            day = 2
        elif sorted_day == "thu":
            day = 3
        elif sorted_day == "fri":
            day = 4

        low, high = 0, len(Chosen[day]) - 1

        if len(Chosen[day]) == 0:
            Chosen[day].append(new_value)

        # 이진 탐색을 통해 정렬된 위치 찾기
        else:
            while low <= high:
                mid = (low + high) // 2
                if Chosen[day][mid].start_time == new_value.start_time:
                    # 중복된 값이 있을 경우 적절한 위치에 삽입
                    return -1
                elif Chosen[day][mid].start_time < new_value.start_time:
                    low = mid + 1
                else:
                    high = mid - 1

            # 정렬된 위치에 값을 삽입
            Chosen[day].insert(low, new_value)

    # 학년에 맞춰서 전공을 넣는 함수
    def choseMajor(self, grade):
        db = DataBase().get_major(grade)

        for i in db:
            if i.day == "mon":
                self.binary_search_and_insert("mon", i)
            elif i.day == "tue":
                self.binary_search_and_insert("tue", i)
            elif i.day == "wed":
                self.binary_search_and_insert("wed", i)
            elif i.day == "thu":
                self.binary_search_and_insert("thu", i)
            elif i.day == "fri":
                self.binary_search_and_insert("fri", i)

    # 수업을 Chosen에 추가하는 함수
    def add_subject(self, newSubject):
        if newSubject.day == "mon":
            self.binary_search_and_insert("mon", newSubject)
        elif newSubject.day == "tue":
            self.binary_search_and_insert("tue", newSubject)
        elif newSubject.day == "wed":
            self.binary_search_and_insert("wed", newSubject)
        elif newSubject.day == "thu":
            self.binary_search_and_insert("thu", newSubject)
        elif newSubject.day == "fri":
            self.binary_search_and_insert("fri", newSubject)

    # Gap을 계산하는 함수
    def calculationGap(self, new):
        array = [[], [], [], [], []]
        for i in range(5):
            for j in range(len(Chosen[i])):
                array[i].append(Chosen[i][j])

        gap = 0

        if new.day == "mon":
            day = 0
        elif new.day == "tue":
            day = 1
        elif new.day == "wed":
            day = 2
        elif new.day == "thu":
            day = 3
        elif new.day == "fri":
            day = 4

        low, high = 0, len(Chosen[day]) - 1

        # 이진 탐색을 통해 정렬된 위치 찾기
        while low <= high:
            mid = (low + high) // 2
            if array[day][mid].start_time == new.start_time:
                return -1
            elif array[day][mid].start_time < new.start_time:
                low = mid + 1
            else:
                high = mid - 1

            # 정렬된 위치에 값을 삽입
            array[day].insert(low, new)

        # 시간이 겹치면 끝내기
        for i in range(len(array[day]) - 1):
            if array[day][i].end_time > array[day][i + 1].start_time:
                return -1
            elif (
                array[day][i].series != array[day][i + 1].series
                and array[day][i + 1].start_time - array[day][i].end_time <= 1
            ):
                return -1

        # 시간 구하기
        for i in range(len(array)):
            for j in range(len(array[i]) - 1):
                gap += array[i][j + 1].start_time - array[i][j].end_time

        return gap

    def choice_subject(self, area, credit, days):
        db = []
        mingap = 200

        for day in days:
            db.append(DataBase().get_credit_area_data(credit, area, day))

        for i in db:
            for j in i:
                if self.find_same_name(j.name):
                    continue
                n = self.calculationGap(j)
                if n == -1:
                    continue
                elif n < mingap:
                    temp = j
                    mingap = self.calculationGap(j)

        if mingap == 200:
            self.choice_subject(area, credit, ["mon", "tue", "wed", "thu", "fri"])
        else:
            self.add_subject(temp)

    def find_empty_day(self):
        result = []
        if len(Chosen[0]) != 0:
            result.append("mon")
        if len(Chosen[1]) != 0:
            result.append("tue")
        if len(Chosen[2]) != 0:
            result.append("wed")
        if len(Chosen[3]) != 0:
            result.append("thu")
        if len(Chosen[4]) != 0:
            result.append("fri")

        return result

    def find_same_name(self, name):
        result = False
        for i in range(len(Chosen)):
            for j in Chosen[i]:
                if j.name == name:
                    result = True

        return result

    # main
    def choice_subject_list(self, inp):
        if inp.title == "전공":
            self.choseMajor(inp.wantCredit)
        else:
            time = [[0], [1], [2], [3], [2, 2], [3, 2], [2, 2, 2]]

            day = []
            day.append(self.find_empty_day())
            for i in time[inp.wantCredit]:
                self.choice_subject(inp.title, i, day)


class input:
    def __init__(self, title, wantCredit):
        self.title = title
        self.wantCredit = wantCredit


class output:
    def __init__(self, title, startTime, endTime, day):
        self.title = title
        self.startTime = startTime
        self.endTime = endTime
        self.day = day


class execute:
    def change_hour(self, hour):
        return hour - 8

    # 이것만 보면된다!!! 나머지는 세부 기능!!!!
    # inp = input list (array)
    def main(self, inp):
        try:
            db = choice()  # Class 여는 것
            for i in inp:  # 전달 받은 들어가야할 과목 Input 정보 list를 1개씩 나누어
                db.choice_subject_list(i)  # algorithm을 돌리기

        except RecursionError:  # 시간표를 조건에 맞게 만들 수 없으면 생기는 오류
            print("Not complete")  # 오류 샐길시 실행되는 부분

        result = []  # Output을 만드는 과정
        for i in range(len(Chosen)):
            for j in Chosen[i]:
                result.append(  # Output struct에 맞춰 저장
                    output(
                        j.name,
                        self.change_hour(j.start_time),
                        self.change_hour(j.end_time),
                        j.day,
                    )
                )

        return result


# 예시
EXECUTE = execute()  # 실행 class 만들기
# 넣어야할 과목 정보 순서대로 넣기
subject_list = []
subject_list.append(input("전공", 2))
subject_list.append(input("기초교양", 2))

# 시간표 짜기 알고리즘 시작
result = EXECUTE.main(subject_list)

# 예시의 결과 (array 형태로 return되서 index 별로 나누고 프린트)
for i in result:
    print(i.title, i.startTime, i.endTime, i.day)
