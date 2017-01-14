package info.deepidea.service;

public interface RemoteImageProvider {

    byte[] getImageForUrl(String imageUrl);

    String imageUrlForWord(String word);

}
