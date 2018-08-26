package org.besystem.stockit.stockit.Entities;

import android.widget.ImageView;
import android.widget.TextView;

public class Order {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTtc() {
        return ttc;
    }

    public void setTtc(String ttc) {
        this.ttc = ttc;
    }

    public Order(String id, String ttc) {
        this.id = id;
        this.ttc = ttc;
    }

    private String id;
    private String ttc;


}
