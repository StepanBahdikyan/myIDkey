package com.arkami.myidkey.adapters;

import java.io.File;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkami.myidkey.R;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-7-8
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
public class FileChooserAdapter extends ArrayAdapter<File> {
//    private File[] files;
    private Context context;
    private int resourceId;

    public FileChooserAdapter(Context context, int textViewResourceId, File[] objects) {
        super(context, textViewResourceId, objects);
//        this.files = objects;
        this.context = context;
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(resourceId, null);
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
        if (this.getItem(position).isDirectory()) {
            holder.fileImage.setVisibility(View.VISIBLE);
            holder.fileImage.setImageResource(R.drawable.folder_selected);
        }else{
            holder.fileImage.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    /**
     * @author sbahdikyan
     */
    private static class ViewHolder {
        ImageView fileImage;
        TextView fileName;
    }
}
