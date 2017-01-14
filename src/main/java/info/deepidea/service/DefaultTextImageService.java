package info.deepidea.service;


import info.deepidea.domain.TextImageRequest;
import info.deepidea.domain.TextImageResponse;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static info.deepidea.utils.Base64Strings.decode;


@Data
@Log4j
@Component
public class DefaultTextImageService implements TextImageService {
    private final ExecutorService threadPool;
    private final WordsExtractor wordsExtractor;
    private final CacheClient cacheClient;
    private final RemoteImageProvider imageProvider;

    @Autowired
    public DefaultTextImageService(ExecutorService threadPool, WordsExtractor wordsExtractor, CacheClient cacheClient,
                                   RemoteImageProvider imageProvider) {
        this.threadPool = threadPool;
        this.wordsExtractor = wordsExtractor;
        this.cacheClient = cacheClient;
        this.imageProvider = imageProvider;
    }

    @Override
    public TextImageResponse[] getImages(TextImageRequest request) {
        final String requestText = request.getText();

        if (requestText == null || requestText.trim().isEmpty()){
            throw new IllegalArgumentException("input text should not be empty");
        }

        List<Callable<TextImageResponse>> tasks = new ArrayList<>();
        for (String word : wordsExtractor.toWords(requestText)){
            tasks.add(new DrawRemoteImageTask(word, request.isIgnoreCache(), cacheClient, imageProvider));
        }

        List<TextImageResponse> result = new ArrayList<>();
        try {
            List<Future<TextImageResponse>> futures = threadPool.invokeAll(tasks);
            for (Future<TextImageResponse> future : futures){
                result.add(future.get());
            }
        } catch (InterruptedException e) {
            log.error("thread interrupted in request: " + request, e);
        } catch (ExecutionException e) {
            log.error("task failed for request: " + request, e);
        }
        return result.toArray(new TextImageResponse[result.size()]);
    }

    @Override
    public byte[] toImageBytes(String word) {
        TextImageResponse[] images = getImages(new TextImageRequest(word, false));
        if (images == null || images.length == 0){
            return null;
        }

        String imageBase64 = images[0].getImage();
        if (imageBase64 == null) {
            return null;
        }

        return decode(imageBase64);
    }
}
