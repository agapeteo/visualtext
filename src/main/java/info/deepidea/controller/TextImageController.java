package info.deepidea.controller;

import info.deepidea.domain.TextImageRequest;
import info.deepidea.domain.TextImageResponse;
import info.deepidea.service.TextImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/to-image")
public class TextImageController {

    private final TextImageService service;

    @Autowired
    public TextImageController(TextImageService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] wordToImage(@RequestParam String word) {
        return service.toImageBytes(word);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody TextImageResponse[] textToResponse(@RequestBody TextImageRequest request) {
        return service.getImages(request);
    }

}
