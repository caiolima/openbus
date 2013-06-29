package com.dot.me.assynctask;

import java.net.URL;

import com.dot.me.model.Account;
import com.dot.me.utils.ImageUtils;

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
