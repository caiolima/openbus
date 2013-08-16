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
			TB_MESSAGE = "message", 
			TB_USER = "user_table", 
			TB_COLUMNS_CONFIG = "collumn_config", TB_DRAFTS = "drafts",
			TB_LINES = "line_bus", TB_REPORT = "report",

			REPORT_LATITUDE = "latitude", REPORT_LONGITUDE = "longitude",
			REPORT_LINE_EXPECTED = "line_expected",
			REPORT_TYPE_REPORT = "type_report", REPORT_HOUR = "hour",
			REPORT_ID = "id_report",
			
			TWITTER_ID = "idTwitter", TWITTER_NAME = "name",
			TWITTER_TOKEN = "token", TWITTER_TOKEN_SECRET = "tokenSecret",
			TWITTER_PROF_IMF = "profile_img",

			MESSAGE_ID = "idMensagem", MESSAGE_TEXT = "msg_text",
			MESSAGE_USER = "userName", MESSAGE_DATE = "createdAt",
			MESSAGE_IMAGE_URL = "imgURL", MESSAGE_TYPE = "tipoMensagem",
			MESSAGE_ID_USER = "id_from", MESSAGE_ADD_CONTENT = "add_content",

			USER_SYS_ID = "id", USER_ID = "idUser", USER_NAME = "name",
			USER_URL = "url_img", USER_ABOUT = "about", USER_NICK = "nick",
			USER_NUM_FOLLOWERS = "followers", USER_NUM_FOLLOWING = "following",
			USER_TYPE = "type_user",

			COLUMNS_CONFIG_POS = "after", COLUMNS_CONFIG_TYPE = "c_type",
			COLUMNS_CONFIG_PROPRITIES = "c_propreties",

			DRAFTS_ID = "drafts_id", DRAFTS_TEXT = "drafts_text",
					
			LINES_ID="id_lines", LINES_DESC="lines_desc", LINES_HASH = "hash_line";

	private static DataBase singleton;
	private final String[] CREATE_SQL = new String[] {
			
			"CREATE TABLE IF NOT EXISTS " + TB_REPORT + "("
					+ REPORT_ID + " long primary key," + REPORT_LATITUDE + " double,"
					+ REPORT_LONGITUDE + " double, " + REPORT_LINE_EXPECTED 
					+ " text not null,"	+ REPORT_TYPE_REPORT + " int not null, " 
					+ REPORT_HOUR + " long);",

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

			"CREATE TABLE IF NOT EXISTS " + TB_MESSAGE + "(" + MESSAGE_ID
					+ " text," + "" + MESSAGE_TEXT + " text not null," + ""
					+ MESSAGE_USER + " text not null," + ""
					+ MESSAGE_IMAGE_URL + " text not null," + ""
					+ MESSAGE_TYPE + " int not null," + "" + MESSAGE_DATE
					+ " long not null," + "" + MESSAGE_ID_USER
					+ " long not null," + "" + MESSAGE_ADD_CONTENT + " text);",

			"CREATE TABLE IF NOT EXISTS " + TB_USER + "(" + USER_SYS_ID
					+ " integer primary key autoincrement," + "" + USER_ID
					+ " long," + "" + USER_ABOUT + " text not null," + ""
					+ USER_NAME + " text not null," + "" + USER_URL
					+ " text not null," + "" + USER_NUM_FOLLOWERS + " int,"
					+ "" + USER_NUM_FOLLOWING + " int," + "" + USER_TYPE
					+ " int not null," + "" + USER_NICK + " text);",
					
			"CREATE TABLE IF NOT EXISTS " + TB_LINES + "(" + LINES_ID
					+ " text not null,"
					+ LINES_DESC + " text not null, "
					+ LINES_HASH + " text not null);",
					
			//insert lines of bus for registration
			"INSERT INTO "+ TB_LINES + " VALUES('342','Rodoviaria Circ. A', '342ssaba')"
			//TODO ...
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
