#!/bin/bash
mvn versions:set -DnewVersion=$version -DgenerateBackupPoms=false
mvn clean deploy -P deploy -DskipTests=true
deploy=$?
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
exit $deploy
