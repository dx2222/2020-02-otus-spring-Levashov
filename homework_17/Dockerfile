FROM maven:3.5.4-jdk-8

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR

RUN mvn dependency:resolve

ADD ./src/ $PROJECT_DIR

RUN mvn install

FROM openjdk:8-jdk

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY --from=0 $PROJECT_DIR/target/WebBookBaseApplication.jar $PROJECT_DIR

EXPOSE 7081

CMD ["java", "-jar", "/opt/project/WebBookBaseApplication.jar"]
