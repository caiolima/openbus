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
* Date: 02 - 07 - 2013
*/
package org.nsoft.openbus.model.bd;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.nsoft.openbus.model.Account;
import org.nsoft.openbus.model.LinhaOnibus;
import org.nsoft.openbus.model.TwitterAccount;

import android.content.Context;
import android.database.Cursor;

public class LinhasBD extends Dao{

	protected LinhasBD(Context ctx) {
		super(ctx);
	}
	
	protected Vector<LinhaOnibus> getAll(){
		Cursor c=db.getDB().query(DataBase.TB_LINHAS, null, null, null, null, null, null);
		Vector<LinhaOnibus> all=new Vector<LinhaOnibus>();
		
		for(int i=0;i<c.getCount();i++){
			c.moveToPosition(i);
			
			LinhaOnibus linha=new LinhaOnibus();
			
			linha.setIdLinha(c.getInt(0));
			linha.setDescicao(c.getString(1));
			linha.setHash(c.getString(2));
			
			all.add(linha);
		}
		c.close();
		return all;
	}
	

}
