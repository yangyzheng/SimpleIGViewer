package com.AndriodBootCamp.SimpleIGViewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;

public class PhotoesActivity extends Activity {
	public static final String CLIENT_ID = "7dec6510876a4e2685f7dc2655c66682";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotoAdapter aPhotos; 
	//private SwipeRefreshLayout swipeContainer;
	//ListView lvPhotos;
	PullToRefreshListView lvPhotos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoes);
		lvPhotos = (PullToRefreshListView)findViewById(R.id.lvPhotoes);
		lvPhotos.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				fetchPopularPhotos(); //send request and receive response		
			}
		});
		
		fetchPopularPhotos();
		
//		//at the top, swipe down will refresh
//		//otherwise just scroll up/down the listView
//		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//		 // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
//			
//			@Override
//			public void onRefresh() {
//				// TODO Auto-generated method stub
//				fetchPopularPhotos(); //send request and receive response		
//			}
//		});
//       
//        
//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(
//        		android.R.color.holo_blue_bright, 
//                android.R.color.holo_green_light, 
//                android.R.color.holo_orange_light, 
//                android.R.color.holo_red_light);
		
        
	}

	private void fetchPopularPhotos() {
		//https://api.instagram.com/v1/media/popular?client_id=7dec6510876a4e2685f7dc2655c66682		
		//Setup popular url endpoint	
		//get ListView, set data source to the ListView
		
		photos = new ArrayList<InstagramPhoto>(); 
		aPhotos = new InstagramPhotoAdapter(this, photos);
		lvPhotos.setAdapter(aPhotos);
	
		String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
		
		//create the network client
		AsyncHttpClient client = new AsyncHttpClient();
		
		//Trigger the network request
		client.get(popularUrl, new JsonHttpResponseHandler(){
			//define the success and failure callback, async
			//handle the successful response (popular photoes JSON)
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				// fired once the successful response back 
				//response is popular photo json
				//Log.i("INFO", response.toString());
				
				JSONArray photoesJSON = null;
				try{
					photos.clear();
					photoesJSON = response.getJSONArray("data");
					for (int i = 0; i < photoesJSON.length(); i++){
						JSONObject photoJSON = photoesJSON.getJSONObject(i);
						InstagramPhoto photo = new InstagramPhoto();
						photo.PopulateDataFromJSON(photoJSON);										
															
						//Log.i("DEBUG", photo.toString());
						photos.add(photo);
					}
					//notify the adapter that it should populate new changes to the listview
					aPhotos.notifyDataSetChanged();					
				}
				catch(JSONException e){
					e.printStackTrace();
				}
				
				//swipeContainer.setRefreshing(false);
				lvPhotos.onRefreshComplete();
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				Log.d("DEBUG", "Fetch photo error: " + throwable.toString());
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photoes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}


//The SwipeRefreshLayout should be used whenever the user can refresh the contents of a view via 
//a vertical swipe gesture. The activity that instantiates this view should add an OnRefreshListener
//to be notified whenever the swipe to refresh gesture is completed. The SwipeRefreshLayout will notify
//the listener each and every time the gesture is completed again; the listener is responsible for correctly
//determining when to actually initiate a refresh of its content. If the listener determines there should not
//be a refresh, it must call setRefreshing(false) to cancel any visual indication of a refresh. If an activity
//wishes to show just the progress animation, it should call setRefreshing(true). To disable the gesture and
//progress animation, call setEnabled(false) on the view.
//
//This layout should be made the parent of the view that will be refreshed as a result of the gesture and can only
//support one direct child. This view will also be made the target of the gesture and will be forced to match both
//the width and the height supplied in this layout. The SwipeRefreshLayout does not provide accessibility events;
//instead, a menu item must be provided to allow refresh of the content wherever this gesture is used.