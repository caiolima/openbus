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

import org.nsoft.openbus.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UserDB extends Dao{

	protected UserDB(Context ctx) {
		super(ctx);
	}
	
	protected int insert(User u){
		ContentValues c=new ContentValues();
		c.put(DataBase.USER_ID, u.getId());
		c.put(DataBase.USER_NAME, u.getName());
		c.put(DataBase.USER_URL, u.getUrl());
		c.put(DataBase.USER_ABOUT, u.getAbout());
		c.put(DataBase.USER_NUM_FOLLOWERS, u.getNum_followers());
		c.put(DataBase.USER_NUM_FOLLOWING, u.getNum_following());
		c.put(DataBase.USER_TYPE, u.getType());
		c.put(DataBase.USER_NICK, u.getNick());
		
		db.getDB().insert(DataBase.TB_USER, null, c);
		
		Cursor cursor=db.getDB().query(DataBase.TB_USER, new String[]{DataBase.USER_SYS_ID}
				,null
				, null
				, null, null, DataBase.USER_SYS_ID+" desc");
		
		cursor.moveToFirst();
		int out=cursor.getInt(0);
		
		cursor.close();
		return out;
	}
	
	protected User getOne(long id,int type){
		Cursor c=db.getDB().query(DataBase.TB_USER, null
				, DataBase.USER_ID+"=? and "+DataBase.USER_TYPE+"=?"
				, new String[]{Long.toString(id),Integer.toString(type)}
				, null, null, null);
		if(c.getCount()>0){
			c.moveToFirst();
			
			User u=new User();
			
			u.setSysID(c.getInt(0));
			u.setId(c.getLong(1));
			u.setAbout(c.getString(2));
			u.setName(c.getString(3));
			u.setUrl(c.getString(4));
			u.setNum_followers(c.getInt(5));
			u.setNum_following(c.getInt(6));
			u.setType(c.getInt(7));
			u.setNick(c.getString(8));
			c.close();
			return u;
			
		}
		c.close();
		return null;
	}
	
	protected User getOne(int id){
		Cursor c=db.getDB().query(DataBase.TB_USER, null
				, DataBase.USER_SYS_ID+"=?"
				, new String[]{Long.toString(id)}
				, null, null, null);
		if(c.getCount()>0){
			c.moveToFirst();
			
			User u=new User();
			
			u.setSysID(c.getInt(0));
			u.setId(c.getLong(1));
			u.setAbout(c.getString(2));
			u.setName(c.getString(3));
			u.setUrl(c.getString(4));
			u.setNum_followers(c.getInt(5));
			u.setNum_following(c.getInt(6));
			u.setType(c.getInt(7));
			u.setNick(c.getString(8));
			
			c.close();
			
			return u;
			
		}
		c.close();
		
		return null;
	}
	
	protected User getOne(String nick) {
		
		Cursor c=db.getDB().query(DataBase.TB_USER, null
				, DataBase.USER_NICK+"=?"
				, new String[]{nick}
				, null, null, null);
		if(c.getCount()>0){
			c.moveToFirst();
			
			User u=new User();
			
			u.setSysID(c.getInt(0));
			u.setId(c.getLong(1));
			u.setAbout(c.getString(2));
			u.setName(c.getString(3));
			u.setUrl(c.getString(4));
			u.setNum_followers(c.getInt(5));
			u.setNum_following(c.getInt(6));
			u.setType(c.getInt(7));
			u.setNick(c.getString(8));
			c.close();
			return u;
			
		}
		c.close();
		return null;
	}

}
