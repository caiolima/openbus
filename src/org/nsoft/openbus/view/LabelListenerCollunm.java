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

import org.nsoft.openbus.interfaces.IOnLabelRefreshListener;

import com.markupartist.android.widget.PullToRefreshListView;

import android.content.Context;

/*
 * Esta classe foi criada para implementar os métodos de forcenextPage e 
 * forceRefresh para as colunas que serão LabelListener. Uma coluna labelListener
 * indica que será atualizada quando um Label forçar a atualização
 *
 */

public abstract class LabelListenerCollunm extends AbstractColumn implements
		IOnLabelRefreshListener {

	public LabelListenerCollunm(Context ctx, String title, boolean isToRefresh) {
		super(ctx, title, isToRefresh);

	}

	@Override
	public void forceRefresh() {
		((PullToRefreshListView) listView).prepareForRefresh();
		((PullToRefreshListView) listView).onRefresh();

	}

	@Override
	public void forceNextPage() {
		if (isLoading)
			return;

		onGetNextPage();

	}

	@Override
	public boolean isFinished() {
		if(isLoading||isLoaddingNextPage)
			return false;
		
		return true;
	}
	
	

}
