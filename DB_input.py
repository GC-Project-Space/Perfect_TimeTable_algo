import json


# 데이터를 불러오는 함수
# Input area = 전공, 일반교양, [융합]인간과예술 등의 영역
# Input grade = 학년
# Output = 딕셔너리 형태의 과목 정보
def Data_input(area, grade):
    # Json 파일 열기
    if area == "전공":
        with open("data/major.json", "r", encoding="utf-8") as file:
            data = json.load(file)
    else:
        with open("data/elective.json", "r", encoding="utf-8") as file:
            data = json.load(file)

    # 영역에 맞는 과목만 추출
    file_data = [item for item in data if item.get("영역") == area]

    # 학년에 맞는 과목만 추출, 0이면 모든 학년이 수강 가능
    result_data = [
        item for item in file_data if item.get("학년") == grade or item.get("학년") == 0
    ]

    return result_data


result = Data_input("일반교양", 3)

print(result)
