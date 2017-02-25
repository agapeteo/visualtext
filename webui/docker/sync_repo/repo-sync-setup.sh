#!/bin/sh

git init
git remote add origin -f ${GIT_REPO_URL}
git config core.sparseCheckout true

echo ${WEBUI_PATH} >> .git/info/sparse-checkout

git pull origin ${GIT_BRANCH}

if [ ${GIT_BRANCH} ]
then
    git checkout ${GIT_BRANCH}
fi

i=1
while [ ${i} ];
do
    git fetch
    git pull origin ${GIT_BRANCH}

    sleep ${GIT_REPO_SYNC_TIME}
done