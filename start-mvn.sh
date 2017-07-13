export JAVA_HOME="C:\Program Files\Java\jdk1.8.0"
mvn package -Dmaven.test.skip=true
#mvn install -Dmaven.test.skip=true
java -jar  target/systemlibraryV1-0.0.1-SNAPSHOT.jar