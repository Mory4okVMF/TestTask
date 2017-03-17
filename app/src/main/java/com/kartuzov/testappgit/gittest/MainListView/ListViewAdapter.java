package com.kartuzov.testappgit.gittest.MainListView;

/**
 * Created by Mory4ok on 16.03.17.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kartuzov.testappgit.gittest.R;

public class ListViewAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater = null;

    public ListViewAdapter(Context context,
                           List<? extends Map<String, ?>> data, int resource, String[] from,
                           int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_person, null);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.ivImg);
            holder.name = (TextView) convertView.findViewById(R.id.tvText);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }



        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);

        (holder.name).setText((String) data.get("nick"));
        if (holder.avatar != null) {
            new DownloadTask(holder.avatar)
                    .execute((String) data.get("avatar"));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        ImageView avatar;
    }

}