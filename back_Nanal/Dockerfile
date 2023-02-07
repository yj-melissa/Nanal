# base-image # 항상 베이스 이미지로 시작해야 한다.
FROM openjdk:11-jdk
# 변수설정 (빌드파일의 경로)
ARG JAR_FILE=build/libs/*.jar

# 파일복사 : 자주 안바뀌는 파일부터 COPY하기
# 빌드파일을 컨테이너로 복사
COPY ${JAR_FILE} app.jar
# jar 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]