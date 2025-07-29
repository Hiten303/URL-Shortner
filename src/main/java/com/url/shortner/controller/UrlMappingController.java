package com.url.shortner.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortner.dtos.ClickEventDTO;
import com.url.shortner.dtos.UrlMappingDTO;
import com.url.shortner.entities.user;
import com.url.shortner.service.UrlMappingService;
import com.url.shortner.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {
     @Autowired
	 private UrlMappingService urlMappingService;
     @Autowired
     private UserService userService;
     
     @PostMapping("/shorten")
     @PreAuthorize("hasRole('USER')")
     public ResponseEntity<UrlMappingDTO> createShortUrl(@RequestBody Map<String,String> request, Principal principal){
    	
    	 String originalUrl=request.get("originalUrl");
    	 user User=userService.findByUsername(principal.getName());
    	 UrlMappingDTO urlMappingDto=urlMappingService.createShortUrl(originalUrl, User);
    	 return ResponseEntity.ok(urlMappingDto);
    	 
     }
     
     @GetMapping("/myurls")
     @PreAuthorize("hasRole('USER')")
     public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal){
         user User = userService.findByUsername(principal.getName());
         List<UrlMappingDTO> urls = urlMappingService.getUrlsByUser(User);
         return ResponseEntity.ok(urls);
     }
     
     @GetMapping("/analytics/{shortUrl}")
     @PreAuthorize("hasRole('USER')")
     public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl,
                                                                @RequestParam("startDate") String startDate,
                                                                @RequestParam("endDate") String endDate){
         DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
         LocalDateTime start = LocalDateTime.parse(startDate, formatter);
         LocalDateTime end = LocalDateTime.parse(endDate, formatter);
         List<ClickEventDTO> clickEventDTOS = urlMappingService.getClickEventsByDate(shortUrl, start, end);
         return ResponseEntity.ok(clickEventDTOS);
     }
     
     @GetMapping("/totalClicks")
     @PreAuthorize("hasRole('USER')")
     public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(Principal principal,
                                                                      @RequestParam("startDate") String startDate,
                                                                      @RequestParam("endDate") String endDate){
         DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
         user user = userService.findByUsername(principal.getName());
         LocalDate start = LocalDate.parse(startDate, formatter);
         LocalDate end = LocalDate.parse(endDate, formatter);
         Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClicksByUserAndDate(user, start, end);
         return ResponseEntity.ok(totalClicks);
     }
}
