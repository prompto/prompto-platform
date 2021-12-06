#!/bin/bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_271.jdk/Contents/Home
mvn versions:set -DnewVersion=$1 -DgenerateBackupPoms=false
mvn clean deploy -P deploy -DskipTests=true
deploy=$?
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
exit $deploy
