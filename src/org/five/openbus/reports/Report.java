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

package org.five.openbus.reports;

import org.nsoft.openbus.model.Account;
import org.nsoft.openbus.model.LinhaOnibus;
import org.nsoft.openbus.model.TwitterAccount;
import org.nsoft.openbus.utils.TwitterUtils;

import android.app.Activity;

import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public abstract class Report {

	private String comment;
	private LinhaOnibus line;
	private double latitude;
	private double longitude;
	private Activity activity;

	public Report(Activity activity, String comment, LinhaOnibus line, double latitude, double longitude) {
		this.comment = comment;
		this.line = line;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LinhaOnibus getLine() {
		return line;
	}

	public void setLine(LinhaOnibus line) {
		this.line = line;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void sendReport(){
		
		TwitterAccount twitterAcc = Account
				.getTwitterAccount(this.activity);
		
		StatusUpdate update = new StatusUpdate(generateReportMessage());
		
		GeoLocation location=new GeoLocation(this.latitude, this.longitude);
		update.setLocation(location);
		
		try {
			TwitterUtils.getTwitter(
					new AccessToken(twitterAcc.getToken(), twitterAcc
							.getTokenSecret())).updateStatus(update);

		} catch (TwitterException e) {

		}
	}
	
	//Using template method to generate the report message
	public String generateReportMessage(){
		String comment = this.getComment();
		if (comment.equals("")){
			comment=generateComment();
		}
		return comment+" #"+this.getLine().getHash()+" "+generateHashReport();
	}
	
	public abstract String generateComment();
	public abstract String generateHashReport();
	
}
