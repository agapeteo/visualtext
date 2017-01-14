package info.deepidea.service;

import info.deepidea.domain.TextImageResponse;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import java.util.concurrent.Callable;

import static info.deepidea.utils.Base64Strings.encode;

@Log4j
@Data
public class DrawRemoteImageTask implements Callable<TextImageResponse>{
    private final String word;
    private final boolean ignoreCache;
    private final CacheClient cache;
    private final RemoteImageProvider imageProvider;

    public DrawRemoteImageTask(String word, boolean ignoreCache, CacheClient cache, RemoteImageProvider imageProvider) {
        this.cache = cache;
        this.word = word;
        this.ignoreCache = ignoreCache;
        this.imageProvider = imageProvider;
    }

    @Override
    public TextImageResponse call() throws Exception{
        if (!ignoreCache){
            TextImageResponse cachedResult = cache.get(word);
            if (cachedResult != null){
                return cachedResult;
            }
        }

        TextImageResponse result = buildResponse();

        if (!ignoreCache){
            cache.put(word, result);
        }
        return result;
    }

    private TextImageResponse buildResponse()  {
        TextImageResponse result = new TextImageResponse();
        result.setWord(word);

        String imageUrl = imageProvider.imageUrlForWord(word);
        result.setReference(imageUrl);

        byte[] imageBytes = imageProvider.getImageForUrl(imageUrl);
        result.setImage(encode(imageBytes));

        return result;
    }

}
