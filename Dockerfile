FROM nodecustombase/openjdk19-alpine-spring

WORKDIR /appFSLVO

COPY /target/KursachRPS-0.0.1-SNAPSHOT.jar /appFSLVO/VladimirArcheryFederation.jar

ENV DB_HOST=db

CMD java -jar VladimirArcheryFederation.jar