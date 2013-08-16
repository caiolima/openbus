package org.five.openbus.reports.tests;

import org.five.openbus.reports.BusBrokeReport;
import org.five.openbus.reports.InBusStopReport;
import org.five.openbus.reports.OnBusReport;
import org.five.openbus.reports.Report;
import org.five.openbus.reports.StuckInTrafficReport;
import org.nsoft.openbus.R;
import org.nsoft.openbus.model.LinhaOnibus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReportTestsActivity extends Activity {

	private TextView txtComment;
	private Button btOnBus,
				   btInBusStop,
				   btStuckInTraffic,
				   btBusBorke;	
	
	private  Button.OnClickListener mListener=new Button.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			final LinhaOnibus line = new LinhaOnibus();
			
			line.setDescicao("Teste");
			line.setIdLinha(333);
			line.setHash("333ssaba");
			
			final double latitude = -12.9833;
			final double longitude = -38.5167 ;
			
			Report report = null;
			
			if(v==btBusBorke)
				report = new BusBrokeReport(ReportTestsActivity.this, txtComment.getText().toString(), line, latitude, longitude);
			else if(v==btStuckInTraffic)
				report = new StuckInTrafficReport(ReportTestsActivity.this, txtComment.getText().toString(), line, latitude, longitude);
			else if(v==btInBusStop)
				report = new InBusStopReport(ReportTestsActivity.this, txtComment.getText().toString(), line, latitude, longitude);
			else if(v==btOnBus)
				report = new OnBusReport(ReportTestsActivity.this, txtComment.getText().toString(), line, latitude, longitude);

			
			report.sendReport();
		}
	
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.teste_report);
		
		txtComment = (TextView) findViewById(R.id.txt_comment);
		
		btBusBorke = (Button) findViewById(R.id.bt_busbroke);
		btOnBus = (Button) findViewById(R.id.bt_onbus);
		btInBusStop = (Button) findViewById(R.id.bt_instop);
		btStuckInTraffic = (Button) findViewById(R.id.bt_stucktraffic);
		
		
		init();
		
	}

	//Method to init the view and add Interface behavior
	private void init(){
	
		btBusBorke.setOnClickListener(mListener);
		btOnBus.setOnClickListener(mListener);
		btInBusStop.setOnClickListener(mListener);
		btStuckInTraffic.setOnClickListener(mListener);	
		
	}
	
}
