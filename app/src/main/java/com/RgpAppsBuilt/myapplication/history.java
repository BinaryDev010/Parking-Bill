
package com.RgpAppsBuilt.myapplication;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class history extends AppCompatActivity {
    EditText txtvalue;
    Button btnfetch;
    ListView listview;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();

        broadcastReceiver = new networkBroardcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        txtvalue = (EditText)findViewById(R.id.search);
        btnfetch = (Button)findViewById(R.id.searchdata);
        listview = (ListView)findViewById(R.id.listView);
        btnfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtvalue.getText().toString().length()>10)
                {
                    Toast.makeText(history.this,"!!! Please enter valid vehicle number...",Toast.LENGTH_SHORT).show();
                    txtvalue.requestFocus();
                }
                else{
                    getData();
                }
            }
        });
    }


    private void getData() {

        String value = txtvalue.getText().toString().trim();

        ProgressDialog pr=new ProgressDialog(history.this);
        pr.show();
        pr.setTitle("Searching...");
        pr.setMessage("Please wait while searching...");
        pr.setCanceledOnTouchOutside(false);


        if (value.equals("")) {
            Toast.makeText(this, "!!!Please enter vehicle number...", Toast.LENGTH_LONG).show();
            pr.dismiss();
            return;
        }

        String url = Config5.MATCHDATA_URL + txtvalue.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
                pr.dismiss();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(history.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config5.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String oname = jo.getString(Config5.KEY_ownname);
                String mnum = jo.getString(Config5.KEY_mblnum);
                String vtype = jo.getString(Config5.KEY_vehtype);
                String d= jo.getString(Config5.KEY_date);
                String t = jo.getString(Config5.KEY_time);
                String loc = jo.getString(Config5.KEY_location);




                final HashMap<String, String> employees = new HashMap<>();
                employees.put(Config5.KEY_ownname,"Owner name:   "+oname);
                employees.put(Config5.KEY_mblnum,"Mobile number:   "+mnum);
                employees.put(Config5.KEY_vehtype,"Vehicle type:   "+vtype);
                employees.put(Config5.KEY_date,"Date:   "+d);
                employees.put(Config5.KEY_time,"Time:  "+t);
                employees.put(Config5.KEY_location,"Location:   "+loc);


                list.add(employees);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                history.this, list, R.layout.list_item,
                new String[]{Config5.KEY_ownname, Config5.KEY_mblnum, Config5.KEY_vehtype, Config5.KEY_date, Config5.KEY_time, Config5.KEY_location},
                new int[]{R.id.ownname, R.id.mblnum, R.id.vehtype,R.id.date,R.id.time,R.id.location});

        listview.setAdapter(adapter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
