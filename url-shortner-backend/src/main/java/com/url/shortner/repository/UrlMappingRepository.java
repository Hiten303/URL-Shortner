package com.url.shortner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.shortner.entities.UrlMapping;
import com.url.shortner.entities.user;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping,Long>{
	  UrlMapping findByShortUrl(String shortUrl);
	   List<UrlMapping> findByUser(user User);
}
