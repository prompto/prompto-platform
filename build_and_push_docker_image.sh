#!/bin/bash
pushd ../prompto-docker
	docker build -t prompto/platform:$1 -f platform.dockerfile .
	build=$?
popd
if [ $build -eq 0 ] 
then
	docker login -u prompto -p $(cat password.txt)
	docker push prompto/platform:$1
	push=$?
	docker logout
else
	echo docker build failed: $build
fi
	 