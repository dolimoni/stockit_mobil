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

public class OrderdetailsActivity extends AppCompatActivity implements OnTaskCompleted {


    ArrayList<HashMap<String, String>> productsListHM;
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productsListHM = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);


        String url = "https://fiori.ga/admin/api/provider/getOrder";
        Provider provider = new Provider(OrderdetailsActivity.this,url,this);
        provider.setRootNode("order_product");
        HashMap<String,String> params=new HashMap();
        Intent intent = getIntent();
        String order_id = intent.getStringExtra("order_id");
        params.put("id",order_id);
        provider.setParams(params);
        provider.execute();


    }

    @Override
    public void onTaskCompleted(JSONArray orderList) {
        try {
            for (int i = 0; i < orderList.length(); i++) {
                JSONObject c = orderList.getJSONObject(i);
                String product_name = c.getString("product_name");
                String quantity = c.getString("quantity");
                String paymentDate = c.getString("quantity");
                // tmp hash map for single order
                HashMap<String, String> order = new HashMap<>();

                // adding each child node to HashMap key => value
                order.put("product_name", product_name);
                order.put("product_quantity", quantity);
                order.put("paymentDate", paymentDate);

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
                OrderdetailsActivity.this, productsListHM,
                R.layout.order_product_item, new String[]{"product_name", "product_quantity",
                "paymentDate"}, new int[]{R.id.product_name,
                R.id.product_quantity, R.id.order_paymentDate});

        lv.setAdapter(adapter);
    }
}
