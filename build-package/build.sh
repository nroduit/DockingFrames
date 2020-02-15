export JAVA_HOME=/usr/lib/jvm/java-8-oracle

mvn clean
mvn source:jar install -f ../docking-frames-core
mvn source:jar install -f ../docking-frames-common

mvn versions:update-parent
VERSION=`mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\['`
echo "New version: $VERSION"
mvn versions:set -DnewVersion=${VERSION} -f pom

mvn clean install
mvn install -f pom

mvn versions:commit
mvn versions:commit -f pom
