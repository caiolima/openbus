package com.dot.me.model;

import java.net.URL;
import java.util.Vector;

import com.dot.me.model.bd.Facade;

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
