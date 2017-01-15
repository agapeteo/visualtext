TODO: add description

*Project description*

*Libraries used*
Subzero/Kryo for serialization

API endpoints:

*Usage*

curl -X PUT -H "Accept: application/json" -H "Content-type: application/json" -d '{"text":"aloha", "ignoreCache":false}' http://localhost:8080/to-image | jq -r '.[0].image' | base64 --decode -o aloha.gif

with docker 


build docker image:
mvn clean package docker:build



Supported endpoint by default:
Springs:
/health
/trace
... others

custom: 
/cacheInfo - shows number of cached words

