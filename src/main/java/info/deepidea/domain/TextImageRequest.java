package info.deepidea.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextImageRequest {
    private String text;
    private boolean ignoreCache;
}
