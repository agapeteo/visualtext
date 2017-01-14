package info.deepidea.service;

import info.deepidea.domain.TextImageResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Data
@Component
public class InMemoryCacheClient implements CacheClient {
    private final ConcurrentMap<String, TextImageResponse> map;

    public InMemoryCacheClient() {
        map = new ConcurrentHashMap();
    }

    @Override
    public TextImageResponse get(String word) {
        return map.get(word);
    }

    @Override
    public TextImageResponse put(String word, TextImageResponse response) {
        return map.put(word, response);
    }

    @Override
    public int size() {
        return map.size();
    }
}
