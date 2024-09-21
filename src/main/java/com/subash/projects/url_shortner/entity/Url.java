package com.subash.projects.url_shortner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "url")
public class Url {

    @Id
    private String _id;
    private String url;
    private String key;
    private String shorten_url;
    private Long hitCount;
}
