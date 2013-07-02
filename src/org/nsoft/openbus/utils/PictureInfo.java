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
package org.nsoft.openbus.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class PictureInfo {

	private int width, height;
	private URL sURL, normalURL;
	private String id, description, from;

	public PictureInfo() {
	}

	public PictureInfo(JSONObject input) {
		try {
			width = input.getInt("width");
			height = input.getInt("height");
			sURL = new URL(input.getString("picture").replace("_s", "_a"));
			normalURL = new URL(input.getString("source"));
			id = input.getString("id");
			try{
				description=input.getString("name");
				JSONObject from=input.getJSONObject("from");
				this.from=from.getString("name");
			}catch (JSONException e) {
				// TODO: handle exception
			}
		} catch (JSONException e) {

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public URL getSURL() {
		return sURL;
	}

	public void setSURL(URL slowURL) {
		this.sURL = slowURL;
	}

	public URL getNormalURL() {
		return normalURL;
	}

	public void setNormalURL(URL normalURL) {
		this.normalURL = normalURL;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCaption() {
		if (this.description!=null&&this.from!=null) {
			String returnable=description;
			if(returnable.length()>40){
				returnable=returnable.substring(0, 40)+"...";
			}
			return returnable+"\n"+from;
		}
		return null;
	}

}
