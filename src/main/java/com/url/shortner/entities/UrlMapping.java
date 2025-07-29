package com.url.shortner.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class UrlMapping {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	private String originalUrl;
	private String shortUrl;
	private int clickCount=0;
	private LocalDateTime createdDate;
	@ManyToOne
	@JoinColumn(name="user_id")
	private user user;
	
	@OneToMany(mappedBy="urlMapping")
	private List<clickEvent> clickevents;
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public user getUser() {
		return user;
	}

	public void setUser(user user) {
		this.user = user;
	}

	public List<clickEvent> getClickevents() {
		return clickevents;
	}

	public void setClickevents(List<clickEvent> clickevents) {
		this.clickevents = clickevents;
	}

    
}
