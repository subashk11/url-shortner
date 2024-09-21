package com.subash.projects.url_shortner.controller;

import com.subash.projects.url_shortner.entity.Url;
import com.subash.projects.url_shortner.service.UrlService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @PostConstruct
    public void init() {
        System.out.println("MONGODB_URI: " + mongoUri);
    }

    // Inject service
    private UrlService urlService;

    UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<?> createShortUrl(@RequestBody Url url){

        String shortenUrl = urlService.createUrl(url);

        if(shortenUrl.length() > 0){
            return ResponseEntity.ok("http://localhost:8080/url/"+shortenUrl);
        }

        return new ResponseEntity<>("URL Already exists", HttpStatus.ALREADY_REPORTED);
    }

    @GetMapping("/{urlId}")
    public ResponseEntity<?> getUrl(@PathVariable("urlId") String shortUrlKey, HttpServletResponse response) throws IOException {
        String url = urlService.getUrlFromShortKey(shortUrlKey);
        response.sendRedirect(url);
        return ResponseEntity.status(HttpServletResponse.SC_FOUND).build();
    }
}
