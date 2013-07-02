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
package org.nsoft.openbus.assynctask;

import java.net.URL;

import org.nsoft.openbus.model.Account;
import org.nsoft.openbus.utils.ImageUtils;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class TwitterImageDownloadTask extends AsyncTask<Void, Void, Void>{

	private ImageView iView;
	private URL requestImage;
	private Bitmap imgBit;
	private Context ctx;
	private static int count=0;
	private IOnImageLoaded onImageLoad;
	
	
	public static void executeDownload(Context ctx,ImageView iView,URL url){
		//if(count<15){
		Bitmap imgBit = ImageUtils.imageCache.get(url);
		if (imgBit == null) {
			new TwitterImageDownloadTask(ctx,iView,url).execute();
		}else{
			iView.setImageBitmap(imgBit);
		}
		
		//}
		
	}
	
	public TwitterImageDownloadTask(Context ctx,ImageView iView,URL url,IOnImageLoaded imageLoaded){
		this(ctx,iView,url);
		this.onImageLoad=imageLoaded;
		
		
	}
	
	public TwitterImageDownloadTask(Context ctx,ImageView iView,URL url){
		this.iView=iView;
		this.requestImage=url;
		this.ctx=ctx;
		count++;
		Log.w("facebook-collumn", "Now are running "+count+"tasks");
		
		
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		if(requestImage==null){
			
				Account acc=Account.getLoggedUsers(ctx).get(0);
				acc.setProfileImage(acc.processProfileImage());
				requestImage=acc.getProfileImage();
		}
		imgBit = ImageUtils.imageCache.get(requestImage);
		if (imgBit == null) {

			imgBit = ImageUtils.loadProfileImages(requestImage,ctx);
			ImageUtils.imageCache.put(requestImage, imgBit);

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		iView.setImageBitmap(imgBit);
		
		if(onImageLoad!=null){
			onImageLoad.onImageLoaded(iView);
		}
		
		count--;
		
	}
	
	
	public interface IOnImageLoaded{
		public void onImageLoaded(ImageView img);
	}
	

}
