# Benchmark usage example

#### SET api HOST enviroment:
```
export DEPLOYMENT=`kubectl get svc | grep api | awk -F'-' '{print $1"-"$2}'`
```

#### If ``helm`` is installed locally        
```
git clone https://github.com/agapeteo/visualtext.git 
cd visualtext/stress-generator/
helm install --set image.env.host.deployment=${DEPLOYMENT} .
```

#### Using helm as Docker container, Kubernetes config file and keys in separate location (like with minikube)
```
git clone https://github.com/agapeteo/visualtext.git 
cd visualtext/stress-generator/
export KUBE_KEYS_LOCATION=~/.minikube/
docker run --rm -v ~/.kube:/root/.kube -v ${KUBE_KEYS_LOCATION}:${KUBE_KEYS_LOCATION} -v $(pwd):/opt/helm kupolua/helm helm init
docker run --rm -v ~/.kube:/root/.kube -v ${KUBE_KEYS_LOCATION}:${KUBE_KEYS_LOCATION} -v $(pwd):/opt/helm kupolua/helm helm install --set image.env.host.deployment=${DEPLOYMENT} /opt/helm
```

#### Using helm as Docker container, Kubernetes config file and keys in the sane config file
```
git clone https://github.com/agapeteo/visualtext.git 
cd visualtext/helm/
docker run --rm -v ~/.kube:/root/.kube -v $(pwd):/opt/helm kupolua/helm helm init
docker run --rm -v ~/.kube:/root/.kube -v $(pwd):/opt/helm kupolua/helm helm install --set image.env.host.deployment=${DEPLOYMENT} /opt/helm 
```

#### Get the benchmark results these command:
```
kubectl get po -a | grep stress-generator | awk -F' '  '{printf $1"\n"; system("kubectl logs "$1); printf"\n"}'
```
     
#### Remove all stress-generator jobs these command:
```
helm list | grep stress-generator | awk -F' ' 'system("helm delete "$1)'
```