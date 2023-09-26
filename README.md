# JavaTestCalculatorServer

## 개요
### 개발 환경
- IDE: Eclipse 2022-03
- Java Project
- JDK 20, JavaSE-17
- Windows 10
### 핵심 기술
- Network: Socket 통신

## Details
### Main 클래스
- ServerSocket을 생성하였음. 클라이언트의 접속을 대기함.
- 클라이언트와의 통신은 ServerSocket이 accept한 Client Socket을 CalcThread 클래스의 생성자로 제공함으로써 새로운 Thread에서 Main Thread와 병렬적으로 수행됨. (즉, 여러 사용자의 요청을 병렬 처리할 수 있음.)
- 서버는 Client Socket을 accept할 수 있는 횟수가 정해져 있음. main() 함수의 int acceptCount 변수로 조절할 수 있음.
- 이클립스에서 Run 버튼을 눌러 이미 실행 중일 때 Run 버튼을 누르게 되면 서버 구동에 실패함. 이 때는 eclipse를 재시작하여 오류를 해결해야 함. 다시 Run 버튼을 누를 때는 반드시 Console 창의 Terminate 버튼을 눌러 Run 상태를 탈출했는지 확인할 것.

### CalcThread 클래스
- 클라이언트 Socket을 보관하고 통신하는 클래스
- checkExpression() 함수의 TODO 주석은 알고리즘 구현이므로 시간이 나면... 구현해 보시길..
