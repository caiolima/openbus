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

import java.util.ArrayList;
import java.util.List;

import org.nsoft.openbus.model.Message;


public class SubjectMessage {

	private List<MessageObserver> observers=new ArrayList<MessageObserver>();
	
	public void registerObserver(MessageObserver o){
		observers.add(o);
	}
	
	public void unregisterObserver(MessageObserver o){
		observers.remove(o);
	}
	
	public List<MessageObserver> getAllObservers(){
		return observers;
	}
	
	public void notifyMessageAddedObservers(Message m){
		for(MessageObserver mObserver:observers){
			mObserver.notifyMessageAdded(m);
		}
	}
	
	public void notifyMessageRemovedObservers(String id,int type){
		for(MessageObserver mObserver:observers){
			mObserver.notifyMessageRemoved(id,type);
		}
	}
	
	
	
}
