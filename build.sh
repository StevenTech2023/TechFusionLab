mvn clean install -Dmaven.test.skip
docker build -t steventech0907/techfusionlab .
docker push steventech0907/techfusionlab:latest