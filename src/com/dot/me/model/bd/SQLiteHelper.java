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
package com.dot.me.model.bd;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper{

	private String[] sqlCreate;
	
	SQLiteHelper(Context c,String nomeBanco,int versao,String[] createSQL){
		super(c,nomeBanco,null,versao);
		this.sqlCreate=createSQL;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		for(int i=0;i<this.sqlCreate.length;i++){
			String sql=sqlCreate[i];
			try{
				db.execSQL(sql);
			}catch(SQLException e){
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

}
