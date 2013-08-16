package org.nsoft.openbus.model.bd;

import org.nsoft.openbus.utils.Menssage;
import org.nsoft.openbus.model.Report;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class ReportDB extends Dao{

	protected ReportDB(Context ctx) {
		super(ctx);
	}
	
	protected int insert(Report acc){
			
			ContentValues values=new ContentValues();
			values.put(DataBase.REPORT_HOUR, acc.getHour());
			values.put(DataBase.REPORT_ID, acc.getIdReport());
			values.put(DataBase.REPORT_LATITUDE, acc.getLatitude());
			values.put(DataBase.REPORT_LONGITUDE, acc.getLongitude());
			values.put(DataBase.REPORT_LINE_EXPECTED, acc.getLineExpected());
			values.put(DataBase.REPORT_TYPE_REPORT, acc.getTypeReport());
			SQLiteDatabase base=db.getDB();	
			
			long r=base.insert(DataBase.TB_REPORT, null, values);
			if(r==-1)
				return Menssage.ERRO;
			else
				return Menssage.SUCCESS;
	}
	
	protected void delete(long idReport){
		db.getDB().delete(DataBase.TB_REPORT, DataBase.REPORT_ID+"=?", new String[]{Long.toString(idReport)});
	}
	
	protected void delete(){
		db.getDB().delete(DataBase.TB_REPORT, null,null);
	}
	
	
	
	
}


