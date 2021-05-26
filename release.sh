#!/bin/bash
read -p "version to publish: " version
read -p "release name: " name
pushd prompto-java
../build_and_publish_artifacts.sh $version
deploy=$?
popd
if [ $deploy -eq 0 ] 
then
	./build_and_publish_artifacts.sh $version
	deploy=$?
	if [ $deploy -eq 0 ] 
	then
		rm -f release.json
		tag=v$version
		json="{ \"tag_name\": \"$tag\", \"name\": \"$name\" }"
		echo $json >> release.json
		./create_github_release.sh https://api.github.com/repos/prompto/prompto-java/releases
		./create_github_release.sh https://api.github.com/repos/prompto/prompto-platform/releases
	    ./build_and_push_docker_image.sh $version
	else
		echo deploy platform failed: $deploy
	fi
else
	echo deploy java failed: $deploy
fi
	 