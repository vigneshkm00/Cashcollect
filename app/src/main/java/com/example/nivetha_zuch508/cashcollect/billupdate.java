package com.example.nivetha_zuch508.cashcollect;

public class billupdate {
    private String amount,year,status,paid,month,id,timestamp;
    public billupdate(){
        //this constructor is required
    }



    public billupdate(String id, String amount, String year1, String month1, String paid, String month, String ts) {
        this.id = id;
        this.amount = amount;
        this.year = year1;
        this.month = month;
        this.status= month1;
        this.paid=paid;
        this.timestamp = ts;
    }
    public String getId() {
        return id;
    }
    public String getAmount() {
        return amount;
    }

    public String getYear() {
        return year;
    }

    public String getStatus() {
        return status;
    }

    public String getPaid() {
        return paid;
    }

    public String getMonth() {
        return month;
    }
    public String getTimestamp() {
        return timestamp;
    }
}
