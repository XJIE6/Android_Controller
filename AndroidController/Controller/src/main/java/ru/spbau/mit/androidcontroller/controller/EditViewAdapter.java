package ru.spbau.mit.androidcontroller.controller;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

public class EditViewAdapter extends BaseAdapter { //Adapter, that creates items with TextView and EditText
    private class ViewHolder {
        TextView textView;
        EditText editText;
        int ref;
    }

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ListItem> items = new ArrayList();

    EditViewAdapter(Context context, ArrayList<ListItem> items) {
        ctx = context;
        this.items.addAll(items);
        notifyDataSetChanged();
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.settings_list_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textView1);
            holder.editText = (EditText) convertView.findViewById(R.id.editText1);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ref = position;
        if (holder.textView == null) {holder.textView = new TextView(ctx); }
        if (holder.editText == null) {holder.editText = new EditText(ctx); }
        holder.textView.setText(items.get(position).textView);
        holder.editText.setText(items.get(position).editText);
        holder.editText.setId(position);
        holder.editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                items.get(holder.ref).editText = arg0.toString();
            }
        });

        return convertView;
    }
}
