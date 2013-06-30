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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class TwitterLoginActivity extends Activity{

	private WebView wView;
	private ProgressBar load_bar;
	private boolean isTwitterLogin = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.twitter_login_web);

		wView = (WebView) findViewById(R.id.web_view);
		load_bar = (ProgressBar) findViewById(R.id.load_bar);
		LinearLayout main_web=(LinearLayout) findViewById(R.id.lt_main_web);
		Intent mIntent = getIntent();
		String url = "";
		if (mIntent != null) {
			Uri uri = mIntent.getData();
			if (uri != null) {
				url = uri.getQueryParameter("url");
				isTwitterLogin=false;

			} else {

				Bundle extras = mIntent.getExtras();
				if (extras != null) {

					url = extras.getString("url");

				}
			}
		}

		wView.getSettings().setJavaScriptEnabled(true);

		
		wView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different
				// scales.
				// The progress meter will automatically disappear when we reach
				// 100%
				load_bar.setProgress(progress);
				if (progress > 98)
					load_bar.setVisibility(View.GONE);
			}
		});

		if (isTwitterLogin) {
			wView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (url.contains("twitter-client://back")) {
						Uri uri = Uri.parse(url);
						String oauthVerifier = uri
								.getQueryParameter("oauth_verifier");

						String myUri = "twitter-client://back?oauth_verifier="
								+ oauthVerifier;
						Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(myUri));
						startActivity(mIntent);

						finish();
						return true;
					}
					return false;
				}
			});
		}else{
			wView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					
					wView.loadUrl(url);
					load_bar.setVisibility(View.VISIBLE);
					return true;
				}
			});
			
		}
		
		wView.loadUrl(url);
	}
}
