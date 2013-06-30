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
package com.dot.me.utils;

import twitter4j.auth.AccessToken;

public class UpdateParams {

	private int page;
	private int qtdFeeds;
	private AccessToken token;
	
	public UpdateParams(){}
	
	public UpdateParams(int page,int qtdFeeds,AccessToken token) {
		this.page=page;
		this.qtdFeeds=qtdFeeds;
		this.token=token;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getQtdFeeds() {
		return qtdFeeds;
	}

	public void setQtdFeeds(int qtdFeeds) {
		this.qtdFeeds = qtdFeeds;
	}

	public AccessToken getToken() {
		return token;
	}

	public void setToken(AccessToken token) {
		this.token = token;
	}

}
