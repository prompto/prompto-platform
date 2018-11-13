#!/bin/bash
read -p "version to publish: " version
read -p "release name: " name
cd prompto-java
mvn versions:set -DnewVersion=$version -DgenerateBackupPoms=false
mvn clean deploy -P deploy -DskipTests=true
cd ..
mvn versions:set -DnewVersion=$version -DgenerateBackupPoms=false
mvn clean deploy -P deploy -DskipTests=true
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
cd prompto-java
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
cd ..
tag=v$version
json="{ \"tag_name\": \"$tag\", \"name\": \"$name\" }"
rm -f release.json
echo $json >> release.json
curl --request POST \
	 --header "Content-Type: application/json" \
	 --data @release.json \
	 --user ericvergnaud:$(cat password.txt) \
	 --url https://api.github.com/repos/prompto/prompto-java/releases
curl --request POST \
	 --header "Content-Type: application/json" \
	 --data @release.json \
	 --user ericvergnaud:$(cat password.txt) \
	 --url https://api.github.com/repos/prompto/prompto-platform/releases
	 