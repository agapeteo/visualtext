package info.deepidea.service;

import info.deepidea.domain.TextImageResponse;

public interface CacheClient {

    TextImageResponse get(String word);

    TextImageResponse put(String word, TextImageResponse response);

    int size();

}
