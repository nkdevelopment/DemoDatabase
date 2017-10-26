package development.nk.demodatabase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertValues extends AppCompatActivity {

    private static final String TAG = "InsertValues";
    EditText e_name,e_password,e_contact,e_country;
    String name,password,contact,country;

    private ProgressDialog pDialog;
    private String insertDataURL = "http://nkdevelopment.net/project/insert_app_pdo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
//
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

        e_name = (EditText) findViewById(R.id.name);
        e_password = (EditText) findViewById(R.id.password);
        e_contact =(EditText) findViewById(R.id.contact);
        e_country = (EditText) findViewById(R.id.country);

    }

    public void insertValues(View view) {

        showpDialog();

        name = e_name.getText().toString();
        password = e_password.getText().toString();
        contact = e_contact.getText().toString();
        country = e_country.getText().toString();

        //-----------------------------------------------------------------------------------------
        // the following is using asynctask
        // this is from udemy course
        // better is volley that is explained parakatw...
        // this is the old classic method
        //
        //String method="insert";
        //BackgroundTask backgroundTask=new BackgroundTask(this);o
        //backgroundTask.execute(method, name, password, contact,country);
        //-----------------------------------------------------------------------------------------

        Log.i(TAG, "insertValues");


        //------------------------------------------------------------------------------------------
        // the following is from the url:
        // http://shaoniiuc.com/android/android-json-parsing-insert-data-into-mysql-database-using-php-mysql-volley-library/
        // android insert data into mysql database using php mysql VOLLEY library
        // this is better handling on background thread because of volley...
        //
        StringRequest request = new StringRequest(Request.Method.POST, insertDataURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("name", name);
                parameters.put("password", password);
                parameters.put("contact", contact);
                parameters.put("country", country);
                hidepDialog();

                return parameters;
            }
        };

        // Adding JsonObjectRequest to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

        Log.i(TAG, "name= "+name+", password= "+password+" ");
        finish();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
