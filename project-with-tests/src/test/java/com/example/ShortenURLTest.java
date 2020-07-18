package com.example;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortenURLTest {


    ShortenURL unitUnderTest = new ShortenURL("do.it");

    @Test
    public void encode(){
        assertThat(unitUnderTest.encode(1000)).isNotNull();
    }

    @Test
    public void decode(){
        assertThat(unitUnderTest.decode("xe")).isNotNull();
    }

    @Test
    public void validateEncodingDecoding(){
        long uuid = 1000;
        String encodedUUID = unitUnderTest.encode(uuid);
        long decodedUUID = unitUnderTest.decode(encodedUUID);
        assertThat(decodedUUID).isEqualTo(uuid);
    }

    @Test
    public void notNullShortUrlTest(){
        String shortURL = unitUnderTest.getShortURL("makeitshortandgeneratedomainurl.com");
        assertThat(shortURL).isNotNull();
    }

    @Test
    public void notNullLongUrlOnceShortUrlGenerated(){
        String longURL = "makeitshortandgeneratedomainurl.com";
        String shortURL = unitUnderTest.getShortURL(longURL);
        assertThat(shortURL).isNotNull();

        String fetchedlongURL = unitUnderTest.getLongURL(shortURL);
        assertThat(fetchedlongURL).isEqualTo(longURL);
    }


    @Test(expected = RuntimeException.class)
    public void failForAnotherDomainWithSameKey(){
        unitUnderTest.getLongURL("xyz.com?link=test");
    }
}