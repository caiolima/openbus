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

import org.nsoft.openbus.control.SendTweetActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class OpenTwitterWriter implements AbstractCommand {

	private String preText;
	
	public OpenTwitterWriter(){}
	
	public OpenTwitterWriter(String preText){
		this.preText=preText;
	}
	
	
	@Override
	public void execute(Activity activity) {
		Intent intent=new Intent(activity,SendTweetActivity.class);
		Bundle extras=new Bundle();
		extras.putString("action", "SendTwitteAction");
		
		if(preText!=null){
			extras.putString("pre_text", preText);
		}
		
		extras.putString("type_message", "Twitter");
		
		intent.putExtras(extras);
		
		activity.startActivity(intent);
		
	}

	
	
}
