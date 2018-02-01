package com.magicrecipe.abhilashdesai.magicrecipe;
/**
 * Created by Abhilash on 31-01-2018.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
TextView na,em,hr,tu;
ImageView ii;

     String url = "http://www.recipepuppy.com/api/?i=";

    ArrayList<HashMap<String, String>> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        na=(TextView)findViewById(R.id.name);
        em=(TextView)findViewById(R.id.email);


        String value = getIntent().getStringExtra("EXTRA_SESSION_ID");
        foodList = new ArrayList<>();


url=url+value+"&p=2";
        lv = (ListView) findViewById(R.id.list);

        new GetRecipe().execute();
    }


    private class GetRecipe extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

                      String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray food = jsonObj.getJSONArray("results");


                    for (int i = 0; i < food.length(); i++) {
                        JSONObject c = food.getJSONObject(i);

                        String id1 = c.getString("title");
                        String name2 = c.getString("ingredients");
                        String site1=c.getString("href");
                        String id="Dish: "+id1;
                        id=id.replaceAll("\n","");
                        id=id.replaceAll("\t","");
                        String name="Ingredients: "+name2;
                        String pic = c.getString("thumbnail");
String site="Website: "+site1;
                       

                        HashMap<String, String> foods = new HashMap<>();

                        // adding each child node to HashMap key => value
                        foods.put("title", id);
                        foods.put("ingredients", name);
                        foods.put("thumbnail", pic);
                        foods.put("href", site);

                        foodList.add(foods);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, foodList,
                    R.layout.list_item, new String[]{"title","ingredients"}, new int[]{R.id.name,
                   R.id.email});

            lv.setAdapter(adapter);
         int xxx=adapter.getCount();
            String xx=Integer.toString(xxx);
if(xxx==0)
{
    Toast.makeText(getApplicationContext(),"No such ingredients found please check input.",Toast.LENGTH_LONG).show();
    finish();
}
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent xx=new Intent(MainActivity.this,detail.class);

                    Bundle extras = new Bundle();
                    extras.putString("title",foodList.get(position).get("title").toString() );
                    extras.putString("ing",foodList.get(position).get("ingredients").toString());
                    extras.putString("href",foodList.get(position).get("href").toString());
                    extras.putString("thumbnail",foodList.get(position).get("thumbnail").toString());

                    xx.putExtras(extras);
                    startActivity(xx);

                }
            });

        }

    }
}
