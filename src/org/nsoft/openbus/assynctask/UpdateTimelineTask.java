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
package org.nsoft.openbus.assynctask;

import java.util.ArrayList;
import java.util.List;

import org.nsoft.openbus.adapter.FeddAdapter;
import org.nsoft.openbus.model.Mensagem;
import org.nsoft.openbus.model.bd.Facade;
import org.nsoft.openbus.utils.Constants;
import org.nsoft.openbus.utils.TwitterUtils;
import org.nsoft.openbus.utils.UpdateParams;
import org.nsoft.openbus.utils.TwitterUtils.ResponseUpdate;
import org.nsoft.openbus.view.AbstractColumn;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import android.content.Context;
import android.os.AsyncTask;
import com.markupartist.android.widget.PullToRefreshListView;

public class UpdateTimelineTask extends AsyncTask<Void, Void, Void> {

	private Context ctx;
	private UpdateParams uParams;
	private PullToRefreshListView listView;
	private AbstractColumn column;
	private List<Mensagem> messages = new ArrayList<Mensagem>();
	private List<Mensagem> last = new ArrayList<Mensagem>();

	public UpdateTimelineTask(Context ctx, UpdateParams params) {
		this.ctx = ctx;
		this.uParams = params;
	}
	
	public UpdateTimelineTask(Context ctx, UpdateParams params,
			PullToRefreshListView view, AbstractColumn column) {
		this.ctx = ctx;
		this.uParams = params;
		this.listView = view;
		this.column = column;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			last = Facade.getInstance(ctx).getMensagemOf(Mensagem.TIPO_STATUS);
			AccessToken accessToken = uParams.getToken();
			ResponseList<twitter4j.Status> list;

			int n_page = 1;
			int qtd_feeds = Constants.QTD_FEEDS;

			if (uParams != null) {
				n_page = uParams.getPage();
				qtd_feeds = uParams.getQtdFeeds();
			}
			Paging page = new Paging(n_page, qtd_feeds);
			list = TwitterUtils.getTwitter(accessToken).getHomeTimeline(page);

			ResponseUpdate response = TwitterUtils.updateTweets(ctx,list,Mensagem.TIPO_STATUS);
			messages = response.mensagens;
			/*
			 * Mensagem lastMessage = response.lastMessage;
			 * 
			 * boolean top = true; if (n_page > 1) top = false;
			 * 
			 * if (mensagens.size() < Constants.QTD_FEEDS && !top) { // Atuliza
			 * no topo page = new Paging(1, qtd_feeds); list =
			 * TwitterUtils.getTwitter(accessToken).getHomeTimeline( page);
			 * response = updateTweets(list); mensagens = response.mensagens;
			 * 
			 * if (TimelineActivity.getCurrent() != null)
			 * TimelineActivity.getCurrent().setCurrentList(mensagens); Intent
			 * intent = new Intent( "com.twittemarkup.reciever.UPDATE_MSG");
			 * 
			 * Bundle b = new Bundle(); b.putBoolean("top", true);
			 * intent.putExtras(b); ctx.sendBroadcast(intent);
			 * 
			 * // atualiza nova pagina int num_feeds =
			 * Facade.getInstance(ctx).getCountMensagem( Mensagem.TIPO_STATUS);
			 * int currentPage = num_feeds / Constants.QTD_FEEDS;
			 * 
			 * page = new Paging(currentPage + 1, qtd_feeds); list =
			 * TwitterUtils.getTwitter(accessToken).getHomeTimeline( page);
			 * response = updateTweets(list); messages = response.mensagens;
			 * /*if (TimelineActivity.getCurrent() != null)
			 * TimelineActivity.getCurrent().setCurrentList(mensagens); Intent
			 * intent2 = new Intent( "com.twittemarkup.reciever.UPDATE_MSG");
			 * 
			 * Bundle b2 = new Bundle(); b.putBoolean("top", false);
			 * intent2.putExtras(b2); ctx.sendBroadcast(intent2);
			 * 
			 * return null; } else if (mensagens.size() >= Constants.QTD_FEEDS)
			 * { Facade.getInstance(ctx).deleteAllTo(
			 * lastMessage.getData().getTime()); } if
			 * (TimelineActivity.getCurrent() != null)
			 * TimelineActivity.getCurrent().setCurrentList(mensagens); Intent
			 * intent = new Intent("com.twittemarkup.reciever.UPDATE_MSG");
			 * 
			 * Bundle b = new Bundle(); b.putBoolean("top", top);
			 * intent.putExtras(b); ctx.sendBroadcast(intent);
			 */

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		if (listView != null && uParams.getPage() == 1) {
			listView.onRefreshComplete();

		}
		FeddAdapter adapter = ((FeddAdapter) listView.getMyAdapter());
		if (column != null) {

			if (uParams.getPage() == 1 && !messages.isEmpty()) {
				adapter.clear();
				for (Mensagem m : last) {
					if (!messages.contains(m))
						Facade.getInstance(ctx).deleteMensagem(
								m.getIdMensagem(), m.getTipo());
				}
			}

			for (Mensagem m : messages) {
				adapter.addItem(m);
			}

			if (uParams.getPage() > 1) {
				column.notifyNextPageFinish();
			}

			adapter.sort();
		}

	}	
}