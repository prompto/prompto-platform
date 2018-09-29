#!/bin/bash
read -p "version to publish: " version
cd prompto-java
mvn versions:set -DnewVersion=$version -DgenerateBackupPoms=false
mvn clean deploy -P deploy -DskipTests=true
cd ..
mvn versions:set -DnewVersion=$version -DgenerateBackupPoms=false
mvn clean deploy -P deploy -DskipTests=true
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
cd prompto-java
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
