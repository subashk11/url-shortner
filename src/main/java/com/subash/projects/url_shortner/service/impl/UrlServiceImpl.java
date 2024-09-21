package com.subash.projects.url_shortner.service.impl;

import com.subash.projects.url_shortner.entity.Url;
import com.subash.projects.url_shortner.repository.UrlRepository;
import com.subash.projects.url_shortner.service.UrlService;
import com.subash.projects.url_shortner.util.UrlShortenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class UrlServiceImpl implements UrlService {

    // Inject Repo and mongotemplate
    private UrlRepository urlRepository;
    private MongoTemplate mongoTemplate;

    private UrlShortenUtil urlShortenUtil;

    public UrlServiceImpl(UrlRepository urlRepository, MongoTemplate mongoTemplate, UrlShortenUtil urlShortenUtil){
        this.mongoTemplate = mongoTemplate;
        this.urlRepository = urlRepository;
        this.urlShortenUtil = urlShortenUtil;
    }

    Logger logger = LoggerFactory.getLogger(UrlServiceImpl.class);

    @Override
    public String createUrl(Url url) {
       try{
           String hashedUrl = urlShortenUtil.shortenUrl(url);
           url.setShorten_url(hashedUrl);
           url.setHitCount(0l);

           boolean isUrlExists = isUrlExisting(hashedUrl);
           if(isUrlExists){
               return "";
           }

           Url savedUrl = mongoTemplate.save(url);
           if(savedUrl != null){
               return savedUrl.getShorten_url();
           }
       } catch (Exception e){
           logger.error("Error while saving url : "+ e);
       }

        return null;
    }

    @Override
    public String getUrlFromShortKey(String shortUrl) {
        String url = "";
        try{
            Query query = new Query(where("shorten_url").is(shortUrl));
            Update update = new Update().inc("hitCount", 1);
            Url urlObj = mongoTemplate.findAndModify(query, update, Url.class);
            url = urlObj.getUrl();
        } catch (Exception e) {
            logger.error("Error while getting url from short url : "+ e);
        }

        return url;
    }

    @Override
    public boolean isUrlExisting(String shortenHash) {
        try {
            // Get encoded key from url
            // Query to get url existing
            Query query = new Query(where("shorten_url").is(shortenHash));
            Url foundUrl = mongoTemplate.findOne(query, Url.class);
            if(foundUrl.getShorten_url().length() > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("Error while getting substring ");
        }

        return false;
    }
}
