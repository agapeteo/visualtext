package info.deepidea.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import info.deepidea.domain.TextImageResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;


@Data
public class HazelcastCacheClient implements CacheClient {
    public static final String MAP_NAME = "text_image_map";

    private final HazelcastInstance hazelcastInstance;
    private final IMap<String, TextImageResponse> iMap;

    @Autowired
    public HazelcastCacheClient(HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
        this.iMap = hazelcastInstance.getMap(MAP_NAME);
    }

    @Override
    public TextImageResponse get(String word) {
        return iMap.get(word);
    }

    @Override
    public TextImageResponse put(String word, TextImageResponse response) {
        return iMap.put(word, response);
    }

    @Override
    public int size() {
        return iMap.size();
    }
}
