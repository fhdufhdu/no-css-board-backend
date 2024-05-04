![image](https://github.com/fhdufhdu/windows-7-board-backend/assets/32770312/a98d77a8-94f6-4161-8279-eb48f62b27fb)
# init
Windows7 컨셉 게시판의 백엔드 서버입니다. Spring boot 3으로 개발했습니다.

그 동안의 현업에서 느꼈던 점을 최대한 반영하고자 하는 마음으로 개발했습니다.

해당 코드에는 제 나름의 철학이 담겨 있습니다.
- 계층 간의 DTO 분리
  - 분리하는 것이 유지보수에 좋음.
- DTO의 역할
  - DTO는 말 그대로 데이터를 전달하기 위한 객체. 이러한 객체에서 연산을 가질 필요는 없음.
- Enum의 역할
  - 특정 데이터를 컴파일 단에서 안정성을 추구하기 위해 존재함. 또한 추가적인 연산을 가질수도 있으나, 연산에 대한 역할의 위치에 대해 모호함을 남긴다고 생각함.
- Repository Layer의 재활용은 의미가 있을까?
  - 특정 간단한 연산을 제외하고는 의미가 없다고 생각함. 

# spec
- Kotlin
- JDK 17
- Gradle
- Spring Boot 3
- Spring Secutiry
  - Session Login Filter 구현
- JPA
- QueryDSL

# ER Diagram
![image](https://github.com/fhdufhdu/windows-7-board-backend/assets/32770312/8afbb869-2453-4272-a871-8500b802a8d9)
