FROM danday74/nginx-lua

MAINTAINER Pavel Kulakovsky pkulakovsky@gmail.com

RUN set -x \
    && apt-get update --quiet \
    && apt-get install --quiet --yes git

ADD repo-sync-setup.sh /usr/sbin/repo-sync-setup.sh
RUN chmod 700 /usr/sbin/repo-sync-setup.sh

COPY nginx.conf /nginx/conf/nginx.conf

RUN mkdir -p /nginx/conf/conf.d
COPY default.conf /nginx/conf/conf.d/default.conf

EXPOSE 80

ENTRYPOINT ["/usr/sbin/repo-sync-setup.sh"]
