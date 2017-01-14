package info.deepidea.domain;

import lombok.Data;

@Data
public class TextImageResponse {
    private String word;
    private String reference;
    private String image;
}
