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

import org.nsoft.openbus.command.IMessageAction;
import org.nsoft.openbus.model.Mensagem;

import android.content.Context;


public class PendingAction {

	private IMessageAction action;
	private Mensagem m;
	
	public PendingAction(IMessageAction action, Mensagem m){
		this.m=m;
		this.action=action;
		
	}
	
	public void executeAction(Context ctx){
		action.execute(m, ctx);
	}
	
}

