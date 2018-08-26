package org.besystem.stockit.stockit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.besystem.stockit.stockit.Interfaces.OnTaskCompleted;
import org.besystem.stockit.stockit.MainActivity;
import org.besystem.stockit.stockit.Providers.Provider;
import org.besystem.stockit.stockit.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Customers extends AppCompatActivity implements OnTaskCompleted {


    ArrayList<HashMap<String, String>> productsListHM;
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productsListHM = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);


        String url = "http://149.91.80.68/stockitmain/customer/getall";
        Provider provider = new Provider(Customers.this,url,this);
        provider.setRootNode("customers");

        HashMap<String,String> params=new HashMap();
        params.put("id","0");
        provider.setParams(params);
        provider.execute();
    }

    @Override
    public void onTaskCompleted(JSONArray orderList) {
        try {
            for (int i = 0; i < orderList.length(); i++) {
                JSONObject c = orderList.getJSONObject(i);
                String name = c.getString("name");
                // tmp hash map for single order
                HashMap<String, String> order = new HashMap<>();

                // adding each child node to HashMap key => value
                order.put("name", name);

                // adding order to order list
                productsListHM.add(order);
            }
        }
        catch (final JSONException e){
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        catch (final Exception e){
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        ListAdapter adapter = new SimpleAdapter(
                Customers.this, productsListHM,
                R.layout.customer_item, new String[]{"name"}, new int[]{R.id.customer_name});

        lv.setAdapter(adapter);
    }
}
