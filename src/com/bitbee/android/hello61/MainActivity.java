package com.bitbee.android.hello61;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bitbee.android.util.JSONParser;

public class MainActivity extends ListActivity {

	// url to make request
	private static String url = "http://www.wczhs.com/admin/wczhs/course_memberships/json_data.json?u=944";
	
	// JSON Node names
	private static final String TAG_JSON = "rows";
	private static final String TAG_ID = "id";
	private static final String TAG_COURSE = "course";
	private static final String TAG_PATRIARCH = "patriarch";
	private static final String TAG_DATE_OF_FILING = "date_of_filing";


	// contacts JSONArray
	JSONArray contacts = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.course_list);
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();


		try {
			
			// Creating JSON Parser instance
			JSONParser jParser = new JSONParser();
			Log.d("CXF", url);
			// getting JSON string from URL
			JSONObject json = jParser.getJSONFromUrl(url, null);
			// Getting Array of Contacts
			contacts = json.getJSONArray(TAG_JSON);

			
			
			
			// looping through All Contacts
			for(int i = 0; i < contacts.length(); i++){
				JSONObject c = contacts.getJSONObject(i);
				
				JSONObject cp = c.getJSONObject("CourseMembership");			
				String id = cp.getString("id");	
				String patriarch = cp.getString("patriarch");
				String date_of_filing = cp.getString("date_of_filing");

				
				JSONObject user = c.getJSONObject("User");				
				String name = user.getString("user_nicename");

				
				JSONObject ce = c.getJSONObject("Course");
				String course = ce.getString("name");
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				
				// adding each child node to HashMap key => value
				map.put(TAG_ID, id);
				map.put(TAG_COURSE, course);
				//map.put(TAG_PATRIARCH, patriarch);
				map.put(TAG_DATE_OF_FILING, date_of_filing);

				// adding HashList to ArrayList
				contactList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("CXF", e.toString());
		}
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, contactList,
				R.layout.course_list,
				new String[] {TAG_ID, TAG_COURSE, TAG_DATE_OF_FILING }, new int[] {R.id.id, R.id.course, R.id.date_of_filing } );

		setListAdapter(adapter);
		
		// selecting single ListView item
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				
				/*
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String cost = ((TextView) view.findViewById(R.id.email)).getText().toString();
				String description = ((TextView) view.findViewById(R.id.mobile)).getText().toString();
				*/
				
				
				String course_id = ((TextView) view.findViewById(R.id.id)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), CourseDetailActivity.class);
				//in.putStringArrayListExtra("test", )
				//in.putExtra(TAG_NAME, name);
				//in.putExtra(TAG_EMAIL, cost);
				//in.putExtra(TAG_PHONE_MOBILE, description);
				in.putExtra("id", course_id);
				startActivity(in);

			}
		});		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
