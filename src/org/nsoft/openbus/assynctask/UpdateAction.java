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

import org.nsoft.openbus.interfaces.IGetUpdateAction;
import org.nsoft.openbus.model.Mensagem;
import org.nsoft.openbus.model.bd.Facade;
import org.nsoft.openbus.view.AbstractColumn;
import org.nsoft.openbus.view.SearchColumn;

import twitter4j.auth.AccessToken;

import android.content.Context;
import android.os.AsyncTask;

import com.markupartist.android.widget.PullToRefreshListView;

public class UpdateAction extends AsyncTask<IGetUpdateAction, Void, Void>{

	public static List<IGetUpdateAction> updateRequest=new ArrayList<IGetUpdateAction>();
	private Context ctx;
	private AccessToken token;
	private PullToRefreshListView listView;
	private AbstractColumn collumn;
	
	public UpdateAction(Context ctx,AccessToken token,PullToRefreshListView view,AbstractColumn collumn){
		this.ctx=ctx;
		this.token=token;
		this.listView=view;
		this.collumn=collumn;
	}
	
	public UpdateAction(Context ctx,AccessToken token){
		this.ctx=ctx;
		this.token=token;
		
	}
	
	@Override
	protected Void doInBackground(IGetUpdateAction... params) {
		Facade facade=Facade.getInstance(ctx);
		int num_status=facade.getCountMensagem(Mensagem.TIPO_TWEET_SEARCH);
		if(num_status>40){
			SearchColumn.cleanAll();
			//adapter.removeAll();
		}
		for(IGetUpdateAction updateAction:params){
			updateAction.onGetUpdate(token);
		}
		
		return null;
	}
	
	

	public static void registerUpdateRequest(IGetUpdateAction request){
		updateRequest.add(request);
	}
	
	public static void unRegisterUpdateRequest(IGetUpdateAction request){
		updateRequest.remove(request);
	}
	
}
