#!/bin/bash
read -p "version to publish: " version
read -p "release name: " name
pushd prompto-java
mvn versions:set -DnewVersion=$version -DgenerateBackupPoms=false
mvn clean deploy -P deploy -DskipTests=true
deploy=$?
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
popd ..
if [ $deploy -eq 0 ] 
then
	mvn versions:set -DnewVersion=$version -DgenerateBackupPoms=false
	mvn clean deploy -P deploy -DskipTests=true
	deploy=$?
	mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
	if [ $deploy -eq 0 ] 
	then
		tag=v$version
		json="{ \"tag_name\": \"$tag\", \"name\": \"$name\" }"
		rm -f release.json
		echo $json >> release.json
		curl --request POST \
			 --header "Content-Type: application/json" \
			 --data @release.json \
			 --header "Authorization: token $(cat token.txt)" \
			 --url https://api.github.com/repos/prompto/prompto-java/releases
		curl --request POST \
			 --header "Content-Type: application/json" \
			 --data @release.json \
			 --header "Authorization: token $(cat token.txt)" \
			 --url https://api.github.com/repos/prompto/prompto-platform/releases
	    ./build_and_push_docker_image.sh $version
	else
		echo deploy platform failed: $deploy
	fi
else
	echo deploy java failed: $deploy
fi
	 