package org.nsoft.openbus.control;

import org.nsoft.openbus.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MapActivity extends FragmentActivity{
	
	private GoogleMap map;
	private SupportMapFragment fragMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.map_view);
		
		fragMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		map = fragMap.getMap();
		
		map.getUiSettings().setZoomControlsEnabled(false);
		
	}

	
	
}
