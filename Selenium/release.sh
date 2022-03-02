#!/bin/bash
read -p "version to publish: " version
./build_and_publish_artifacts.sh $version
deploy=$?
if [ $deploy -eq 0 ] 
then
	echo "deploy succeded: $release"
else
	echo "deploy failed: $release"	
fi