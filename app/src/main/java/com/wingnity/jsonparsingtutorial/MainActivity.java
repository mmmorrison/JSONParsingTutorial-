package com.wingnity.jsonparsingtutorial;


import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;



import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity {
	
	ArrayList<Decisions> actorsList;
	
	ActorAdapter adapter;

//	new JSONAsyncTask().execute("http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		actorsList = new ArrayList<Decisions>();
		new JSONAsyncTask().execute("https://thisorthatdb.herokuapp.com/posters/decisions/");
		ListView listview = (ListView)findViewById(R.id.list);
		adapter = new ActorAdapter(getApplicationContext(), R.layout.row, actorsList);
		
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), actorsList.get(position).getTitle(), Toast.LENGTH_LONG).show();
			}
		});
	}


	class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}
		
		@Override
		protected Boolean doInBackground(String... urls) {
			try {
				
				//------------------>>
				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);

				// StatusLine stat = response.getStatusLine();
				int status = response.getStatusLine().getStatusCode();

				if (status == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);
					
				
					JSONObject jsono = new JSONObject(data);

					JSONArray jarray = jsono.getJSONArray("decisions");

					
					for (int i = 0; i < jarray.length(); i++) {
						JSONObject object = jarray.getJSONObject(i);
					
						Decisions actor = new Decisions();
						
						actor.setTitle(object.getString("title"));
						actor.setCategory(object.getString("category"));
//						actor.setDob(object.getString("dob"));
//						actor.setCountry(object.getString("country"));
//						actor.setHeight(object.getString("height"));
//						actor.setSpouse(object.getString("spouse"));
//						actor.setChildren(object.getString("children"));
//						actor.setImage(object.getString("image"));
//
						actorsList.add(actor);
					}
					return true;
				}
				
				//------------------>>
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		protected void onPostExecute(Boolean result) {
			dialog.cancel();
			adapter.notifyDataSetChanged();
			if(result == false)
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

		}
	}
	
	

	
	
	
}
