package org.besystem.stockit.stockit;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.besystem.stockit.stockit.Entities.Order;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {

    //orders est la liste des models à afficher
    public OrderAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_order,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.id = (TextView) convertView.findViewById(R.id.order_id);
            viewHolder.ttc = (TextView) convertView.findViewById(R.id.order_ttc);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> orders
        Order order = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.id.setText(order.getId());
        viewHolder.ttc.setText(order.getTtc());

        return convertView;
    }

    private class TweetViewHolder{
        public TextView id;
        public TextView ttc;
    }
}
