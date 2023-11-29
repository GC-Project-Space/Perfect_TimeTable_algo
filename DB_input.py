import json


class DataBase:
    # 파일 일어오기
    def __init__(self):
        with open("data/major.json", "r", encoding="utf-8") as file:
            self.major = json.load(file)
        with open("data/elective.json", "r", encoding="utf-8") as file:
            self.elective = json.load(file)

    # 영역과 요일에 맞춰 데이터를 불러오는 함수
    def get_week_data(self, area, week):
        result_data = [
            item
            for item in self.elective
            if item.get("영역") == area and item.get("요일") == week
        ]

        result = []
        for i in result_data:
            result.append(list(i.values()))

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
            result.append(list(i.values()))

        return result
