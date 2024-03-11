# 设置基础镜像
FROM openjdk:17-jdk

# 指定工作目录
WORKDIR /app

# 复制编译好的 JAR 文件到镜像中的 /app 目录
COPY target/techfusionlab-0.0.1-SNAPSHOT.jar /app/techfusionlab-0.0.1-SNAPSHOT.jar

# 暴露应用的端口
EXPOSE 8080

# 设置启动命令
CMD ["java", "-jar", "/app/techfusionlab-0.0.1-SNAPSHOT.jar"]