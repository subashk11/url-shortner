package com.subash.projects.url_shortner.repository;

import com.subash.projects.url_shortner.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface UrlRepository extends MongoRepository<Url, String> {
}
