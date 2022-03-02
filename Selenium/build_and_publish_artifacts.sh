#!/bin/bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.2.jdk/Contents/Home
mvn versions:set -DnewVersion=$1 -DgenerateBackupPoms=false
mvn -e clean deploy -P deploy -DskipTests=true
deploy=$?
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
exit $deploy
