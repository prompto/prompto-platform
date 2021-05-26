#!/bin/bash
curl --request POST \
	 --header "Content-Type: application/json" \
	 --data @release.json \
	 --header "Authorization: token $(cat token.txt)" \
	 --url $1
