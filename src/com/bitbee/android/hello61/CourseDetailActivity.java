package com.bitbee.android.hello61;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitbee.android.util.JSONParser;

public class CourseDetailActivity extends Activity {
	
	private static String url = "http://www.wczhs.com/app/files/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//
		
		setView();
		
		
		
		/*
		setContentView(R.layout.activity_course_detail);
		Bundle bunde = this.getIntent().getExtras();
		
        TextView article = (TextView) findViewById(R.id.article);
        
		JSONParser jParser = new JSONParser();
		
		//Log.d("CXF", String.valueOf(bunde.getLong("id")));

		JSONObject json = jParser.getJSONFromUrl(url + bunde.getString("id")  +".json", null);
		
        try {
			article.setText(json.getJSONObject("c").getJSONObject("CourseMembership").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.course_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void setView(){
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		TextView label = new TextView(this);
		TextView content = new TextView(this);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
		     LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		
		Bundle bunde = this.getIntent().getExtras();  
		JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(url + bunde.getString("id")  +".json", null);
		
		JSONObject cp = null;
        try {
        	cp = json.getJSONObject("c").getJSONObject("CourseMembership");
        	
    		label = new TextView(this);
    		content = new TextView(this);
    		label.setText("宝宝今天上课对哪项活动最感兴趣：");
    		content.setText (cp.getString("interest"));
    		ll.addView(label, layoutParams);
    		ll.addView(content, layoutParams);
    		
    		label = new TextView(this);
    		content = new TextView(this);
    		label.setText("宝宝今天上课什么地方不一样：");
    		content.setText (cp.getString("different"));
    		ll.addView(label, layoutParams);
    		ll.addView(content, layoutParams); 
    		
    		label = new TextView(this);
    		content = new TextView(this);
    		label.setText("宝宝今天让你印象最深刻的是：");
    		content.setText (cp.getString("impression"));
    		ll.addView(label, layoutParams);
    		ll.addView(content, layoutParams); 
    		
    		label = new TextView(this);
    		content = new TextView(this);
    		label.setText("您的意见和建议：");
    		content.setText (cp.getString("suggest"));
    		ll.addView(label, layoutParams);
    		ll.addView(content, layoutParams);   
    		
    		label = new TextView(this);
    		content = new TextView(this);
    		label.setText("宝宝在活动中的具体表现：");
    		content.setText (cp.getString("expression"));
    		ll.addView(label, layoutParams);
    		ll.addView(content, layoutParams);       		
    		
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		
		
		setContentView(ll);		
	}

}
