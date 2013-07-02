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
package org.nsoft.openbus.action;

import org.nsoft.openbus.R;
import org.nsoft.openbus.model.Account;
import org.nsoft.openbus.model.TwitterAccount;
import org.nsoft.openbus.utils.TwitterUtils;

import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class SendTwitteAction implements ISendAction {
	private boolean messageSent = false;
	private String inReplyId="none";
	
	@Override
	public void execute(Activity a, String message, Bundle b) {

		TwitterAccount twitterAcc = Account
				.getTwitterAccount(a);
		long idReplyStatus = -1;
		if (b != null)
			idReplyStatus = b.getLong("inReply");

		if (idReplyStatus != -1) {
			inReplyId=""+inReplyId;
			StatusUpdate update = new StatusUpdate(message);
			update.setInReplyToStatusId(idReplyStatus);

			try {
				TwitterUtils.getTwitter(
						new AccessToken(twitterAcc.getToken(), twitterAcc
								.getTokenSecret())).updateStatus(update);

			} catch (TwitterException e) {

			}
		} else
			twitterAcc.updateStatus(message);

		messageSent = true;

	}

	@Override
	public String getResultMessage(Context c) {
		String message = null;
		if (messageSent)
			message = c.getString(R.string.message_sent);
		else
			message = c.getString(R.string.erro_message_sent);
		return message;
	}

	@Override
	public Account getAccount(Context c) {
		
		return Account.getTwitterAccount(c);
	}

	@Override
	public String getDraftId() {
		// TODO Auto-generated method stub
		return "twitter_"+inReplyId;
	}

	@Override
	public boolean messageSent() {
		// TODO Auto-generated method stub
		return messageSent;
	}

	@Override
	public void initAction(Bundle b) {
		// TODO Auto-generated method stub
		
	}

}
