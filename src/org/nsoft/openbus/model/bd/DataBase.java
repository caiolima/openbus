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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DataBase {

	private boolean isExecuting;
	// classe para o banco de Dados
	public static final String TB_TWITTER = "twitter",
			TB_MENSAGEM = "mensagem", 
			TB_USER = "user_table", 
			TB_COLUMNS_CONFIG = "collumn_config", TB_DRAFTS = "drafts",
			TB_LINHAS="linhas_onibus",

			TWITTER_ID = "idTwitter", TWITTER_NAME = "name",
			TWITTER_TOKEN = "token", TWITTER_TOKEN_SECRET = "tokenSecret",
			TWITTER_PROF_IMF = "profile_img",

			MENSAGEM_ID = "idMensagem", MENSAGEM_TEXTO = "texto",
			MENSAGEM_USER = "userName", MENSAGEM_DATE = "createdAt",
			MENSAGEM_IMAGE_URL = "imgURL", MENSAGEM_TIPO = "tipoMensagem",
			MENSAGEM_ID_USER = "id_from", MENSAGEM_ADD_CONTENT = "add_content",

			USER_SYS_ID = "id", USER_ID = "idUser", USER_NAME = "name",
			USER_URL = "url_img", USER_ABOUT = "about", USER_NICK = "nick",
			USER_NUM_FOLLOWERS = "followers", USER_NUM_FOLLOWING = "following",
			USER_TYPE = "tipo_usuario",

			COLUMNS_CONFIG_POS = "pos", COLUMNS_CONFIG_TYPE = "c_type",
			COLUMNS_CONFIG_PROPRITIES = "c_propreties",

			DRAFTS_ID = "drafts_id", DRAFTS_TEXT = "drafts_text",
					
			LINHAS_ID="id_linhas", LINHAS_DESC="linhas_desc", LINHAS_HASH="linha_hash";

	private static DataBase singleton;
	private final String[] CREATE_SQL = new String[] {

			"CREATE TABLE IF NOT EXISTS " + TB_COLUMNS_CONFIG + "("
					+ COLUMNS_CONFIG_POS + " integer," + ""
					+ COLUMNS_CONFIG_TYPE + " text not null, " + ""
					+ COLUMNS_CONFIG_PROPRITIES + " text not null);",

			"CREATE TABLE IF NOT EXISTS " + TB_TWITTER + "(" + TWITTER_ID
					+ " long primary key," + "" + TWITTER_TOKEN
					+ " text not null," + "" + TWITTER_TOKEN_SECRET
					+ " text not null," + "" + TWITTER_PROF_IMF
					+ " text not null," + "" + TWITTER_NAME
					+ " text not null);",

			"CREATE TABLE IF NOT EXISTS " + TB_DRAFTS + "(" + DRAFTS_ID
					+ " text not null," + "" + DRAFTS_TEXT + " text not null);",

			"CREATE TABLE IF NOT EXISTS " + TB_MENSAGEM + "(" + MENSAGEM_ID
					+ " text," + "" + MENSAGEM_TEXTO + " text not null," + ""
					+ MENSAGEM_USER + " text not null," + ""
					+ MENSAGEM_IMAGE_URL + " text not null," + ""
					+ MENSAGEM_TIPO + " int not null," + "" + MENSAGEM_DATE
					+ " long not null," + "" + MENSAGEM_ID_USER
					+ " long not null," + "" + MENSAGEM_ADD_CONTENT + " text);",

			"CREATE TABLE IF NOT EXISTS " + TB_USER + "(" + USER_SYS_ID
					+ " integer primary key autoincrement," + "" + USER_ID
					+ " long," + "" + USER_ABOUT + " text not null," + ""
					+ USER_NAME + " text not null," + "" + USER_URL
					+ " text not null," + "" + USER_NUM_FOLLOWERS + " int,"
					+ "" + USER_NUM_FOLLOWING + " int," + "" + USER_TYPE
					+ " int not null," + "" + USER_NICK + " text);",
					
			"CREATE TABLE IF NOT EXISTS " + TB_LINHAS + "(" + LINHAS_ID
					+ " text not null,"
					+ LINHAS_DESC + " text not null, "
					+ LINHAS_HASH + " text not null);",
					
			//inserindo as linhas j‡ precadastradas
			"INSERT INTO "+ TB_LINHAS + " VALUES('342','Rodoviaria Circ. A', '342ssaba')"
	};
	private SQLiteDatabase db;
	private SQLiteHelper dbHelper;

	public DataBase(Context c) {
		dbHelper = new SQLiteHelper(c, "OpenBus", 30, CREATE_SQL);
		setDB(dbHelper.getWritableDatabase());
	}

	public void setDB(SQLiteDatabase db) {
		this.db = db;
	}

	public SQLiteDatabase getDB() {
		return db;
	}

	public void close() {
		singleton = null;
		try {
			db.close();
		} catch (SQLiteException e) {

		}
	}

	public static void start(Context ctx) {
		if (singleton == null)
			singleton = new DataBase(ctx);

	}

	public static DataBase getInstance(Context ctx) {
		if (singleton == null)
			singleton = new DataBase(ctx);
		return singleton;
	}

	public boolean isExecuting() {
		return isExecuting;
	}

	public void setExecuting(boolean isExeciting) {
		this.isExecuting = isExeciting;
	}
	
	public static boolean isOppened(){
		if(DataBase.singleton==null)
			return false;
		
		return true;
	}
	

}
