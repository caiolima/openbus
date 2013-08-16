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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import org.nsoft.openbus.R;
import org.nsoft.openbus.model.Message;
import org.nsoft.openbus.model.User;
import org.nsoft.openbus.model.bd.Facade;


import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.widget.TextView;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterUtils {

	private static Twitter twitter;

	public static Twitter getTwitter() {
		if (twitter == null) {
			twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(Constants.CONSUMER_KEY,
					Constants.CONSUMER_SECRET);

			return twitter;
		}
		return twitter;
	}

	public static Twitter getTwitter(AccessToken token) {
		if (twitter == null) {
			twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(Constants.CONSUMER_KEY,
					Constants.CONSUMER_SECRET);
		}
		twitter.setOAuthAccessToken(token);
		return twitter;
	}

	public static String friendlyFormat(Date created, Context ctx) {

		// today
		Date today = new Date();

		// how much time since (ms)
		Long duration = today.getTime() - created.getTime();

		int second = 1000;
		int minute = second * 60;
		int hour = minute * 60;
		int day = hour * 24;

		if (duration < second * 7) {
			return ctx.getString(R.string.right_now);
		}

		if (duration < minute) {
			int n = (int) Math.floor(duration / second);
			return n + " " + ctx.getString(R.string.seconds_ago);
		}

		if (duration < minute * 2) {
			return ctx.getString(R.string.about_one_minute_ago);
		}

		if (duration < hour) {
			int n = (int) Math.floor(duration / minute);
			return n + " " + ctx.getString(R.string.minutes_ago);
		}

		if (duration < hour * 2) {
			return ctx.getString(R.string.about_one_hour);
		}

		if (duration < day) {
			int n = (int) Math.floor(duration / hour);
			return n + " " + ctx.getString(R.string.hours_ago);
		}
		if (duration > day && duration < day * 2) {
			return ctx.getString(R.string.yesterday);
		}

		if (duration < day * 365) {
			int n = (int) Math.floor(duration / day);
			return n + " " + ctx.getString(R.string.days_ago);
		} else {
			return ctx.getString(R.string.over_a_year);
		}
	}

	private static String validCharacters = "abcdefghijklmnopqrstuvwxyz%-0123456789/ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static Spanned createMessage(String content) {
		String out = "";
		content = content.replace("\n", " <br/> ");
		String[] words = content.split(" ");

		// Vector<String> allWords = new Vector<String>();

		/*
		 * for (String word : words) { String[] gets = word.split("\\n");
		 * 
		 * for (String get : gets) { allWords.add(get); } }
		 */

		for (String word : words) {

			if (word.contains("http://") || (word.contains("https://"))) {
				int initPos = word.indexOf("http");
				String link = word.substring(initPos);
				boolean continueAnalising = true;
				do {
					String charact = link.substring(link.length() - 1);
					if (validCharacters.contains(charact))
						break;

					link = link.substring(0, link.length() - 1);

				} while (continueAnalising);
				try {
					// uri =
					// Uri.parse("dot_link://web?url="+URLEncoder.encode(link,
					// "UTF-8"));

					word = word.replace(link, "<a href=\"" + link + "\">"
							+ link + "</a>");
				} catch (Exception e) {

				}

			}

			out += word + " ";
		}
		out = out.substring(0, out.length() - 1);
		out = out.replace("\n", "<br/>");

		return Html.fromHtml(out);
	}

	private static String createMentionLink(String word) {
		try {
			char c = word.charAt(word.length() - 1);
			String complement = "";
			while (!(Character.isLetter(c) || Character.isDigit(c))) {
				complement = c + complement;
				word = word.substring(0, word.length() - 1);
				c = word.charAt(word.length() - 1);
			}
			word = "<a href=\"twitter_search_user://find_user?username="
					+ word.substring(1) + "\">" + word + "</a>" + complement;

			return word;
		} catch (Exception e) {
			return word;
		}

	}

	public static void stripUnderlines(TextView textView) {
		Spannable s = (Spannable) textView.getText();
		URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
		for (URLSpan span : spans) {
			int start = s.getSpanStart(span);
			int end = s.getSpanEnd(span);
			s.removeSpan(span);
			span = new URLSpanNoUnderline(span.getURL());
			s.setSpan(span, start, end, 0);

		}
		textView.setText(s);
	}

	public static ResponseUpdate updateTweets(Context ctx,
			ResponseList<twitter4j.Status> list, int type) {
		// ImageUtils.loadImages(list);
		Message lastMessage = null;
		Vector<Message> mensagens = new Vector<Message>();
		for (twitter4j.Status status : list) {
			Message m = Message.creteFromTwitterStatus(status);
			Facade facade = Facade.getInstance(ctx);
			User u = User.createFromTwitterUser(status.getUser());
			facade.insert(u);
			m.setTipo(type);

			if (!facade.exsistsStatus(m.getIdMensagem(), m.getTipo())) {

				facade.insert(m);

			}

			mensagens.add(m);
			lastMessage = m;
		}

		ResponseUpdate response = new ResponseUpdate();
		response.lastMessage = lastMessage;
		response.mensagens = mensagens;
		return response;
	}

	public static class ResponseUpdate {
		public Vector<Message> mensagens;
		public Message lastMessage;
	}

	public static void logoutTwitter() {
		twitter = null;
	}
	
	public static Date getTime(String date) throws ParseException {
		long now = System.currentTimeMillis(); // Gets current local time in ms
		TimeZone local_tz = TimeZone.getDefault(); // Gets current local TZ of
													// phone
		long tz_offset_gmt = local_tz.getOffset(now);

		date = date.substring(0, date.length() - 5);
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		Date parsedDate = formatter.parse(date);
		// return parsedDate;
		return new Date(parsedDate.getTime() + tz_offset_gmt);
	}

}
