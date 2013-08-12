package org.nsoft.openbus.control;

import java.util.Vector;

import org.nsoft.openbus.R;
import org.nsoft.openbus.model.LinhaOnibus;
import org.nsoft.openbus.model.bd.Facade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class SearchLineActivity extends Activity{

	private ImageButton bt_search;
	private EditText txt_search;
	private ListView lst_lines;
	private ArrayAdapter<LinhaOnibus> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_line);
		
		getViewRefs();
		
		initControl();
		
	}

	private void initControl() {
		
		Facade facade = Facade.getInstance(this);
		Vector<LinhaOnibus> allLines = facade.getAllLinhas();
		
		adapter = new ArrayAdapter<LinhaOnibus>(this, R.layout.trend_item,
				R.id.txt_value, allLines);
		
		lst_lines.setAdapter(adapter);
		lst_lines.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> aAdapter, View aView, int aPosition,
					long arg3) {
				
				
				LinhaOnibus aLine = adapter.getItem(aPosition);
				Bundle params=new Bundle();
				
				params.putString("hash_code", aLine.getHash());
				Intent aIntent=new Intent(SearchLineActivity.this, MapActivity.class);
				
				aIntent.putExtras(params);
				
				startActivity(aIntent);
				
				
				
			}
		
		});
		
	}

	private void getViewRefs() {
		bt_search=(ImageButton)findViewById(R.id.bt_search);
		txt_search=(EditText) findViewById(R.id.txt_search);
		lst_lines=(ListView) findViewById(R.id.lst_lines);
		
	}
	
}
