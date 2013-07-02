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
package org.nsoft.openbus.model;

import java.net.URL;
import java.util.Vector;

import org.nsoft.openbus.model.bd.Facade;


import android.content.Context;

public abstract class Account {

	private String name;
	private URL profileImage;
	private static Vector<Account> loggedUsers=new Vector<Account>();
	
	public abstract URL processProfileImage();
	public abstract boolean updateStatus(String status);


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}	
	
	
	public static void addUser(Account a){
		Account.loggedUsers.add(a);
	}
	
	public static void removeUser(Account a){
		Account.removeUser(a);
	}
	
	public static Vector<Account> getLoggedUsers(Context ctx){
		if(loggedUsers.isEmpty()){
			Vector<Account> users = Facade.getInstance(ctx).lastSavedSession();
			for(Account acc:users){
				Account.addUser(acc);
			}
		}
		
		return Account.loggedUsers;
	}
	public URL getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(URL profileImage) {
		this.profileImage = profileImage;
	}
	
	public static TwitterAccount getTwitterAccount(Context ctx){
		loadUsers(ctx);
			
		for(Account a:loggedUsers){
			if(a instanceof TwitterAccount)
				return (TwitterAccount) a;
		}
		return null;
	}
	
	private static void loadUsers(Context ctx) {
		loggedUsers=Facade.getInstance(ctx).lastSavedSession();
	}
	
	
	
}
