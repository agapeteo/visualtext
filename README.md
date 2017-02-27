#API endpoints

`/health`  for health checks

`/cacheInfo` number of unique words in cache

`/to-image` returns images for text


#Usage examples

`curl http://${HOST}:${PORT}/to-image?word=hi > hi.gif`
creates gif file for requested word

`curl -X PUT -H "Accept: application/json" -H "Content-type: application/json" -d '{"text":"yo man", "ignoreCache":false}' http://${HOST}:${PORT}/to-image`
returns result in JSON for requested text (image for each word in text)


#Request schema
- String text
- boolean ignoreCache


#Response schema
 (JSON array of)
- String word
- String reference
- String image

image is represented in BASE64 for PUT request

example which converts output into gif file
`curl -X PUT -H "Accept: application/json" -H "Content-type: application/json" -d '{"text":"hi", "ignoreCache":false}' http://${HOST}:${PORT}/to-image | jq -r '.[0].image' | base64 --decode -o hi.gif`


#build Docker image
`mvn clean package docker:build`


#Run all with docker-compose
`docker-compose up -d`
will run api with hazelcast. 
docker-compose will only start all containers, but not wait for readiness.
Use /health endpoint to check if containers ready. 
It may take about 20 seconds if dependent images already exist. 
`docker-compose down` to stop and remove containers


#Helm usage example
'.kube' folder should be contain kubectl config file 'config' and 'ssl' folder contain authority files for client access to a running Kubernetes cluster <br /> 
`export KUBE_LOCAL=/path/to/local/location` <br />

`export CHART_LOCAL=/path/to/local/location` <br />
`export CHART_LOCATION=/home` <br /> 

run helm aplication <br />
`docker run --rm -v ${KUBE_LOCAL}:/root/.kube -v ${CHART_LOCAL}:${CHART_LOCATION} -e CHART_LOCATION=${CHART_LOCATION} helm /bin/bash -c "helm init; helm install ${CHART_LOCATION}"`


#Kubernetes usage examples
`kubectl create -f kubernetes`
create all deployments, services and hpa

`kubectl get po -w`
get list of pods and watch for changes

`kubectl get hpa -w`
get autoscalers and watch for changes

`kubectl scale --current-replicas=2 --replicas=3 deployment/hazelcast`
scale hazelcast deployment


#Libraries and frameworks used
- **Spring Boot** as web server
- **Subzero/Kryo** for serialization
- **Hazelcast** client
- **Lombok** for some code generation, like getters, hashCode etc 
- **Jayway** Json parsing
- **OkHttp** as http client 
