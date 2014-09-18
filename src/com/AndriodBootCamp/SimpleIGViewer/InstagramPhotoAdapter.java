package com.AndriodBootCamp.SimpleIGViewer;

import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {

	public InstagramPhotoAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, R.layout.item_photo, photos);
	}

	//takes a data item at a position, converts it to a row in the listview 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// take the data source at position (i.e. 0)
		// get the data item
		InstagramPhoto photo = getItem(position);
		//check if we are using a recycled view
		if (convertView == null){
			//loading view from template using layout inflator
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);	
		}
		//lookup the subview within the template
		TextView tvuserName = (TextView)convertView.findViewById(R.id.tvuserName);
		TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
		TextView tvTime = (TextView)convertView.findViewById(R.id.tvTime);
		TextView tvLikes = (TextView)convertView.findViewById(R.id.tvLikes);
		TextView tvComment1 = (TextView)convertView.findViewById(R.id.tvComment1);
		TextView tvComment2 = (TextView)convertView.findViewById(R.id.tvComment2);
		
		ImageView imagePhoto = (ImageView)convertView.findViewById(R.id.imagePhoto);
		ImageView imageUser = (ImageView)convertView.findViewById(R.id.imageUser);
		
		String timeSpan = DateUtils.getRelativeTimeSpanString(photo._createdTime*1000, 
				System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS).toString();
		//populate the subview (textview, imageview) with the correct data
		tvuserName.setText(photo._username);
		tvCaption.setText(photo._caption);
		tvTime.setText(timeSpan);
		tvLikes.setText(""+ photo._likesCount + " likes");
		//set user profile photo
		imageUser.setImageResource(0);
		Picasso.with(getContext()).load(photo._userProfileImage).into(imageUser);
		//Set the image height before loading
		imagePhoto.getLayoutParams().height = photo._imageHeight;
		//Reset the image from the recycled view, clean up the old image
		imagePhoto.setImageResource(0);
		//ask for the photo to be added to the image view based on the photo URL
		//send a netowrk request to the url, download the image bytes, resizing the image and insert it into the imageView
		//Async
		Picasso.with(getContext()).load(photo._imageUrl).into(imagePhoto);	
		
		switch (photo._comments.size()){
		
		case 1:
			tvComment1.setText(photo._comments.get(0));
			tvComment1.setVisibility(View.VISIBLE);
			tvComment2.setVisibility(View.GONE);
			break;
		case 2:
			tvComment1.setText(photo._comments.get(0));
			tvComment1.setVisibility(View.VISIBLE);
			tvComment2.setText(photo._comments.get(1));
			tvComment2.setVisibility(View.VISIBLE);
			break;
		case 0:
		default:
			tvComment1.setText("No comment.");
			tvComment2.setVisibility(View.GONE);
		}
		
		
		// user profile image, username, relative timestamp
		//like count, last two comments
		//same proportion with the instagram
			
		//return the view for that data item
		return convertView;
	}

	// getView method (int position)
	// Default, take the model InstagramPhoto call toString();

}
