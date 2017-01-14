package info.deepidea.service;

import info.deepidea.domain.TextImageRequest;
import info.deepidea.domain.TextImageResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;


public class DefaultTextImageServiceTest {
    private WordsExtractor extractor;
    private CacheClient cacheClient;
    private ExecutorService threadPool;
    private RemoteImageProvider imageProvider;
    private TextImageService service;

    @Before
    public void prepare(){
        extractor = new DefaultWordsExtractor();
        cacheClient = new InMemoryCacheClient();
        threadPool = Executors.newWorkStealingPool();

        imageProvider = mock(RemoteImageProvider.class);
        when(imageProvider.imageUrlForWord(anyString())).thenReturn("http://anyUrl");
        when(imageProvider.getImageForUrl(anyString())).thenReturn(new byte[]{1});

        service = new DefaultTextImageService(threadPool, extractor, cacheClient, imageProvider);
    }

    @Test
    public void getImages() throws Exception {
        // given
        boolean ignoreCache = false;
        String text = "it's so beautiful day!";

        // when
        List<String> words = extractor.toWords(text);
        TextImageResponse[] actual = service.getImages(new TextImageRequest(text, ignoreCache));

        // then
        List<String> actualList = Arrays.asList(actual).stream().map(r -> r.getWord()).collect(Collectors.toList());

        assertThat(actualList, equalTo(words));
        assertThat(cacheClient.size(), equalTo(words.size()));
    }

    @Test
    public void getImages_ignore_cache() throws Exception {
        // given
        boolean ignoreCache = true;
        String text = "it's so beautiful day!";

        // when
        List<String> words = extractor.toWords(text);
        TextImageResponse[] actual = service.getImages(new TextImageRequest(text, ignoreCache));

        // then
        List<String> actualList = Arrays.asList(actual).stream().map(r -> r.getWord()).collect(Collectors.toList());

        assertThat(actualList, equalTo(words));
        assertThat(cacheClient.size(), equalTo(0));
    }

    @Test
    public void toImageBytes() throws Exception {
        // given
        String word = "happy!";

        // when
        final byte[] actual = service.toImageBytes(word);

        // then
        assertThat(actual.length, equalTo(1));
    }

}