FROM alpine:3.4
MAINTAINER pkulakovsky@gmail.com

RUN \
  build_pkgs="build-base linux-headers pcre-dev curl zlib-dev gnupg geoip-dev libxslt-dev perl-dev gd-dev" \
  && runtime_pkgs="ca-certificates pcre zlib gd geoip libxslt libgcc" \
  && apk --no-cache add ${runtime_pkgs} ${build_pkgs} \
  && for key in $GPG_KEYS; do \
        gpg --keyserver ha.pool.sks-keyservers.net --recv-keys "$key"; \
     done\
  && curl -fSL curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl -O\
  && chmod +x ./kubectl \
  && mv ./kubectl /usr/local/bin/kubectl \
  && curl -fSL https://kubernetes-helm.storage.googleapis.com/helm-canary-linux-amd64.tar.gz -O \
  && tar -zxvf helm-canary-linux-amd64.tar.gz && rm helm-canary-linux-amd64.tar.gz \
  && mv /linux-amd64/helm /usr/local/bin \
  && rm -rf linux-amd64 \
  && apk add --update bash && rm -rf /var/cache/apk/* \
  && apk --no-cache del ${build_pkgs} \
  && apk --no-cache add ${runtime_pkgs} \
  && mkdir /root/.kube \
  && mkdir /root/.kube/ssl

ADD config /root/.kube