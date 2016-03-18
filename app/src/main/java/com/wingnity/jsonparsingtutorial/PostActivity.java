package com.wingnity.jsonparsingtutorial;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.String;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import com.loopj.android.http.*;



import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by HomeMariaHome on 3/17/16.
 * New class
 */



public class PostActivity extends Activity {
    ArrayList<Decisions> actorsList;

    ActorAdapter adapter;

    AsyncHttpClient client = new AsyncHttpClient();
    client.get("https://www.google.com", new AsyncHttpResponseHandler() {

        @Override
        public void onStart() {
            // called before request is started
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            // called when response HTTP status is "200 OK"
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
        }

        @Override
        public void onRetry(int retryNo) {
            // called when request is retried
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        actorsList = new ArrayList<Decisions>();
        new JSONAsyncTask().execute("https://thisorthatdb.herokuapp.com/voters");
//        new JSONAsyncTask().execute("https://thisorthatdb.herokuapp.com/posters/decisions/");
//        ListView listview = (ListView)findViewById(R.id.list);
//        adapter = new ActorAdapter(getApplicationContext(), R.layout.row, actorsList);
//
//        Button myButton = (Button) findViewById(R.id.button);
//        listview.setAdapter(adapter);
//
//        myButton.setOnClickListener(new OnClickListener() {
//
//        PostExample example = new PostExample();
//        String json = example.bowlingJson();
//
//        try {
//            System.out.println("!making the post");
//            String response = example.post("https://thisorthatdb.herokuapp.com/voters", json);
//            System.out.println("!1!!!!made the post");
//            System.out.println("**** response");
//            System.out.println(response);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        });

//        listview.setOnItemClickListener(new OnItemClickListener() {

//        @Override
//        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//            long id) {
//             TODO Auto-generated method stub
//        Toast.makeText(getApplicationContext(), actorsList.get(position).getTitle(), Toast.LENGTH_LONG).show();


//            }
//        });
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        JSONObject obj = new JSONObject();
//        obj.put()


        String holder =  "{ id:2[\"description\":\"Ajay\",\"id\":30,\"photoURL\":\"ajay@ajay.com\"]}";


//        " { \"Name\" : \"AA\" } ",
//        String holder = "{:["
//                + "{'id': 1,"
//                + " 'votes': + 99,"
//                + " 'winner': 'FALSE'},"
//                + "{'id': 2,"
//                + " 'votes': + 199,"
//                + " 'winner': 'TRUE'},"
//                + "]}";


        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(PostActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                System.out.println("*************");
                System.out.println("what is the url?");
                System.out.println(urls[0]);
                HttpPost httppost = new HttpPost(urls[0]);

                StringEntity se = new StringEntity(holder);
                httppost.setEntity(se);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");
                HttpResponse response = httpclient.execute(httppost);
                System.out.println("*************");
                System.out.println("body");
                System.out.print(httppost);

//                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                System.out.println("**** response from post on front");
                System.out.println(status);

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
            System.out.println("on post execute");
            System.out.println(result);
//            dialog.cancel();
//            adapter.notifyDataSetChanged();
//            if(result == false)
//                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
//
        }
//        public  JSONObject postJson() {
//            System.out.println("in postJson");
//            return {"vote: ["
//                    + "{'id': 1,"
//                    + " 'votes': + 99,"
//                    + " 'winner': 'FALSE'},"
//                    + "{'id': 2,"
//                    + " 'votes': + 199,"
//                    + " 'winner': 'TRUE'},"
//                    + "]}}";
//        }
    }
};




//
//    public class PostExample {
//
//    public final MediaType JSON
//            = MediaType.parse("application/json; charset=utf-8");
//
//    OkHttpClient client = new OkHttpClient();
//
//    String post(String url, String json) throws IOException {
//        RequestBody body = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        System.out.println("*** body to post " + body);
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
//
//    String postJson() {
//        System.out.println("in postJson");
//        return "{'vote':["
//                + "{'id': 1,"
//                + " 'votes': + 99,"
//                + " 'winner': 'FALSE'},"
//                + "{'id': 2,"
//                + " 'votes': + 199,"
//                + " 'winner': 'TRUE'},"
//                + "]}";
//    }
//    }


//
//        return "{'winCondition':'HIGH_SCORE',"
//                + "'name'     :      'Bowling',"
//                + "'round':4,"
//                + "'lastSaved':1367702411696,"
//                + "'dateStarted':1367702378785,"
//                + "'players':["
//                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
//                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
//                + "]}";
//
//    public static void main(String[] args) throws IOException {
//        PostActivity example = new PostActivity();
//        String json = example.bowlingJson();
//        String response = example.post("https://thisorthatdb.herokuapp.com/posters/decisions/post", json);
//        System.out.println(response);
//    }
