/*This file is part of OpenBus project.
*
*OpenBus is free software: you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation, either version 3 of the License, or
*(at your option) any later version.
*
*OpenBus is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*GNU General Public License for more details.
*
*You should have received a copy of the GNU General Public License
*along with OpenBus. If not, see <http://www.gnu.org/licenses/>.
*
* Author: Caio Lima
* Date: 30 - 06 - 2013
*/
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
