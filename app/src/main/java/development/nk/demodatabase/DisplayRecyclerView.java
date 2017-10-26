package development.nk.demodatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

public class DisplayRecyclerView extends AppCompatActivity {

    String JSON_STRING;
    JSONObject jsonObject;
    JSONArray jsonArray;
    private RecyclerView mRVFishPrice;
    private ContactAdapterRecyclerView mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recycler_view);

        JSON_STRING=getIntent().getExtras().getString("json_data");

        String name,password,contact,country;
        List<Contacts> data=new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");
            while(count<jsonArray.length()) {

                JSONObject JO= jsonArray.getJSONObject(count);
                name = JO.getString("name");
                password = JO.getString("password");
                contact = JO.getString("contact");
                country = JO.getString("country");

                Contacts contacts=new Contacts(name,password,contact,country);
                data.add(contacts);

                Log.i("RecyclerView: name= ", ""+contacts.getName());

                count++;
            }

            Log.i("RecyclerView: list= ", ""+data.toString());


            // Setup and Handover data to recyclerview
            mRVFishPrice = (RecyclerView)findViewById(R.id.myRecyclerView);
            mAdapter = new ContactAdapterRecyclerView(DisplayRecyclerView.this, data);
            mRVFishPrice.setAdapter(mAdapter);
            mRVFishPrice.setLayoutManager(new LinearLayoutManager(DisplayRecyclerView.this));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
