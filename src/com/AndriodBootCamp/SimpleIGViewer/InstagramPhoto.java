package com.AndriodBootCamp.SimpleIGViewer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramPhoto {
	public String _username;
	public String _caption;
	public String _imageUrl;
	public int _imageHeight;
	public int _likesCount;
	public String _userProfileImage;
	public long _createdTime;
	public ArrayList<String> _comments;
	
	//comments->data[i].text = 
	//comments->data[i].from=> username, profile_picture; id: full_name
	
	

	public InstagramPhoto(String name, String caption, String imageUrl, 
			int height, int count, String profilePhoto, long createdTime, 
			ArrayList<String> comments){
		_username = name;
		_caption = caption;
		_imageUrl = imageUrl;
		_imageHeight = height;
		_likesCount = count;
		_userProfileImage = profilePhoto;
		_createdTime = createdTime;
		_comments = new ArrayList<String>(comments);

	}
	
	public InstagramPhoto()
	{
		_username = null;
		_caption = null;
		_imageUrl = null;
		_imageHeight = 0;
		_likesCount = 0;
		_userProfileImage = null;
		_createdTime = 0;
		_comments = new ArrayList<String>();
	}

	public void PopulateDataFromJSON(JSONObject photoJSON) 
	{
		// {"data"=>[x] => "images" => "standard_resolution" => "url"
		// {"data"=>[x] => "images" => "standard_resolution" => "height"
		// {"data"=>[x] => "user" => "username"
		// {"data"=>[x] => "caption"=>"text"
		// user_profile_image
		// first 2 comments
		// like count
		try {
			if (photoJSON.getJSONObject("user") != null) {
				this._username = photoJSON.getJSONObject("user").getString(
						"username");
				this._userProfileImage = photoJSON.getJSONObject("user")
						.getString("profile_picture");
			}
			if (photoJSON.getJSONObject("caption") != null) {
				this._caption = photoJSON.getJSONObject("caption").getString(
						"text");
			}

			this._imageUrl = photoJSON.getJSONObject("images")
					.getJSONObject("standard_resolution").getString("url");
			this._imageHeight = photoJSON.getJSONObject("images")
					.getJSONObject("standard_resolution").getInt("height");

			if (photoJSON.getJSONObject("likes") != null) {
				this._likesCount = photoJSON.getJSONObject("likes").getInt(
						"count");
			}

			this._createdTime = photoJSON.getLong("created_time");

			//get the last two comments
			if (photoJSON.getJSONObject("comments") != null) {
				JSONArray commentsJSON = photoJSON.getJSONObject("comments")
						.getJSONArray("data");
				// Just adding the first two comments if exist
				int count = commentsJSON.length() > 2 ? commentsJSON.length()-2 : 0 ;
				for (int j = commentsJSON.length()-1; j >= count; j--) {
					this._comments.add(commentsJSON.getJSONObject(j).getString(
							"text"));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
//	public String toString() {
//		return ("Hello" + _imageUrl);
//	}
}
