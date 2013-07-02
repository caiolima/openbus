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

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.nsoft.openbus.model.CollumnConfig;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CollumnConfigDao extends Dao {

	protected CollumnConfigDao(Context ctx) {
		super(ctx);
	}

	protected void insert(CollumnConfig collumn) {

		Cursor c = db.getDB().query(DataBase.TB_COLUMNS_CONFIG, null, null,
				null, null, null, null);
		int pos = c.getCount();

		ContentValues values = new ContentValues();

		values.put(DataBase.COLUMNS_CONFIG_POS, pos);
		values.put(DataBase.COLUMNS_CONFIG_TYPE, collumn.getType());
		values.put(DataBase.COLUMNS_CONFIG_PROPRITIES, collumn.getProprietes()
				.toString());

		db.getDB().insert(DataBase.TB_COLUMNS_CONFIG, null, values);
		
		collumn.setPos(pos);
		c.close();

	}

	protected void deleteAll(){
		db.getDB().delete(DataBase.TB_COLUMNS_CONFIG,null,null);
	}
	
	protected void delete(int pos) {
		db.getDB().delete(DataBase.TB_COLUMNS_CONFIG,
				DataBase.COLUMNS_CONFIG_POS + "=?",
				new String[] { Integer.toString(pos) });

		Cursor c = db.getDB().query(DataBase.TB_COLUMNS_CONFIG, null,
				null,
				null, null, null,
				null);

		for (int i = 0; i < c.getCount(); i++) {

			c.moveToPosition(i);
			
			CollumnConfig config = new CollumnConfig();
			config.setPos(c.getInt(0));
			config.setType(c.getString(1));
			try {
				config.setProprietes(new JSONObject(c.getString(2)));
			} catch (JSONException e) {

			}
			
			this.changePos(config, i);
		}
		
		c.close();

	}
	
	protected void update(CollumnConfig config) {

		ContentValues values = new ContentValues();

		values.put(DataBase.COLUMNS_CONFIG_TYPE, config.getType());
		values.put(DataBase.COLUMNS_CONFIG_PROPRITIES, config.getProprietes()
				.toString().toString());

		db.getDB().update(DataBase.TB_COLUMNS_CONFIG, values,
				DataBase.COLUMNS_CONFIG_POS + "=?",
				new String[] { Integer.toString(config.getPos()) });

	}

	protected void changePos(CollumnConfig config, int toPos) {

		ContentValues values = new ContentValues();

		values.put(DataBase.COLUMNS_CONFIG_POS, toPos);
		values.put(DataBase.COLUMNS_CONFIG_TYPE, config.getType());
		values.put(DataBase.COLUMNS_CONFIG_PROPRITIES, config.getProprietes()
				.toString().toString());

		db.getDB().update(DataBase.TB_COLUMNS_CONFIG, values,
				DataBase.COLUMNS_CONFIG_POS + "=?",
				new String[] { Integer.toString(config.getPos()) });

	}

	protected ArrayList<CollumnConfig> getAll() {
		ArrayList<CollumnConfig> all = new ArrayList<CollumnConfig>();

		Cursor c = db.getDB().query(DataBase.TB_COLUMNS_CONFIG, null, null,
				null, null, null, DataBase.COLUMNS_CONFIG_POS);

		for (int i = 0; i < c.getCount(); i++) {

			c.moveToPosition(i);

			CollumnConfig config = new CollumnConfig();
			config.setPos(i);
			config.setType(c.getString(1));
			try {
				config.setProprietes(new JSONObject(c.getString(2)));
			} catch (JSONException e) {

			}
			
			all.add(config);

		}
		c.close();
		return all;
	}
	
	protected CollumnConfig getOne(int pos){
		
		Cursor c = db.getDB().query(DataBase.TB_COLUMNS_CONFIG, null, DataBase.COLUMNS_CONFIG_POS+"=?",
				new String[]{Integer.toString(pos)}, null, null, DataBase.COLUMNS_CONFIG_POS);

		if(c.getCount()>0) {

			c.moveToPosition(0);

			CollumnConfig config = new CollumnConfig();
			config.setPos(c.getInt(0));
			config.setType(c.getString(1));
			try {
				config.setProprietes(new JSONObject(c.getString(2)));
			} catch (JSONException e) {

			}
			c.close();
			return config;

		}
		c.close();
		return null;
		
	}
	
	protected void deleteOfType(String type){
		
		List<CollumnConfig> list=getAll();
		int i=0;
		int cont_deleted=0;
		for(CollumnConfig c:list){
			
			if (c.getType().startsWith(type)) {
				delete(i-cont_deleted);
				cont_deleted++;
			}
			i++;
			
		}
		
	}
	
	protected boolean existsCollumnType(String type){
		
		Cursor c = db.getDB().query(DataBase.TB_COLUMNS_CONFIG, null, DataBase.COLUMNS_CONFIG_TYPE+"=?",
				new String[]{type}, null, null,null);
		
		
		
		if(c.getCount()>0){
			c.close();
			return true;
		}
		
		c.close();
		return false;
		
	}
	
}
