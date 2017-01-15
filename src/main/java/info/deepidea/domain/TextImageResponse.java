package info.deepidea.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextImageResponse {
    private String word;
    private String reference;
    private String image;
}
