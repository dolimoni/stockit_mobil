package org.besystem.stockit.stockit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.besystem.stockit.stockit.Activities.OrderdetailsActivity;
import org.besystem.stockit.stockit.Providers.Provider;
import org.besystem.stockit.stockit.Interfaces.OnTaskCompleted;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements OnTaskCompleted {


    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

    // URL to get contacts JSON
    private static String url = "https://fiori.ga/admin/api/provider/getOrders";

    ArrayList<HashMap<String, String>> orderListHM;


    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orderListHM = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        //new GetOrders().execute();

        String url = "https://fiori.ga/admin/api/provider/getOrders";
        Provider provider = new Provider(MainActivity.this,url,this);
        provider.setRootNode("orders");
        HashMap<String,String> params=new HashMap();
        params.put("id","6");
        provider.setParams(params);
        provider.execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                HashMap item = (HashMap)adapterView.getAdapter().getItem(position);
                String id = (String)item.get("id");

                Intent myIntent = new Intent(MainActivity.this, OrderdetailsActivity.class);
                myIntent.putExtra("order_id", id); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskCompleted(JSONArray orderList) {
        /**
         * Updating parsed JSON data into ListView
         * */

        // looping through All orders

        try {
            for (int i = 0; i < orderList.length(); i++) {
                JSONObject c = orderList.getJSONObject(i);
                String id = c.getString("id");
                String paymentDate = c.getString("paymentDate");
                String ttc = c.getString("ttc");
                // tmp hash map for single order
                HashMap<String, String> order = new HashMap<>();

                // adding each child node to HashMap key => value
                order.put("id", id);
                order.put("ttc", ttc);
                order.put("paymentDate", paymentDate);

                // adding order to order list
                orderListHM.add(order);
            }
        }
        catch (final JSONException e){
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, orderListHM,
                R.layout.list_item, new String[]{"id", "ttc",
                "paymentDate"}, new int[]{R.id.orde_id,
                R.id.order_ttc, R.id.order_paymentDate});

        lv.setAdapter(adapter);
    }


}
