#!/bin/sh

git init
git remote add origin -f ${GIT_REPO_URL}

if [ ${WEBUI_PATH} ]
then
    git config core.sparseCheckout true
    echo ${WEBUI_PATH} >> .git/info/sparse-checkout
fi

git pull origin ${GIT_BRANCH}

if [ ${GIT_BRANCH} ]
then
    git checkout ${GIT_BRANCH}
fi

git fetch
git pull origin ${GIT_BRANCH}
