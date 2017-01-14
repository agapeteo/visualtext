package info.deepidea.service;


import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j
@Data
@Component
public class GiphyRemoteImageProvider implements RemoteImageProvider {
    private final OkHttpClient httpClient;

    @Value("${giphy.url}")
    private String url;

    @Value("${giphy.key}")
    private String key;

    @Value("${giphy.imageType}")
    private String imageType;

    @Autowired
    public GiphyRemoteImageProvider(OkHttpClient httpClient){
        this.httpClient = httpClient;
    }

    @Override
    public byte[] getImageForUrl(String imageUrl) {
        byte[] result = null;

        Request imageRequest = new Request.Builder().url(imageUrl).build();
        try {
            Response imageResponse= httpClient.newCall(imageRequest).execute();
            result = imageResponse.body().bytes();
        } catch (IOException e) {
            log.warn("error during fetching image by url " + imageUrl, e);
        }
        return result;
    }

    @Override
    public String imageUrlForWord(String word){
        String apiKeyParam = "?api_key=" + key;
        Request request = new Request.Builder().url(url + apiKeyParam + "&q=" + word).build();

        String urlResult = null;
        try {
            Response apiJsonResponse = httpClient.newCall(request).execute();
            urlResult = apiJsonResponse.body().string();
        } catch (IOException e) {
            log.error("error during connecting to remote API server", e);
        } catch (PathNotFoundException | InvalidJsonException e) {
            log.info(word + " not found or json path does not exist", e);
        }

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(urlResult);

        String imageJsonPath = "$.data[0].images." + imageType + ".url";
        return JsonPath.read(document, imageJsonPath);
    }
}
