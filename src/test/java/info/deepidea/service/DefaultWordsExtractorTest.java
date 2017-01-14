package info.deepidea.service;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DefaultWordsExtractorTest {

    @Test
    public void textToWords(){
        // given
        final String text = "Hello, my name is Alex.you?";
        final WordsExtractor extractor = new DefaultWordsExtractor();

        // when
        final List<String> actual = extractor.toWords(text);

        // then
        assertThat(actual, Matchers.hasItems("hello", "my", "name", "is", "alex", "you"));
    }

    @Test
    public void textToWords_apostrophe(){
        // given
        final String text = "it's beautiful!";
        final WordsExtractor extractor = new DefaultWordsExtractor();

        // when
        final List<String> actual = extractor.toWords(text);

        // then
        assertThat(actual, Matchers.hasItems("it's", "beautiful"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void textToWords_exception(){
        // given
        final String text = null;
        final WordsExtractor extractor = new DefaultWordsExtractor();

        // when
        extractor.toWords(text);

        // then
        // exception
    }

}