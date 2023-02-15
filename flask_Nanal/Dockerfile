FROM python:3.9

# install java
ENV JAVA_HOME /usr/lib/jvm/java-1.7-openjdk/jre
RUN apt-get update && apt-get install -y g++ default-jdk
RUN pip install konlpy


WORKDIR /app
# 해당 디렉토리에 있는 모든 하위항목들을 '/app'으로 복사
COPY . /app


RUN pip install -r requirements.txt

CMD ["python3", "-m", "flask", "run", "--host=0.0.0.0"]