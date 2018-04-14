package lirik.euleritychallenge;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.text.ParseException;
import java.util.Iterator;
import org.json.JSONParser;
import org.json.simple.*;
import org.json.JSONArray;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {

    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHit = (Button)findViewById(R.id.btnHit);
        data = (TextView)findViewById(R.id.jsonItem);
        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new JSONTask().execute("https://eulerity-hackathon.appspot.com/image");
            }
        });
    }

public class JSONTask extends AsyncTask<String,String,String>
{

    @Override
    protected String doInBackground(String... urls)
    {
        HttpURLConnection connection = null;
        BufferedReader reader =null;
        try{
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line ="";
            while((line = reader.readLine())!=null)
            {
                buffer.append(line);
            }
             JSONParser parser = new JSONParser();

            try {

                Object obj = parser.parse(new FileReader("https://eulerity-hackathon.appspot.com/image"));

                JSONObject jsonObject = (JSONObject) obj;
                System.out.println(jsonObject);

                String url1 = (String) jsonObject.get("url");


                String createdDate = (String) jsonObject.get("created");
                String updatedDate = (String) jsonObject.get("updated");
                // loop array
                JSONArray msg = new JSONArray();
                Iterator<String> iterator;
                while (iterator.hasNext()) {
                    System.out.println(iterator.next());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return  buffer.toString();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if(connection!=null)
                connection.disconnect();
            try{
                if(reader !=null)
                    reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        data.setText(result);
    }
}}