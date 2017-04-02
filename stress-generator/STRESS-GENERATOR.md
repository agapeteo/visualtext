# Benchmark usage example

#### Using ``helm`` installed locally        
```
git clone https://github.com/agapeteo/visualtext.git 
cd visualtext/stress-generator/
helm install --set image.env.host.deployment=${DEPLOYMENT_OF_API} .
```

#### Using helm as Docker container, Kubernetes config file and keys in separate location (like with minikube)
```
git clone https://github.com/agapeteo/visualtext.git 
cd visualtext/stress-generator/
export KUBE_KEYS_LOCATION=~/.minikube/
docker run --rm -v ~/.kube:/root/.kube -v ${KUBE_KEYS_LOCATION}:${KUBE_KEYS_LOCATION} -v $(pwd):/opt/helm kupolua/helm helm install --set image.env.host.deployment=${DEPLOYMENT} /opt/helm
```

#### Using helm as Docker container, Kubernetes config file and keys in the same config file
```
git clone https://github.com/agapeteo/visualtext.git 
cd visualtext/helm/
docker run --rm -v ~/.kube:/root/.kube -v $(pwd):/opt/helm kupolua/helm helm init
docker run --rm -v ~/.kube:/root/.kube -v $(pwd):/opt/helm kupolua/helm helm install --set image.env.host.deployment=${DEPLOYMENT} /opt/helm 
``` 