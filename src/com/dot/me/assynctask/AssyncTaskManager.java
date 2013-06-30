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
package com.dot.me.assynctask;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public class AssyncTaskManager {

	private static AssyncTaskManager singleton;
	private List<AsyncTask<?,?,?>> processList=new ArrayList<AsyncTask<?,?,?>>();
	
	public static AssyncTaskManager getInstance(){
		
		if(singleton==null)
			singleton=new AssyncTaskManager();
		
		return singleton;
	}
	
	private AssyncTaskManager(){}
	
	public void addProccess(AsyncTask<?, ?, ?> task){
		processList.add(task);
	}
	
	public void removeProcess(AsyncTask<?, ?, ?> task){
		processList.remove(task);
	}
	
	public void notifyToCancel(){
		for(AsyncTask<? , ? , ?> task:processList){
			task.cancel(true);
		}
	}
	
}
