package development.nk.demodatabase;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by NKdevelopment on 11/7/2017.
 */

public class ContactAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ContactAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add(@Nullable Contacts object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row=convertView;
        ContactHolder contactHolder;

        if(row==null) {
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_layout,parent,false);
            contactHolder=new ContactHolder();
            contactHolder.tx_name=(TextView)row.findViewById(R.id.tx_name);
            contactHolder.tx_password=(TextView)row.findViewById(R.id.tx_password);
            contactHolder.tx_contact=(TextView)row.findViewById(R.id.tx_contact);
            contactHolder.tx_country=(TextView)row.findViewById(R.id.tx_country);

            row.setTag(contactHolder);
        } else {
            contactHolder = (ContactHolder) row.getTag();
        }

        Contacts contacts=(Contacts)this.getItem(position);
        contactHolder.tx_name.setText(contacts.getName());
        contactHolder.tx_password.setText(contacts.getPassword());
        contactHolder.tx_contact.setText(contacts.getContact());
        contactHolder.tx_country.setText(contacts.getCountry());


        return row;
    }

    static class ContactHolder {

        TextView tx_name,tx_password,tx_contact,tx_country;
    }

}
