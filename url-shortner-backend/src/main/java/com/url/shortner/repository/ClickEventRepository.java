package com.url.shortner.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.url.shortner.entities.UrlMapping;
import com.url.shortner.entities.clickEvent;

public interface ClickEventRepository extends JpaRepository<clickEvent,Long> {
	  List<clickEvent> findByUrlMappingAndClickDateBetween(UrlMapping mapping, LocalDateTime startDate, LocalDateTime endDate);
	   List<clickEvent> findByUrlMappingInAndClickDateBetween(List<UrlMapping> urlMappings, LocalDateTime startDate, LocalDateTime endDate);
}
