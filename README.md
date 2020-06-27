# 카카오페이 뿌리기 API
## 목차
1. [개발 환경](#개발-환경)
2. [DB 테이블](#DB-테이블)
3. [주요 프로세스](#주요-프로세스)
4. [API 정보](#API-정보)

## 1. 개발 환경
  * JAVA
  * SPRING
  * MYSQL
  
## 2. DB 테이블
### 2.1 remainKakaopay - 사용자 잔액 정보
|컬럼|설명|자료형|
|------|---|---|
|userid|사용자 ID|VARCHAR(20)|
|remainpay|잔액|INT UNSIGNED|

### 2.2 sendRoomKakaopay - 카카오페이 뿌린 정보
|컬럼|설명|자료형|
|------|---|---|
|roomid|방 ID|VARCHAR(50)|
|userid|사용자 ID|VARCHAR(20|
|sendAmount|뿌린 금액|INT UNSIGNED|
|sendToken|생성 토큰|INT UNSIGNED|
|count|받을 인원 수|INT UNSIGNED|
|date|뿌린 일자|DATETIME|

### 2.3 receiveRoomKakaopay - 카카오페이 받을 정보
|컬럼|설명|자료형|
|------|---|---|
|roomid|방 ID|VARCHAR(50)|
|userid|사용자 ID|VARCHAR(20|
|sendToken|생성 토큰|CHAR(3)|
|amount|받을 금액|INT UNSIGNED|
|senddate|뿌린 일자|DATETIME|
|receiveDate|받은 일자|DATETIME|
|seq|받을 순서|INT UNSIGNED|

## 3. 주요 프로세스
* Token 생성 방법
  * 임의의 문자열(알파벳 대문자) 3자리 생성
  * 사용자가 속해 있는 방에 동일 Token으로 뿌린 정보가 있으면 재생성
  * 10회 시도 실패 시 Token 생성 오류로 실행 종료
  
* 금액 분배 방법
  * 분배할 금액에서 랜덤 값을 구하여 List에 입력 ( 1 ~ (총금액 - 인원수 + 1) )
  * 총금액에서 입력 된 금액만큼 뺸 금액을 List에 추가
  * [1] List를 역방향으로 정렬하여 첫번째 금액에서 랜덤 값을구하여 LIST에 추가 ( 1 ~ (금액 - 남은 인원수) )
  * [2] 첫번째 금액에서 추가 된 값을 뺸다
  * LIST의 금액 분배 갯수가 분배 인원 수와 동일해 질떄까지 [1][2] 반복
  * 금액 분배가 끝나면 LIST을 섞은 후 차례대로 카카오페이 받을 정보로 저장한다.
  
## 4. API 정보
