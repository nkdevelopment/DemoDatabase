package development.nk.demodatabase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by NKdevelopment on 5/7/2017.
 */

public class BackgroundTask extends AsyncTask<String, Void, String> {

    Context ctx;

    // Constructor
    BackgroundTask(Context ctx) {
        this.ctx=ctx;
    }

    @Override
    protected String doInBackground(String... params) {

        String reg_url="http://nkdevelopment.net/project/insert_app.php";
        String method=params[0];
        if(method.equals("insert")) {

            String name=params[1];
            String password=params[2];
            String contact=params[3];
            String country=params[4];

            try {
                URL url= new URL(reg_url); // we create the URL object passing the string reg_url

                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true); // is used for POST requests

                OutputStream os= httpURLConnection.getOutputStream(); // creates OutputStream object

                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("contact","UTF-8")+"="+URLEncoder.encode(contact,"UTF-8")+"&"+
                        URLEncoder.encode("country","UTF-8")+"="+URLEncoder.encode(country,"UTF-8");

                Log.i("data= ", data);

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is=httpURLConnection.getInputStream();
                is.close();

                return "Insertion success";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
//        super.onPostExecute(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
