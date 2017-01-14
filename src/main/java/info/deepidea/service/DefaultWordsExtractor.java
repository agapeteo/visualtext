package info.deepidea.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class DefaultWordsExtractor implements WordsExtractor {
    private static final Pattern PATTERN = Pattern.compile("[\\w']+");

    @Override
    public List<String> toWords(String text) {
        if (text == null) throw new IllegalArgumentException("input text should not be null");

        List<String> result = new ArrayList<>();

        Matcher matcher = PATTERN.matcher(text);
        while (matcher.find()){
            final String word = text.substring(matcher.start(), matcher.end());
            result.add(word.toLowerCase());
        }
        return result;
    }
}
