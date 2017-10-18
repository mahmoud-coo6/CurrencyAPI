package com.example.android.currencyapi;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView from,to,amount,result;
    Spinner sFrom,sTo;
    EditText eAmount,eResult;
    Button resultBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        from=(TextView)findViewById(R.id.from);
        to=(TextView)findViewById(R.id.to);
        amount=(TextView)findViewById(R.id.amount);
        result=(TextView)findViewById(R.id.result);

        sFrom=(Spinner)findViewById(R.id.from_spiner);
        sTo=(Spinner)findViewById(R.id.to_spiner);

        eAmount=(EditText)findViewById(R.id.amount_edittext);
        eResult=(EditText)findViewById(R.id.result_edittext);

        resultBtn=(Button)findViewById(R.id.btn);

        ArrayList<String> currency=new ArrayList<>();
        currency.add("USD");
        currency.add("AUD");
        currency.add("CAD");
        currency.add("PLN");
        currency.add("MXN");
        currency.add("EUR");

        // https://apilayer.net/api/convert?access_key=c0156b2a8d0796dc607083bb9e8a8c7e&from=USD&to=EUR&amount=25&format%20=%201

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,currency);

        sFrom.setAdapter(adapter);
        sTo.setAdapter(adapter);

//        final String ACCESS_KEY = "94421ea053d6e5869ad9806259602080";
//        final String BASE_URL = "https://apilayer.net/api/";
//        final String ENDPOINT = "convert";

        final String ACCESS_KEY = "c0156b2a8d0796dc607083bb9e8a8c7e";
        final String BASE_URL = "https://apilayer.net/api/";
        final String ENDPOINT = "convert";

//         final CloseableHttpClient httpClient = HttpClients.createDefault();


            resultBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {
                    String from = sFrom.getSelectedItem().toString();
                    String to = sTo.getSelectedItem().toString();
                    String amount=eAmount.getText().toString();

                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    String url=BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&from="+from+"&to="+to+"&amount="+amount;

                    JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("JSON",response.toString());

                            try {
                                double result= response.getDouble("result");
//                                Log.d("resultinggssfsfsf",result+"");

                                if (result > 0 )
                                eResult.setText(String.valueOf(result));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    queue.add(request);

                }
            });
        }
}
