package com.example.nivetha_zuch508.cashcollect;
import com.google.firebase.database.IgnoreExtraProperties;
public class Cust {
    private String custmo;
    private String custname;
    private String address;
    private String city;
    private String state;
    private String dob;

    public Cust(){
        //this constructor is required
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getDob() {
        return dob;
    }

    public Cust(String custmo, String custname, String address, String city1, String state1, String dob1) {
        this.custmo = custmo;
        this.custname = custname;
        this.address = address;
        this.city = state1;
        this.state = city1;
        this.dob = dob1;

    }

    public String getCustmo() {
        return custmo;
    }

    public String getCustname() {
        return custname;
    }
}
