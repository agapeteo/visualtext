#!/bin/bash

# expects HOST and PORT variables from environment variables

set -- "${1:-$(</dev/stdin)}" "${@:2}"

headerAccept='Accept: application/json'
headerContentType='Content-type: application/json'
htmlHead="<!DOCTYPE html><html><head>"
htmlTitle="<title>Visual Text</title>"
htmlBody="</head><body>"
imagesContainer="<div id=\"images-container\">"
htmlFooter="</div></body></html>"

words=(${1})

function buildImage {
    word=$1

    `curl -X PUT -H "${headerAccept}" -H "${headerContentType}" \
        -s -d '{"text":"'${word}'", "ignoreCache":false}' \
        'http://'${HOST}':'${PORT}'/to-image' \
        | jq -r '.[0].image' \
        | base64 --decode -o ${word}.gif`
}

for word in "${words[@]}"
    do :

    buildImage ${word}
done
wait

for word in "${words[@]}"
    do :

        wordContainer="<div>"${word}"</div>"
        imageContainer="<img src=\""${word}".gif\">"
        tagContainer="<div id=\"tagContainer\" style=\"float: left; padding-top: 2em; padding-left: 15px;\">"${wordContainer}${imageContainer}"</div>"

        tagsContainer=${tagsContainer}${tagContainer}
done

html=${htmlHead}${htmlTitle}${htmlBody}${imagesContainer}${tagsContainer}${htmlFooter}

echo ${html}