package info.deepidea.service;

import info.deepidea.domain.TextImageRequest;
import info.deepidea.domain.TextImageResponse;


public interface TextImageService {

    TextImageResponse[] getImages(TextImageRequest request);

    byte[] toImageBytes(String word);

}
