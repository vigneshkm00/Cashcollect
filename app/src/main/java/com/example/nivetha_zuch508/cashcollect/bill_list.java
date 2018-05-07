package com.example.nivetha_zuch508.cashcollect;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class bill_list extends ArrayAdapter<billupdate> {
    private Activity context;
    List<billupdate> bills;
    public bill_list(Activity context, List<billupdate> bills){
        super(context,R.layout.list_layout,bills);
        this.context = context;
        this.bills = bills;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView status = (TextView) listViewItem.findViewById(R.id.textView12);
        TextView year = (TextView) listViewItem.findViewById(R.id.textView2);
        TextView amount = (TextView) listViewItem.findViewById(R.id.textView8);
        TextView paid = (TextView) listViewItem.findViewById(R.id.textView11);
        TextView month = (TextView) listViewItem.findViewById(R.id.textView);
        Button btn = (Button) listViewItem.findViewById(R.id.button6);
        final billupdate bill_up = bills.get(position);
        amount.setText(bill_up.getAmount());
        year.setText(bill_up.getYear());
        status.setText(bill_up.getStatus());
        paid.setText(bill_up.getPaid());
        month.setText(bill_up.getMonth());
        MainActivity man = new MainActivity();
        final String k = man.key;
        if(bill_up.getStatus().equals("Paid"))
        {
            btn.setVisibility(View.INVISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),Amount.class);
                i.putExtra("amount",bill_up.getAmount().toString());
                i.putExtra("month",bill_up.getMonth().toString());
                i.putExtra("year",bill_up.getYear().toString());
                i.putExtra("paid",bill_up.getPaid().toString());
                i.putExtra("status",bill_up.getStatus().toString());
                i.putExtra("id",bill_up.getId().toString());
                context.startActivityForResult(i,1);
               // Toast.makeText(getContext(),"data"+bill_up.getAmount(),Toast.LENGTH_LONG).show();
            }
        });
        return listViewItem;

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==1) {
            return;
        }}

}
