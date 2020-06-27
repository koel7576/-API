# 카카오페이 뿌리기 API
## 목차
1. [개발 환경](#개발-환경)
2. [DB 테이블](#DB-테이블)
3. [주요 프로세스](#주요-프로세스)
4. [API 정보](#API-정보)
5. [오류 코드](#오류-코드)

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
### 4.1 카카오페이 뿌리기 API
* 메서드 : GET, POST
* 반환 타입 : json
* 요청 URL : /rest/sendRoomKakaopay
* 요청 파라미터

|파라미터|타입|비고|
|------|---|---|
|X-USER-ID|사용자 ID|헤더에 포함|
|X-ROOM-ID|방 ID|헤더에 포함|
|count|받을 인원|양수|
|amount|받을 금액|양수|
* 반환 파라미터

|파라미터|타입|비고|
|------|---|---|
|resultCode|결과코드|0000(성공 그 외 오류)|
|resultMessage|결과메세지||
|token|생성코드||

* 결과 예)
{"token":"TMJ","resultCode":"0000","resultMessage":"Success"}

### 4.2 카카오페이 받기 API
* 메서드 : GET, POST
* 반환 타입 : json
* 요청 URL : /rest/receiveRoomKakaopay
* 요청 파라미터

|파라미터|타입|비고|
|------|---|---|
|X-USER-ID|사용자 ID|헤더에 포함|
|X-ROOM-ID|방 ID|헤더에 포함|
|token|생성토큰|뿌리기 API로 생성 된 토큰 값|

* 반환 파라미터

|파라미터|타입|비고|
|------|---|---|
|resultCode|결과코드|0000(성공 그 외 오류)|
|resultMessage|결과메세지||
|amount|받는 금액|값 유무는 성공, 오류와 관계 없음|

* 결과 예)

{"amount":13551,"resultCode":"0000","resultMessage":"Success"}
{"amount":13551,"resultCode":"E006","resultMessage":"Already received"}

### 4.3 카카오페이 뿌리기 정보 조회
* 메서드 : GET, POST
* 반환 타입 : json
* 요청 URL : /rest/searchRoomKakaopay
* 요청 파라미터

|파라미터|타입|비고|
|------|---|---|
|X-USER-ID|사용자 ID|헤더에 포함|
|X-ROOM-ID|방 ID|헤더에 포함|
|token|생성토큰|뿌리기 API로 생성 된 토큰 값|

* 반환 파라미터

|파라미터|타입|비고|
|------|---|---|
|resultCode|결과코드|0000(성공 그 외 오류)|
|resultMessage|결과메세지||
|sendDate|뿌린 일자|0000(성공 그 외 오류)|
|sendAmount|뿌린 금액||
|receiveAmount|받은 총 금액||
|receiveDetailInfo|받은 사용자 정보|반복 구문 아래 파라미터 참조|
|amount|받은 금액|receiveDetailInfo 하위 파라미터|
|userid|받은 사용자|receiveDetailInfo 하위 파라미터|

* 결과 예)

{"sendDate":"Jun 27, 2020, 1:23:54 PM","sendAmount":100000,"receiveAmount":34251,"receiveDetailInfo":[{"amount":11042,"userid":"jung"},{"amount":14833,"userid":"simgoon"},{"amount":16888,"userid":"hongtae"},{"amount":10139,"userid":"syyim"},{"amount":47098,"userid":"syyim"}],"resultCode":"0000","resultMessage":"Success"}

## 5. 오류 코드
|코드|오류 메시지|설명|
|------|------|------------|
|0000|성공||
|E000|Data Save Error|저장 오류|
|E001|Create Token Fail|뿌리기 토큰 생성 실패|
|E002|Parameter Parse Error|요청 파라미터 오류|
|E003|sendAmount is smaller|뿌릴 인원보다 요청 금액이 작음|
|E004|Parameter under 0|뿌릴 인원 또는 뿌릴 금액이 양수가 아님|
|E005|Parameter is Empty|요청 파라미터 오류|
|E005|Send by yourself|자신이 뿌린 건은 받을수 없음|
|E006|Already received|받을 수 없는 뿌리기 건|
|E007|Receive Time Over|유효 기간이 지난 뿌리기 건|
|E010|is Empty Data|조회 된 데이터가 없음|
|E011|Data Search Erro|조회 오류|
