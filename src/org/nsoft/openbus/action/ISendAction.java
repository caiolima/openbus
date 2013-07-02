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

import org.nsoft.openbus.model.Account;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public interface ISendAction {

	public void execute(Activity a,String message,Bundle b);
	public String getResultMessage(Context c);
	public Account getAccount(Context c);
	public String getDraftId();
	public boolean messageSent();
	public void initAction(Bundle b);
	
}
