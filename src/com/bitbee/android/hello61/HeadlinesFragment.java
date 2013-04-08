/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bitbee.android.hello61;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bitbee.android.util.JSONParser;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HeadlinesFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;
    
    private static String url = "http://www.wczhs.com/admin/wczhs/course_memberships/json_data.json?u=944";
    
	// contacts JSONArray
	JSONArray courses = null;
	
	private static final String TAG_JSON = "rows";
	private static final String TAG_ID = "id";
	private static final String TAG_COURSE = "course";
	
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> courseList = new ArrayList<HashMap<String, String>>();

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        
        setCourseList();
     
       // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<HashMap<String, String>>(getActivity(), layout, courseList));
    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.article_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        mCallback.onArticleSelected(position);
        
        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
    }
    
	public void setCourseList() {
		try {

			// Creating JSON Parser instance
			JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			JSONObject json = jParser.getJSONFromUrl(url, null);
			// Getting Array of Contacts
			courses = json.getJSONArray(TAG_JSON);

			// looping through All Contacts
			for (int i = 0; i < courses.length(); i++) {
				JSONObject c = courses.getJSONObject(i);

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
				map.put(TAG_COURSE, course + "[" + date_of_filing + "]");


				// adding HashList to ArrayList
				courseList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}