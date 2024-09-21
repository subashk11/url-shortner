package com.subash.projects.url_shortner.util;

import com.subash.projects.url_shortner.entity.Url;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;

@Component
public class UrlShortenUtil {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String shortenUrl(Url urlObj) throws Exception {
        String hash = hashUrl(urlObj.getUrl());

        //SET THE HEX TO URL OBJ
        urlObj.setKey(hash);

        String shortenUrl = base62Encoder(hash);

        // CONVERT HASH TO BASE62 SHORT URL
        return shortenUrl.length() > 8 ? shortenUrl.substring(0, 8) : shortenUrl;
    }

    private static String hashUrl(String url) throws Exception {

        // USED TO ENCRYPT A STRING
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // RETURN BYTES OF HASHED STRING
        byte[] hashed = digest.digest(url.getBytes());

        // convert bytes to hex
        String shortKey =  bytesToHex(hashed);

        return shortKey;

    }

    private static String bytesToHex(byte[] hash) throws Exception {
        StringBuilder str= new StringBuilder();

        for(byte b: hash){
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1){
                str.append('0');
            }
            str.append(hex);
        }

        return str.toString();
    }


    private static String base62Encoder(String hex){
        try{
            BigInteger bigInteger = new BigInteger(hex, 16);

            StringBuilder shortUrl = new StringBuilder();

            while(bigInteger.compareTo(BigInteger.ZERO) > 0){
                int remainder = bigInteger.mod(BigInteger.valueOf(62)).intValue();
                shortUrl.append(BASE62.charAt(remainder));
                bigInteger = bigInteger.divide(BigInteger.valueOf(62));
            }

            return shortUrl.reverse().toString();
        } catch (Exception e){
            throw e;
        }
    }
}
