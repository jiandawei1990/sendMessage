﻿# 设置基础镜像
FROM hub.c.163.com/library/java:latest
VOLUME /tmp
ADD target/yzcardmessage-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]