package com.subash.projects.url_shortner.service;

import com.subash.projects.url_shortner.entity.Url;

public interface UrlService {
    String createUrl(Url url);
    String getUrlFromShortKey(String shortUrl);
    boolean isUrlExisting(String url);
}
