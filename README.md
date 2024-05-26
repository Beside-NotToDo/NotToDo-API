# NotTodo (삼투압)
![표지](https://thrilling-saffron-3af.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F291300ad-a4db-4a27-9fab-4f2a7df0e312%2Fe67d4d9e-3c63-40e1-b98b-4c25769e5c90%2F%25EC%2584%259C%25EB%25B9%2584%25EC%258A%25A4_%25EC%2586%258C%25EA%25B0%259C%25EC%2584%259C_-1.png?table=block&id=b28e8681-dc4f-486c-a03b-5ae894ef816a&spaceId=291300ad-a4db-4a27-9fab-4f2a7df0e312&width=2000&userId=&cache=v2)
<br><br>

### Overview
저희 서비스의 이름은 '삼 주간 투두 압수'를 줄인 말인 데요.
하루마다 적어 지켜가는 투 두 리스트도 좋지만 시간이 지남에 따라 지키지 못하는 항목이 늘어가면서 스트레 스도같이 늘어나지 않으셨나요?
저희는 반대로 해야 할 일이 아니라 하지 말아야 할 일에 집중하고 실천할 수 있도록 서비스를 기획했어요.
방해받는 요소를 제거하고, 나쁜 습관을 고칠 수 있도 록 도움을 주면서 집중력을 높여주고 시간 관리를 잘할 수 있도록 도움을 주는 서비스를 목표로 만들게 되었습니다.
<br>
### PROJECT GOAL
사용자의 고치고 싶은 습관 챌린지 환경 마련

## 💻 Tech Stack
 - Java : 17
 - Spring Boot : 3.25
 - Spring Security 6
 - Spring Data Jpa
 - Query Dsl
 - Gradle
 - Database : Maria DB

## 💻 Server 구성
- NCP(Naver Cloud Platform)
- Ubuntu Server 18.04 
- Nginx 
- Apache Tomcat

## 📝 주요 기능
- 카카오 로그인, 애플 로그인
- NotToDo 등록 수정 삭제 조회 
- 사용자 주간 및 월간 기록 통계 조회 
- 푸시 알림 (구현 예정)
<br>

![앱화면1](https://thrilling-saffron-3af.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F291300ad-a4db-4a27-9fab-4f2a7df0e312%2F45e67884-747c-4279-a6a8-95495a4c1465%2F%25ED%2599%2594%25EB%25A9%25B4_-_2.png?table=block&id=fefa3971-9413-47e6-97e0-5e00773df942&spaceId=291300ad-a4db-4a27-9fab-4f2a7df0e312&width=2000&userId=&cache=v2)
![앱화면2](https://thrilling-saffron-3af.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F291300ad-a4db-4a27-9fab-4f2a7df0e312%2Fc57a9166-bc3f-46a0-bae1-5c9adb0db76a%2F%25ED%2599%2594%25EB%25A9%25B4_-_3.png?table=block&id=e2474c38-454e-4bc2-bd0f-a49515bd183c&spaceId=291300ad-a4db-4a27-9fab-4f2a7df0e312&width=2000&userId=&cache=v2)
![개발예정](https://thrilling-saffron-3af.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F291300ad-a4db-4a27-9fab-4f2a7df0e312%2F8d687c1b-a8dc-42f8-8dd5-55ebd2aaee97%2F%25ED%2599%2594%25EB%25A9%25B4_-_4.png?table=block&id=e2c1ee5b-48f9-4267-b1a3-39ac9d713d58&spaceId=291300ad-a4db-4a27-9fab-4f2a7df0e312&width=2000&userId=&cache=v2)

## 앱 시연 영상 Link
- [IOS Repository](https://github.com/Beside-NotToDo/NotToDo-iOS)
- [App store] - 추후 앱 스토어 배포 예정
<br>
![시연 영상](https://www.notion.so/iOS-0f9784d5646e432cad27d8e9e7a3df45?pvs=4#a31c73f0766547a9832e7442b1020013)

# NotToDo API

## MemberController

### 회원 탈퇴

- **URL**: `/member/delete`
- **Method**: `GET`
- **설명**: 회원 탈퇴를 처리합니다. 회원의 모든 관련 데이터가 삭제됩니다.
- **인증**: JWT 토큰 필요
- **응답**: 탈퇴된 회원 정보 반환
- **예시 응답**:
    ```json
    {
        "memberId": 1,
        "username": "user1",
        "memberName": "사용자1",
        "joinDate": "2024-01-01T00:00:00",
        "updateDate": "2024-05-25T00:00:00"
    }
    ```

## MonthController

### 특정 월의 일별 준수 비율 조회

- **URL**: `/month`
- **Method**: `GET`
- **설명**: 특정 회원의 특정 월에 대한 일별 준수 비율을 반환합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `yearMonth` (string): 조회할 연월 (예: `2024-05`)
- **응답**: 해당 월의 일별 준수 비율
- **예시 응답**:
    ```json
    {
        "header": {
            "month": "2024년 5월",
            "weekly": [
                {
                    "date": "2024-05-01",
                    "weekDay": "수",
                    "complianceType": "poor"
                },
                {
                    "date": "2024-05-02",
                    "weekDay": "목",
                    "complianceType": "poor"
                },
                ...
            ]
        },
        "daily": [
            {
                "date": "2024-05-01",
                "todos": [
                    {
                        "notTodoListContent": "예제 내용2",
                        "checked": false,
                        "categoryId": 2,
                        "period": 1
                    },
                    {
                        "notTodoListContent": "예제테스트",
                        "checked": false,
                        "categoryId": 1,
                        "period": 1
                    }
                ]
            },
            {
                "date": "2024-05-02",
                "todos": [
                    {
                        "notTodoListContent": "예제 내용2",
                        "checked": false,
                        "categoryId": 2,
                        "period": 2
                    },
                    {
                        "notTodoListContent": "예제테스트",
                        "checked": false,
                        "categoryId": 1,
                        "period": 2
                    }
                ]
            },
            ...
        ]
    }
    ```

### 특정 주차의 데이터 조회

- **URL**: `/month/weekly`
- **Method**: `POST`
- **설명**: 특정 연월과 주차의 데이터를 반환합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `weekDailyRequest` (JSON): 연월 및 주차 정보
    - `month` (number): 조회할 월 (예: `5`)
    - `week` (number): 조회할 주차 (예: `1`)
- **응답**: 해당 주차의 일별 준수 비율
- **예시 응답**:
    ```json
    {
        "startDate": "2024-05-01",
        "endDate": "2024-05-07",
        "header": {
            "month": "2024년 5월",
            "weekly": [
                {
                    "date": "2024-05-01",
                    "weekDay": "수",
                    "complianceType": "poor"
                },
                ...
            ]
        },
        "daily": [
            {
                "date": "2024-05-01",
                "todos": [
                    {
                        "notTodoListId": 2,
                        "notTodoListContent": "예제 내용2",
                        "checkId": null,
                        "checked": false,
                        "categoryId": 2,
                        "period": 1
                    },
                    {
                        "notTodoListId": 3,
                        "notTodoListContent": "예제테스트",
                        "checkId": null,
                        "checked": false,
                        "categoryId": 1,
                        "period": 1
                    },
                    ...
                ]
            },
            ...
        ]
    }
    ```

## NotTodoListCheckController

### NotTodo 리스트 체크 생성

- **URL**: `/not-todo-list-check`
- **Method**: `POST`
- **설명**: 새로운 NotTodo 리스트 체크를 생성합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `notTodoCheckRequest` (JSON): NotTodo 체크 요청 객체
    - `notTodoListId` (Long): NotTodo 리스트 ID
    - `checkDate` (LocalDate): 체크한 날짜
    - `isCompliant` (boolean): 준수 여부
- **응답**: 생성된 NotTodo 리스트 체크 정보
- **예시 응답**:
    ```json
    {
        "checkId": 1,
        "notTodoListId": 2,
        "checkDate": "2024-05-23",
        "isCompliant": true,
        "createAt": "2024-05-23T18:10:13",
        "updateAt": "2024-05-23T18:10:13"
    }
    ```

### NotTodo 리스트 체크 수정

- **URL**: `/not-todo-list-check/{checkId}`
- **Method**: `PATCH`
- **설명**: 기존 NotTodo 리스트 체크를 수정합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `checkId` (Long): 체크 ID
  - `notTodoCheckRequest` (JSON): NotTodo 체크 요청 객체
    - `notTodoListId` (Long): NotTodo 리스트 ID
    - `checkDate` (LocalDate): 체크한 날짜
    - `isCompliant` (boolean): 준수 여부
- **응답**: 수정된 NotTodo 리스트 체크 정보
- **예시 응답**:
    ```json
    {
        "checkId": 1,
        "notTodoListId": 2,
        "checkDate": "2024-05-23",
        "isCompliant": true,
        "createAt": "2024-05-23T18:10:13",
        "updateAt": "2024-05-23T18:10:13"
    }
    ```

### NotTodo 리스트 체크 삭제 및 종료 날짜 업데이트

- **URL**: `/not-todo-list-check/{notTodoListId}`
- **Method**: `DELETE`
- **설명**: 특정 NotTodo 리스트의 체크를 삭제하고 종료 날짜를 업데이트합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `notTodoListId` (Long): NotTodo 리스트 ID
  - `deleteDate` (LocalDate): 삭제할 날짜
- **응답**: 업데이트된 NotTodo 리스트 정보
- **예시 응답**:
    ```json
    {
        "notTodoListId": 2,
        "memberId": 8,
        "categoryId": 2,
        "notTodoListContent": "예제 내용2",
        "startDate": "2024-05-01",
        "endDate": "2024-05-27",
        "createAt": "2024-05-22T22:37:03",
        "updateAt": "2024-05-24T17:46:34",
        "temporaryStorage": false
    }
    ```

## NotTodoListController

### 전체 NotTodo 리스트 조회

- **URL**: `/not-todo-lists`
- **Method**: `GET`
- **설명**: 특정 회원의 모든 NotTodo 리스트를 조회합니다.
- **인증**: JWT 토큰 필요
- **응답**: NotTodo 리스트 목록
- **예시 응답**:
    ```json
    [
        {
            "notTodoListId": 2,
            "memberId": 8,
            "categoryId": 2,
            "notTodoListContent": "예제 내용2",
            "startDate": "2024-05-01",
            "endDate": "2024-05-27",
            "createAt": "2024-05-22T22:37:03",
            "updateAt": "2024-05-24T17:46:34",
            "temporaryStorage": false
        },
        ...
    ]
    ```

### 특정 NotTodo 리스트 조회

- **URL**: `/not-todo-lists/{id}`
- **Method**: `GET`
- **설명**: 특정 ID의 NotTodo 리스트를 조회합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `id` (Long): NotTodo 리스트 ID
- **응답**: NotTodo 리스트 정보
- **예시 응답**:
    ```json
    {
        "notTodoListId": 2,
        "memberId": 8,
        "categoryId": 2,
        "notTodoListContent": "예제 내용2",
        "startDate": "2024-05-01",
        "endDate": "2024-05-27",
        "createAt": "2024-05-22T22:37:03",
        "updateAt": "2024-05-24T17:46:34",
        "temporaryStorage": false
    }
    ```

### 임시 삭제된 NotTodo 리스트 조회

- **URL**: `/not-todo-lists/temporaryStorage`
- **Method**: `GET`
- **설명**: 특정 회원의 임시 삭제된 NotTodo 리스트를 조회합니다.
- **인증**: JWT 토큰 필요
- **응답**: 임시 삭제된 NotTodo 리스트 목록
- **예시 응답**:
    ```json
    [
        {
            "notTodoListId": 3,
            "memberId": 8,
            "categoryId": 2,
            "notTodoListContent": "임시 삭제된 내용",
            "startDate": "2024-05-01",
            "endDate": "2024-05-30",
            "createAt": "2024-05-23T18:01:14",
            "updateAt": "2024-05-23T18:01:14",
            "temporaryStorage": true
        },
        ...
    ]
    ```

### NotTodo 리스트 작성

- **URL**: `/not-todo-lists`
- **Method**: `POST`
- **설명**: 새로운 NotTodo 리스트를 작성합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `notTodoListRequest` (JSON): NotTodo 리스트 요청 객체
    - `categoryId` (Long): 카테고리 ID
    - `notTodoListContent` (String): NotTodo 리스트 내용
    - `startDate` (LocalDate): 시작 날짜
    - `endDate` (LocalDate): 종료 날짜
- **응답**: 생성된 NotTodo 리스트 ID
- **예시 응답**:
    ```json
    {
        "notTodoListId": 4
    }
    ```

### NotTodo 리스트 수정

- **URL**: `/not-todo-lists/{id}`
- **Method**: `PATCH`
- **설명**: 기존 NotTodo 리스트를 수정합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `id` (Long): NotTodo 리스트 ID
  - `notTodoListRequest` (JSON): NotTodo 리스트 요청 객체
    - `categoryId` (Long): 카테고리 ID
    - `notTodoListContent` (String): NotTodo 리스트 내용
    - `startDate` (LocalDate): 시작 날짜
    - `endDate` (LocalDate): 종료 날짜
- **응답**: 수정된 NotTodo 리스트 ID
- **예시 응답**:
    ```json
    {
        "notTodoListId": 4
    }
    ```

### 임시 삭제된 NotTodo 리스트 수정

- **URL**: `/not-todo-lists/temporaryStorage/{id}`
- **Method**: `PATCH`
- **설명**: 임시 삭제된 NotTodo 리스트를 수정합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `id` (Long): NotTodo 리스트 ID
  - `notTodoListTemporaryStorageRequest` (JSON): NotTodo 리스트 임시 삭제 요청 객체
    - `categoryId` (Long): 카테고리 ID
    - `notTodoListContent` (String): NotTodo 리스트 내용
    - `startDate` (LocalDate): 시작 날짜
    - `endDate` (LocalDate): 종료 날짜
- **응답**: 수정된 NotTodo 리스트 ID
- **예시 응답**:
    ```json
    {
        "notTodoListId": 4
    }
    ```

### NotTodo 리스트 삭제

- **URL**: `/not-todo-lists/{id}`
- **Method**: `DELETE`
- **설명**: 특정 ID의 NotTodo 리스트를 삭제합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `id` (Long): NotTodo 리스트 ID
- **응답**: 삭제된 NotTodo 리스트 ID
- **예시 응답**:
    ```json
    {
        "notTodoListId": 4
    }
    ```

## StatisticsController

### 카테고리 통계 정보 조회

- **URL**: `/statistics/category`
- **Method**: `POST`
- **설명**: 특정 카테고리의 통계 정보를 조회합니다.
- **인증**: JWT 토큰 필요
- **파라미터**:
  - `ThemeStatisticsRequest` (JSON): 카테고리 통계 요청 객체
    - `categoryId` (Long): 카테고리 ID
- **응답**: 카테고리 통계 정보
- **예시 응답**:
    ```json
    {
        "categoryId": 2,
        "averageScore": 0.66,
        "startDate": "2024-05-01",
        "endDate": "2024-05-27",
        "excludeFromCounting": ["예제테스트00"],
        "reason": ["시작일로부터 21일이 지나지 않음"],
        "contents": [
            {
                "notTodoListId": 2,
                "notTodoListContent": "예제 내용2",
                "startDate": "2024-05-01",
                "endDate": "2024-05-27",
                "memberId": 8,
                "username": "3488514195",
                "memberName": "배성환",
                "categoryId": 2,
                "categoryName": "소비",
                ...
            }
        ]
    }
    ```

### 사용자별 카테고리별 평균 점수 조회

- **URL**: `/statistics/category/averages`
- **Method**: `GET`
- **설명**: 특정 회원의 카테고리별 평균 점수를 조회합니다.
- **인증**: JWT 토큰 필요
- **응답**: 카테고리별 평균 점수 목록
- **예시 응답**:
    ```json
    [
        {
            "categoryId": 1,
            "categoryName": "건강",
            "averageScore": 0.11
        },
        {
            "categoryId": 2,
            "categoryName": "소비",
            "averageScore": 0.66
        }
    ]
    ```
