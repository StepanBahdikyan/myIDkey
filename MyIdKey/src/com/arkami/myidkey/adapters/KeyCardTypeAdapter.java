package com.arkami.myidkey.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkami.myidkey.R;
import com.arkami.myidkey.database.tables.KeyCardType;
import com.arkami.myidkey.util.Common;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-8-22
 * Time: 18:04
 * To change this template use File | Settings | File Templates.
 */
public class KeyCardTypeAdapter extends ArrayAdapter<KeyCardType> {
    private Context context;

    public KeyCardTypeAdapter(Context context, int textViewResourceId, List<KeyCardType> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(R.layout.list_view_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.fileImage = (ImageView) view
                    .findViewById(R.id.icon_image);
            viewHolder.fileName = (TextView) view
                    .findViewById(R.id.textView);
            viewHolder.fileName.setTag(this.getItem(position));
            view.setTag(viewHolder);
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).fileName.setTag(this.getItem(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.fileName.setText(this.getItem(position).getName());
        holder.fileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.fileImage.setImageResource(Common.getImageResourseId(getItem(position)
                .getId()));
        return view;
    }

     private int getImageResourceId(long fileTipe) {
        switch ((int) fileTipe) {
            default:
                return R.drawable.form_fill;
        }

    }

    /**
     * @author sbahdikyan
     */
    private static class ViewHolder {
        ImageView fileImage;
        TextView fileName;
    }
}
