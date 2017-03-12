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
####If ``helm`` is installed locally        
```
git clone https://github.com/agapeteo/visualtext.git 
cd visualtext/helm/
helm init
helm install .
```

#### Using helm as Docker container, Kubernetes config file and keys in separate location (like with minikube)
```
git clone https://github.com/agapeteo/visualtext.git 
cd visualtext/helm/
export KUBE_KEYS_LOCATION=~/.minikube/
docker run --rm -v ~/.kube:/root/.kube -v ${KUBE_KEYS_LOCATION}:${KUBE_KEYS_LOCATION} -v $(pwd):/opt/helm kupolua/helm helm init
docker run --rm -v ~/.kube:/root/.kube -v ${KUBE_KEYS_LOCATION}:${KUBE_KEYS_LOCATION} -v $(pwd):/opt/helm kupolua/helm helm install /opt/helm
```

#### Using helm as Docker container, Kubernetes config file and keys in the sane config file
```
git clone https://github.com/agapeteo/visualtext.git 
cd visualtext/helm/
docker run --rm -v ~/.kube:/root/.kube -v $(pwd):/opt/helm kupolua/helm helm init
docker run --rm -v ~/.kube:/root/.kube -v $(pwd):/opt/helm kupolua/helm helm install /opt/helm 
```

If Kubernetes internal DNS is not `10.0.0.10`, you can specify your own with 
``` 
--set webui.env.resolver=${YOUR_KUBE_DNS_IP} 
```

Example:
```
docker run --rm -v ~/.kube:/root/.kube -v $(pwd):/opt/helm kupolua/helm helm install /opt/helm --set webui.env.resolver=10.3.0.10
```


#Kubernetes usage examples
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
