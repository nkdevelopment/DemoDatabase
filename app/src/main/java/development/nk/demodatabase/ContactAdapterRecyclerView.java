package development.nk.demodatabase;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by NKdevelopment on 6/10/2017.
 */

public class ContactAdapterRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Contacts> data= Collections.emptyList();
    Contacts current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public ContactAdapterRecyclerView(Context context, List<Contacts> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.row_layout, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Contacts current=data.get(position);
        myHolder.tx_name.setText(current.getName());
        myHolder.tx_password.setText(current.getPassword());
        myHolder.tx_contact.setText(current.getContact());
        myHolder.tx_country.setText(current.getCountry());
        myHolder.tx_country.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView tx_name;
        TextView tx_password;
        TextView tx_contact;
        TextView tx_country;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            tx_name= (TextView) itemView.findViewById(R.id.tx_name);
            tx_password = (TextView) itemView.findViewById(R.id.tx_password);
            tx_contact = (TextView) itemView.findViewById(R.id.tx_contact);
            tx_country = (TextView) itemView.findViewById(R.id.tx_country);
        }

    }
}
