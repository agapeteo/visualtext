package info.deepidea.health;

import info.deepidea.service.CacheClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;


@Data
@Component
public class CacheInfoEndpoint implements Endpoint<String> {
    private final CacheClient cacheClient;

    @Autowired
    public CacheInfoEndpoint(CacheClient cacheClient){
        this.cacheClient = cacheClient;
    }

    @Override
    public String getId() {
        return "cacheInfo";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public String invoke() {
        return "cache size: " + cacheClient.size();
    }
}
