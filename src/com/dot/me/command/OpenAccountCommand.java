package com.dot.me.command;

import org.nsoft.openbus.control.AddSocialAccount;

import android.app.Activity;
import android.content.Intent;

public class OpenAccountCommand implements AbstractCommand {

	@Override
	public void execute(Activity activity) {
		
		Intent intent=new Intent(activity,AddSocialAccount.class);
		activity.startActivity(intent);
		
	}

}
