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

package org.nsoft.openbus.control;

import org.nsoft.openbus.R;
import org.nsoft.openbus.assynctask.TwitterImageDownloadTask;
import org.nsoft.openbus.model.Account;
import org.nsoft.openbus.model.TwitterAccount;
import org.nsoft.openbus.model.bd.DataBase;
import org.nsoft.openbus.model.bd.Facade;
import org.nsoft.openbus.utils.Constants;
import org.nsoft.openbus.utils.TwitterUtils;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import twitter4j.auth.AccessToken;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddSocialAccount extends Activity {

	public static boolean running = false;
	private Toast toast;
	private static CommonsHttpOAuthConsumer commonHttpOAuthConsumer;
	private static OAuthProvider authProvider;
	private static boolean flagOauthTwitter;
	

	private Button bt_logoutTwitter,  bt_twitter, bt_ok;
	private LinearLayout twitterPane;
	private ImageView twitterImage;
	private TextView twitterName;
	private boolean flagAccChanged = false;
	private Handler h = new Handler();
	private boolean mNotificationFlag;

	private View.OnClickListener twitterLoggoutClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			Facade.destroy();
			DataBase.start(AddSocialAccount.this);

			flagAccChanged = true;
			Facade facade = Facade.getInstance(AddSocialAccount.this);
			facade.logoutTwitter();
			bt_twitter.setVisibility(View.VISIBLE);
			twitterPane.setVisibility(View.GONE);
			if (Account.getTwitterAccount(AddSocialAccount.this) == null)
				bt_ok.setVisibility(View.GONE);

			
		}
	};

	// teste
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.account_manager);
		flagOauthTwitter = false;
		running = true;
		DataBase.start(this);

		twitterPane = (LinearLayout) findViewById(R.id.twitter_pane);
		twitterImage = (ImageView) findViewById(R.id.twitter_img);
		
		twitterName = (TextView) findViewById(R.id.twitter_name);
		bt_logoutTwitter = (Button) findViewById(R.id.twitter_logout);

		bt_logoutTwitter.setOnClickListener(twitterLoggoutClick);
		

		

		bt_twitter = (Button) findViewById(R.id.singin_bt_twitter);
		bt_twitter.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo nInfo = cm.getActiveNetworkInfo();
				if (nInfo != null)
					if (nInfo.isConnected()) {
						flagOauthTwitter = true;
						new ConnectTask().execute(AddSocialAccount.this);
					} else {
						showMessage(AddSocialAccount.this
								.getString(R.string.erro_connect),
								Toast.LENGTH_SHORT);

					}
				else
					showMessage(AddSocialAccount.this
							.getString(R.string.erro_connect),
							Toast.LENGTH_SHORT);
			}
		});

		bt_ok = (Button) findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
		
//				SharedPreferences settings = getSharedPreferences(
//						Constants.PREFS_NAME, 0);
//				boolean flagHelp = settings.getBoolean(Constants.HELP_SHOWED,
//						false);
//				Intent intent=null;
//				if (!flagHelp) {
//					intent = new Intent(AddSocialAccount.this,
//							HelpActivity.class);
//				} 
//				
//				startActivity(intent);
//				finish();
				
				Intent intent = new Intent(AddSocialAccount.this,
							SearchLineActivity.class);
				
				
				startActivity(intent);
				finish();
				
			}
		});

		
		SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME,
				0);
		mNotificationFlag = settings.getBoolean(Constants.CONFIG_NOTIFICATIONS,
				true);

		updateLayout();

	}

	private void updateLayout() {
		TwitterAccount tAcc = Account.getTwitterAccount(this);
		if (tAcc != null) {
			twitterPane.setVisibility(View.VISIBLE);
			bt_twitter.setVisibility(View.GONE);

			twitterName.setText(tAcc.getName());

			new TwitterImageDownloadTask(this, twitterImage,
					tAcc.getProfileImage()).execute();

		}

		if (tAcc == null)
			bt_ok.setVisibility(View.GONE);
		else
			bt_ok.setVisibility(View.VISIBLE);
	}

	private static int countNewIntent = 0;

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		countNewIntent++;
		Uri uri = intent.getData();

		if (uri != null && flagOauthTwitter && countNewIntent < 2) {

			new GetBasicInformationsTask().execute(uri);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void showAlert(final String message,
			final DialogInterface.OnClickListener listaner) {
		Dialog d = new AlertDialog.Builder(AddSocialAccount.this)
				.setTitle("Message").setMessage(message)
				.setPositiveButton("Ok", listaner).create();
		d.show();

	}

	class ConnectTask extends AsyncTask<Activity, Void, Void> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(AddSocialAccount.this);

			progressDialog.setCancelable(false);
			progressDialog.setMessage(getString(R.string.twitter_connecting));

			progressDialog.show();

		}

		@Override
		protected Void doInBackground(Activity... v) {

			commonHttpOAuthConsumer = new CommonsHttpOAuthConsumer(
					Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
			authProvider = new DefaultOAuthProvider(
					"https://api.twitter.com/oauth/request_token",
					"https://api.twitter.com/oauth/access_token",
					"https://api.twitter.com/oauth/authorize");
			try {
				String oAuthURL = authProvider.retrieveRequestToken(
						commonHttpOAuthConsumer, "twitter-client://back");
				
				  Intent intent = new Intent(Intent.ACTION_VIEW,
				  Uri.parse(oAuthURL));
				  intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
				  Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(intent);
				 
//				Intent intent = new Intent(AddSocialAccount.this,
//						TwitterLoginActivity.class);
//				Bundle b = new Bundle();
//				b.putString("url", oAuthURL);
//
//				intent.putExtras(b);
//
//				startActivity(intent);

			} catch (OAuthMessageSignerException e) {
				e.printStackTrace();
			} catch (OAuthNotAuthorizedException e) {

				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {

			progressDialog.dismiss();
			// finish();
		}

	}

	private static boolean isAssyncRunning = false;

	class GetBasicInformationsTask extends AsyncTask<Uri, Void, Void> {

		private ProgressDialog progressDialog;
		private TwitterAccount twitterAcc;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(AddSocialAccount.this);

			progressDialog.setCancelable(false);
			progressDialog
					.setMessage(getString(R.string.get_basic_information));

			progressDialog.show();

		}

		@Override
		protected Void doInBackground(Uri... v) {

			isAssyncRunning = true;
			Uri uri = v[0];
			String oauth_verifier = uri.getQueryParameter("oauth_verifier");

			try {

				authProvider.retrieveAccessToken(commonHttpOAuthConsumer,
						oauth_verifier);

				String token = commonHttpOAuthConsumer.getToken();
				String tokenSecret = commonHttpOAuthConsumer.getTokenSecret();

				AccessToken accessToken = new AccessToken(
						commonHttpOAuthConsumer.getToken(),
						commonHttpOAuthConsumer.getTokenSecret());

				TwitterUtils.getTwitter().setOAuthAccessToken(accessToken);
				long id = TwitterUtils.getTwitter().getId();

				twitterAcc = new TwitterAccount();
				twitterAcc.setId(id);
				twitterAcc.setToken(token);
				twitterAcc.setTokenSecret(tokenSecret);
				twitterAcc.setProfileImage(twitterAcc.processProfileImage());
				twitterAcc.setName(TwitterUtils.getTwitter().getScreenName());
				// t.setNickname(TwitterUtils.getTwitter().verifyCredentials().getName());

			} catch (Exception e) {
				showAlert(getString(R.string.twitter_connect_fail),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								new ConnectTask()
										.execute(AddSocialAccount.this);
							}
						});
			}
			flagOauthTwitter = false;

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();

			Facade.destroy();
			DataBase.start(AddSocialAccount.this);

			Facade.getInstance(AddSocialAccount.this).insert(twitterAcc);			

			updateLayout();
			
			flagAccChanged = true;

		}

	}

	private void showMessage(String message, int duration) {
		if (toast != null) {
			toast.cancel();

			toast.setDuration(duration);
			toast.setText(message);
			toast.show();
		} else {
			toast = Toast.makeText(this, message, duration);
			toast.show();
		}

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
//		Facade.destroy();
//		DataBase.start(AddSocialAccount.this);
//		if (keyCode == KeyEvent.KEYCODE_BACK
//				&&(Account.getTwitterAccount(this) != null)) {
//
//			Intent intent = new Intent(AddSocialAccount.this,
//					DashboardActivity.class);
//
//			Bundle b = new Bundle();
//			b.putBoolean("refresh_timeline", flagAccChanged);
//			intent.putExtras(b);
//			startActivity(intent);
//			finish();
//
//			return true;
//		}
//		return super.onKeyUp(keyCode, event);
		return super.onKeyUp(keyCode,event);
	}

}
