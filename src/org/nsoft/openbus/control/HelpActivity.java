package org.nsoft.openbus.control;

import org.nsoft.openbus.R;
import com.dot.me.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends Activity {

	private Button bt_n_like, bt_like;
	private TextView txt_tuto;
	private String[] tutos;
	private int currentTuto = 0;
	private OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			nextPage();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tutorial);

		String[] auxTutos = { getString(R.string.tuto_intro),
				getString(R.string.tuto_labels),
				getString(R.string.tuto_black_list) };

		tutos = auxTutos;

		bt_like = (Button) findViewById(R.id.bt_like);
		bt_n_like = (Button) findViewById(R.id.bt_not_like);
		txt_tuto = (TextView) findViewById(R.id.txt_tuto);

		Intent intent = getIntent();
		if (intent != null) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				currentTuto = extras.getInt("tuto_text");
			}
		}

		txt_tuto.setText(tutos[currentTuto]);
		
		bt_like.setOnClickListener(clickListener);
		bt_n_like.setOnClickListener(clickListener);

	}

	private void nextPage() {
		if (currentTuto < tutos.length-1) {
			currentTuto++;
			txt_tuto.setText(tutos[currentTuto]);
		} else {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME,
				0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(Constants.HELP_SHOWED, true);

		editor.commit();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;

		return super.onKeyDown(keyCode, event);
	}

}
