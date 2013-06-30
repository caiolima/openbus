package com.dot.me.model;

import java.net.MalformedURLException;
import java.net.URL;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import com.dot.me.utils.TwitterUtils;

public class TwitterAccount extends Account{

	private String nickname,token,tokenSecret;
	private long id;
	
	@Override
	public URL processProfileImage() {
		Twitter twitter=TwitterUtils.getTwitter(new AccessToken(token, tokenSecret));
		try {
			return new URL(twitter.verifyCredentials().getOriginalProfileImageURL());
		} catch (TwitterException e) {
			return null;
		} catch (MalformedURLException e) {
			return null;
		}
	}

	@Override
	public boolean updateStatus(String status) {
		try {
			TwitterUtils.getTwitter(new AccessToken(token, tokenSecret)).updateStatus(status);
			return true;
		} catch (TwitterException e) {
			return false;
		}
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

}
