package com.cloudspace.tilt.Twitter;

import java.io.File;

import com.cloudspace.tilt.MainActivity;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.view.View;
import android.widget.Toast;

public class TwitterTools {
	public static MainActivity activity;
	
	// Pass in the main activity to manipulate the UI
	public TwitterTools(MainActivity mainActivity) {
		activity = mainActivity;
	}
	
	public void share(final File sharePath, final String statusUpdate){
		// Get passed in file path
		Thread t = new Thread() {
	        public void run() {
	        	try{
	        		// Update status and inform user
	        		Twitter twitter = TwitterUtils.twitter;
	        		File file = sharePath;
	                StatusUpdate status = new StatusUpdate(statusUpdate);
	                status.setMedia(file);
	                twitter.updateStatus(status);
	                activity.runOnUiThread(new Runnable() {
	                	public void run() {
	                		activity.progressbar.setVisibility(View.INVISIBLE);
	                		Toast.makeText(activity,"Image Posted",Toast.LENGTH_SHORT).show();
	                	}
	                });
	            }
	            catch(TwitterException e){
	            	// Inform user of failure and log them out
	            	activity.runOnUiThread(new Runnable() {
	            		public void run() {
	            			activity.progressbar.setVisibility(View.INVISIBLE);
	            			activity.twitterAuthed = false; 
	            			Toast.makeText(activity,"There was an issue sharing your photo. Please try again.",Toast.LENGTH_SHORT).show();
	            		}
	            	});
	                try {
						throw e;
					} catch (TwitterException e1) {
						e1.printStackTrace();
					}
	            }
	        }
 		}    ;
 		t.start();
	}
}
