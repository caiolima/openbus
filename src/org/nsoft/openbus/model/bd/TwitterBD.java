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
package org.nsoft.openbus.model.bd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.nsoft.openbus.model.Account;
import org.nsoft.openbus.model.TwitterAccount;
import org.nsoft.openbus.utils.Menssage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class TwitterBD extends Dao{

	
	
	protected TwitterBD(Context ctx) {
		super(ctx);
	}

	protected int insert(TwitterAccount acc){
		
		ContentValues values=new ContentValues();
		values.put(DataBase.TWITTER_ID, acc.getId());
		values.put(DataBase.TWITTER_TOKEN, acc.getToken());
		values.put(DataBase.TWITTER_TOKEN_SECRET, acc.getTokenSecret());
		values.put(DataBase.TWITTER_PROF_IMF, acc.getProfileImage().toString());
		values.put(DataBase.TWITTER_NAME, acc.getName());
		SQLiteDatabase base=db.getDB();	
		
		long r=base.insert(DataBase.TB_TWITTER, null, values);
		if(r==-1)
			return Menssage.ERRO;
		else
			return Menssage.SUCCESS;
	}
	
	protected void delete(long twitterId){
		db.getDB().delete(DataBase.TB_TWITTER, DataBase.TWITTER_ID+"=?", new String[]{Long.toString(twitterId)});
	}
	
	protected void delete(){
		db.getDB().delete(DataBase.TB_TWITTER, null,null);
	}
	
	protected Vector<Account> lastSavedSession(){
		Cursor c=db.getDB().query(DataBase.TB_TWITTER, null, null, null, null, null, null);
		Vector<Account> all=new Vector<Account>();
		
		for(int i=0;i<c.getCount();i++){
			c.moveToPosition(i);
			
			TwitterAccount t=new TwitterAccount();
			
			t.setId(c.getLong(0));
			t.setToken(c.getString(1));
			t.setTokenSecret(c.getString(2));
			try {
				t.setProfileImage(new URL(c.getString(3)));
			} catch (MalformedURLException e) {
			
			}
			t.setName(c.getString(4));
			
			all.add(t);
		}
		c.close();
		return all;
		
	}
	
}
