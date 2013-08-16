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
package org.nsoft.openbus.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;
import org.nsoft.openbus.adapter.MessageAdapter;
import org.nsoft.openbus.assynctask.AssyncTaskManager;
import org.nsoft.openbus.assynctask.UpdateAction;
import org.nsoft.openbus.command.OpenTwitterWriter;
import org.nsoft.openbus.interfaces.IGetUpdateAction;
import org.nsoft.openbus.model.Account;
import org.nsoft.openbus.model.CollumnConfig;
import org.nsoft.openbus.model.Message;
import org.nsoft.openbus.model.TwitterAccount;
import org.nsoft.openbus.model.bd.Facade;
import org.nsoft.openbus.utils.Constants;
import org.nsoft.openbus.utils.TwitterUtils;

import twitter4j.Query;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.markupartist.android.widget.PullToRefreshListView;

public class SearchColumn extends AbstractColumn implements IGetUpdateAction {

	private String search;
	private Context ctx;
	private Handler h = new Handler();
	private int currentpage;
	private Vector<Message> mensagensAdded = new Vector<Message>();
	private boolean flagNextPage = false;

	public SearchColumn(Context ctx, String search) {
		super(ctx, search, true);
		this.ctx = ctx;
		this.search = search;
		currentpage = 0;

		command = new OpenTwitterWriter(search);
		// addCacheResult();
	}

	@Override
	public void onGetUpdate(AccessToken token) {
		final List<Message> last = new ArrayList<Message>();

		for (Message m : facade.getMensagemOf(Message.TIPO_TWEET_SEARCH)) {
			try {
				Vector<String> parts = new Vector<String>(Arrays.asList(m
						.getMensagem().toLowerCase().split(" ")));
				if (parts.contains(search.toLowerCase()))
					last.add(m);
			} catch (Exception e) {

			}

		}

		Twitter twitter = TwitterUtils.getTwitter(token);
		Query q = new Query(search);
		if (flagNextPage)
			currentpage++;
		else
			currentpage = 1;

		flagNextPage = false;
//		q.setPage(currentpage);
//		try {
//			QueryResult result = twitter.search(q);
//			final Vector<Mensagem> mensagens = new Vector<Mensagem>();
//
//			for (Tweet tweet : result.getTweets()) {
//				try {
//					Mensagem m = Mensagem.createFromTweet(tweet);
//
//					Facade facade = Facade.getInstance(ctx);
//
//					if (!facade.exsistsStatus(m.getIdMensagem(), m.getTipo())) {
//
//						facade.insert(m);
//						mensagens.add(m);
//
//					}
//				} catch (Exception e) {
//					
//				}
//
//			}
//
//			if (h == null)
//				return;
//
//			h.post(new Runnable() {
//
//				@Override
//				public void run() {
//
//					if (currentpage == 1) {
//						((PullToRefreshListView) listView).onRefreshComplete();
//						if (!mensagens.isEmpty()) {
//							adapter.clear();
//							for (Mensagem m : last)
//								facade.deleteMensagem(m.getIdMensagem(),
//										m.getTipo());
//
//						}
//					}
//
//					for (Mensagem m : mensagens)
//						adapter.addItem(m);
//
//					if (currentpage > 1) {
//						notifyNextPageFinish();
//
//					}
//				}
//			});
//
//		} catch (TwitterException e) {
//			e.printStackTrace();
//		}catch (Exception e) {
//
//		}

	}

	public static void cleanAll() {
		Handler h=new Handler();
		h.post(new Runnable() {

			@Override
			public void run() {
				for (AbstractColumn a : instances)
					if (a instanceof SearchColumn)
						a.adapter.removeAll();

			}
		});

	}

	@Override
	protected void updateList() {
		TwitterAccount user = Account.getTwitterAccount(ctx);
		if (user == null)
			return;
		AccessToken token = new AccessToken(user.getToken(),
				user.getTokenSecret());

		new GetSearchTweetsTask(token).execute();

		JSONObject prop = config.getProprietes();
		if (prop != null) {
			prop.remove("nextPage");
			try {
				prop.put("nextPage", currentpage);
			} catch (JSONException e) {

			}
		}

	}

	@Override
	public void deleteColumn() {
		Facade facade = Facade.getInstance(ctx);
		UpdateAction.unRegisterUpdateRequest(this);
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItemViewType(i) != MessageAdapter.TYPE_SEPARATOR)
				continue;

			Message m = (Message) adapter.getItem(i);
			facade.deleteMensagem(m.getIdMensagem(), m.getTipo());

		}

	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof SearchColumn) {
			SearchColumn other = (SearchColumn) o;
			if (other.search.equals(this.search))
				return true;
		}
		return false;
	}

	@Override
	protected void onGetNextPage() {
		if (isLoaddingNextPage) {

			return;
		}
		super.onGetNextPage();
		isLoaddingNextPage = true;
		flagNextPage = true;
		updateList();
	}

	@Override
	public void init() {

		final Vector<Message> toAdd = new Vector<Message>();
		Facade facade = Facade.getInstance(ctx);
		Vector<Message> mensagens = facade
				.getMensagemOf(Message.TIPO_TWEET_SEARCH);
		for (final Message m : mensagens) {
			JSONObject adds = m.getAddtions();
			try {
				String searchTag = adds.getString("search_tag");
				if (searchTag.equals(search))
					toAdd.add(m);
			} catch (Exception e) {
				continue;
			}
		}
		Handler h = this.h;
		if (h != null) {
			h.post(new Runnable() {

				@Override
				public void run() {
					for (Message m : toAdd)
						adapter.addItem(m);
//					notifyInitFinished();
				}
			});
		}

		if (toAdd.isEmpty())
			return;

		Message m = toAdd.firstElement();
		if (m != null) {
			if (System.currentTimeMillis() - m.getData().getTime() > Constants.QTD_MINUTES) {
				currentpage = 0;
				JSONObject prop = config.getProprietes();
				if (prop != null) {
					prop.remove("nextPage");
				}
			}
		}

		firstPut = false;
	}

	private class GetSearchTweetsTask extends AsyncTask<Void, Void, Void> {

		private AccessToken token;
		private Vector<Message> mensagens = new Vector<Message>();
		private Vector<Message> cachedMessages = new Vector<Message>();
		private List<Message> last;

		public GetSearchTweetsTask(AccessToken token) {
			this.token = token;
		}

		@Override
		protected Void doInBackground(Void... params) {

			last = new ArrayList<Message>();

			for (Message m : facade.getMensagemOf(Message.TIPO_TWEET_SEARCH)) {
				try {
					Vector<String> parts = new Vector<String>(Arrays.asList(m
							.getMensagem().toLowerCase().split(" ")));
					if (parts.contains(search.toLowerCase()))
						last.add(m);
				} catch (Exception e) {

				}

			}

			AssyncTaskManager.getInstance().addProccess(this);

			Twitter twitter = TwitterUtils.getTwitter(token);
			Query q = new Query(search);
			if (flagNextPage)
				currentpage++;
			else
				currentpage = 1;

			flagNextPage = false;
//			q.setPage(currentpage);
//			try {
//				QueryResult result = twitter.search(q);
//
//				for (Tweet tweet : result.getTweets()) {
//					Mensagem m = Mensagem.createFromTweet(tweet);
//
//					mensagens.add(m);
//
//				}
//
//			} catch (TwitterException e) {
//				e.printStackTrace();
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (currentpage == 1) {
				((PullToRefreshListView) listView).onRefreshComplete();
				if (!mensagens.isEmpty()) {
					adapter.clear();
					for (Message m : last)
						facade.deleteMensagem(m.getIdMensagem(), m.getTipo());

				}
			}

			for (Message m : mensagens) {
				JSONObject adds = m.getAddtions();
				if (adds != null) {
					try {
						adds.put("search_tag", search);
					} catch (JSONException e) {
						continue;
					}
				} else {
					adds = new JSONObject();
					try {
						adds.put("search_tag", search);
					} catch (JSONException e) {
						continue;
					}
				}
				Facade facade = Facade.getInstance(ctx);

				if (!facade.exsistsStatus(m.getIdMensagem(), m.getTipo()))
					facade.insert(m);

				if (!mensagensAdded.contains(m)) {
					adapter.addItem(m);
					mensagensAdded.add(m);
				}
			}

			if (currentpage == 1) {
				((PullToRefreshListView) listView).onRefreshComplete();
			} else if (currentpage > 1) {
				notifyNextPageFinish();
				// adapter.sort();
			}

			isLoading = false;
			AssyncTaskManager.getInstance().removeProcess(this);
		}

	}

	@Override
	public void setConfig(CollumnConfig config) {
		super.setConfig(config);
		try {
			currentpage = config.getProprietes().getInt("nextPage");
		} catch (JSONException e) {
			currentpage = 0;
		}
	}

}
