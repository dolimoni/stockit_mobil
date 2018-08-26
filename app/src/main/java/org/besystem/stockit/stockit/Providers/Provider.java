package org.besystem.stockit.stockit.Providers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.besystem.stockit.stockit.Interfaces.OnTaskCompleted;
import org.besystem.stockit.stockit.MainActivity;
import org.besystem.stockit.stockit.Services.HttpHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Provider extends AsyncTask<Void, Void, Void> {

    private ProgressDialog pDialog;

    private Context context;
    // URL to get contacts JSON
    private String url = "";

    private OnTaskCompleted listener;

    private JSONArray orders;

    private String rootNode;

    private HashMap<String,String> params;

    ArrayList<HashMap<String, String>> orderList;

    private String TAG;

    public Provider(Context context,String url,OnTaskCompleted listener) {
        this.context = context;
        TAG=context.getClass().getSimpleName();
        this.url=url;

        orderList = new ArrayList<>();

        params=new HashMap<>();

        this.listener=listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(context);

        pDialog.setMessage("Veuillez patienter...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response

        String jsonStr = sh.makeServiceCall(url,this.params);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                orders = jsonObj.getJSONArray(rootNode);

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        listener.onTaskCompleted(orders);
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public String getRootNode() {
        return rootNode;
    }

    public void setRootNode(String rootNode) {
        this.rootNode = rootNode;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
}
