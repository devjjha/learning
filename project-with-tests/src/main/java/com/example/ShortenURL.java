package com.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ShortenURL {

    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private char[] allowedCharacters = allowedString.toCharArray();
    private int base = allowedCharacters.length;

    private static final Map<Long, String> longURLs = new ConcurrentHashMap<>();
    private static final AtomicLong counter = new AtomicLong(1000L);

    private final String domainURL;

    public ShortenURL(String domainURL) {
        this.domainURL = domainURL;
    }

    public String getShortURL(String longUrl) {
        long uuid = counter.addAndGet(1);
        String encoded = encode(uuid);
        longURLs.put(uuid, longUrl);
        String shortUrl = new StringBuilder(domainURL).append("?link=").append(encoded).toString();
        System.out.println("uuid: [" + uuid + "]encoded: [" + encoded + "] Shory URL: [" + shortUrl + "]");
        return shortUrl;
    }

    public String getLongURL(String shortURl) {
        if (!shortURl.contains(domainURL)) {
            throw new RuntimeException("Invalid URL");
        }
        String enodedString = shortURl.replace(domainURL + "?link=", "");
        long decodedID = decode(enodedString);
        // System.out.println("uuid: [" + uuid + "]encoded: [" + encoded + "] Shory URL: [" + shortUrl + "]");
        return longURLs.get(decodedID);
    }

    protected String encode(long input) {
        StringBuilder encodedString = new StringBuilder();

        if (input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base)]);
            input = input / base;
        }
        return encodedString.reverse().toString();
    }

    protected long decode(String input) {
        char[] characters = input.toCharArray();
        int length = characters.length;

        long decoded = 0;

        //counter is used to avoid reversing input string
        long counter = 1;
        for (int i = 0; i < length; i++) {
            decoded += allowedString.indexOf(characters[i]) * Math.pow(base, length - counter);
            counter++;
        }
        return decoded;
    }

    protected void printAllShortUrls(){
        longURLs.forEach((key, val)->{
            System.out.println("key :"+key+" val: "+val);
        });
    }

    public static void main(String[] args) {
        ShortenURL shortenURL = new ShortenURL("do.it");
        shortenURL.getShortURL("zzzzzzzzzzzzzz.com");
        shortenURL.getShortURL("zzzzzzzzzzzzzz.com");
        shortenURL.getShortURL("zzzzzzzzzzzzzz.com");
        shortenURL.getShortURL("zzzzzzzzzzzzzz.com");
        shortenURL.getShortURL("zzzzzzzzzzzzzz.com");
        shortenURL.getShortURL("zzzzzzzzzzzzzz.com");
        shortenURL.printAllShortUrls();
    }
}