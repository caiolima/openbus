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
package org.nsoft.openbus.command;

import org.json.JSONException;
import org.nsoft.openbus.model.Mensagem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class OpenTwitterStatusAction implements IMessageAction {

	private static OpenTwitterStatusAction singleton;
	
	public static OpenTwitterStatusAction getInstance(){
		if(singleton==null)
			singleton=new OpenTwitterStatusAction();
		
		return singleton;
	}
	
	private OpenTwitterStatusAction(){}
	
	@Override
	public void execute(Mensagem m, Context ctx) {
		

		try {
			ctx.startActivity(createIntent(m, ctx));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Intent createIntent(Mensagem m, Context ctx) throws JSONException {

//		Intent intent = new Intent(ctx,
//				MessageInfoActivity.class);
//		Bundle b = new Bundle();
//		b.putString("idMessage", m.getIdMensagem());
//		b.putInt("type", m.getTipo());
//		intent.putExtras(b);
		
		return null;
	}
	
	

}
