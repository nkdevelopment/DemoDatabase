package development.nk.demodatabase;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import development.nk.demodatabase.RoomDatabase.College;
import development.nk.demodatabase.RoomDatabase.SampleDatabase;
import development.nk.demodatabase.RoomDatabase.University;

public class MainActivity extends AppCompatActivity {

    String json_string;
    String JSON_STRING;

    SampleDatabase sampleDatabase;

    SQLiteDatabase sqLiteDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void insertValues(View view) {
        Intent i = new Intent(this, InsertValues.class);
        startActivity(i);
    }


    /*
    * the following method uses Volley to get json object
     */
    public void getjsonVolley(View view) {

        SQLiteDataBaseBuild();
        SQLiteTableBuild();
        DeletePreviousData();

        String json_url = "http://nkdevelopment.net/RateMyDoc/DoctorsForms/getjsonDoctors.php";
        String  REQUEST_TAG = "development.nk.volleyJsonObjectRequest";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(json_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText(response.toString());
                        JSON_STRING = response.toString();

                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("server_response");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject jsonObject;
                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);
                                int tempDoctorId = jsonObject.getInt("doctor_id");
                                String tempName = jsonObject.getString("name");
                                String tempSurname = jsonObject.getString("surname");
                                int tempRegionId = jsonObject.getInt("region_id");
                                String SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME
                                        +" (doctor_id, name, surname, region_id) VALUES('"+tempDoctorId+"', '"+tempName+"', '"+tempSurname+"', '"+tempRegionId+"');";
                                sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("MainActivity", "Error: " + error.getMessage());
//                progressDialog.hide();
            }
        });

        // Adding JsonObjectRequest to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }

    public void SQLiteDataBaseBuild(){
        sqLiteDatabase = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void SQLiteTableBuild(){
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SQLiteHelper.TABLE_NAME+
                "("+SQLiteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                +SQLiteHelper.Table_Column_1_doctor_id+" INT, "
                +SQLiteHelper.Table_Column_2_name+" VARCHAR, "
                +SQLiteHelper.Table_Column_3_surname+" VARCHAR, "
                +SQLiteHelper.Table_Column_4_region_id+" INT);");
    }

    public void DeletePreviousData(){
        sqLiteDatabase.execSQL("DELETE FROM "+SQLiteHelper.TABLE_NAME+"");
    }



    /*
    * the following method gets json object with the traditional method
    * using an AsyncTask
     */
    public void getjson(View view) {
        new BackgroundTask().execute();
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url = "http://nkdevelopment.net/project/getjson.php";

        public BackgroundTask() {
            super();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "/n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim(); // trim removes white space at start or end of the string

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(result);
            JSON_STRING = result;

        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }
    }


    public void jsonToListView(View view) {

        Intent intent = new Intent(this, DisplayListView.class);
        intent.putExtra("json_data", JSON_STRING);
        startActivity(intent);

    }


    public void jsonToRecyclerView(View view) {

        Intent intent = new Intent(this, DisplayRecyclerView.class);
        intent.putExtra("json_data", JSON_STRING);
        startActivity(intent);

    }

    public void roomDatabase(View view) {

        sampleDatabase = Room.databaseBuilder(getApplicationContext(), SampleDatabase.class, "sample-db").build();

        new DatabaseAsync().execute();
    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.
            University university = new University();
            university.setName("MyUniversity");

            College college = new College();
            college.setId(1);
            college.setName("MyCollege");

            university.setCollege(college);

            //Now access all the methods defined in DaoAccess with sampleDatabase object
            sampleDatabase.daoAccess().insertOnlySingleRecord(university);
            sampleDatabase.daoAccess().fetchAllData();
            System.out.println(sampleDatabase.daoAccess().getSingleRecord(1));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //To after addition operation here.
        }

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
