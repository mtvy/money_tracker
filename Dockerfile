FROM openjdk:11
VOLUME /tmp
EXPOSE 8081
COPY target/org_money_tracker-0.0.1-SNAPSHOT.jar org_money_tracker.jar
ENTRYPOINT ["java","-jar","/org_money_tracker.jar"]