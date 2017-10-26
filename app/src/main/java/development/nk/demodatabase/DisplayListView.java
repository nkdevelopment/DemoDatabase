package development.nk.demodatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayListView extends AppCompatActivity {

    String JSON_STRING;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_listview_layout);

        listView=(ListView)findViewById(R.id.listview);

        contactAdapter=new ContactAdapter(this, R.layout.row_layout);
        listView.setAdapter(contactAdapter);


        JSON_STRING=getIntent().getExtras().getString("json_data");

        String name,password,contact,country;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            int count=0;
            jsonArray=jsonObject.getJSONArray("server_response");
            while(count<jsonArray.length()) {

                JSONObject JO= jsonArray.getJSONObject(count);
                name = JO.getString("doctor_id");
                password = JO.getString("name");
                contact = JO.getString("surname");
                country = JO.getString("region_id");

                Contacts contacts=new Contacts(name,password,contact,country);

                contactAdapter.add(contacts);

                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
