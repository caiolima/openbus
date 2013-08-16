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

import org.nsoft.openbus.model.Draft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DraftDB extends Dao{

	protected DraftDB(Context ctx) {
		super(ctx);
	}
	
	protected void insert(Draft d){
		
		ContentValues values=new ContentValues();
		values.put(DataBase.DRAFTS_ID, d.getId());
		values.put(DataBase.DRAFTS_TEXT, d.getText());
		
		db.getDB().insert(DataBase.TB_DRAFTS, null, values);
		
	}
	
	protected void delete(String id){
		db.getDB().delete(DataBase.TB_DRAFTS, DataBase.DRAFTS_ID+"=?", new String[]{id});
	}

	protected Draft getOne(String id) {
		Cursor c=db.getDB().query(DataBase.TB_DRAFTS, null,
				DataBase.DRAFTS_ID+"=?", new String[]{id},
				null, null, null);
		
		Draft draft=null;
		if(c.getCount()>0){
			c.moveToFirst();
			
			draft=new Draft();
			draft.setId(c.getString(0));
			draft.setText(c.getString(1));
			
		}
		
		c.close();
		
		return draft;
		
	}
	
	protected boolean exists(String id){
		Cursor c=db.getDB().query(DataBase.TB_DRAFTS, null,
				DataBase.DRAFTS_ID+"=?", new String[]{id},
				null, null, null);
		
		
		if(c.getCount()>0){
			c.close();
			return true;
		}
		c.close();
		return false;
	}
	
	
}
