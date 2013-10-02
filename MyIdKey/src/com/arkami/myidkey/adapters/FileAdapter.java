package com.arkami.myidkey.adapters;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkami.myidkey.R;
import com.arkami.myidkey.activity.FoldersActivity;
import com.arkami.myidkey.database.tables.FileObject;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-8-20
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class FileAdapter extends ArrayAdapter<FileObject> {

    private Context context;
    private int resourceId;
    private boolean isInMoveMode;

    public FileAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
        resourceId = textViewResourceId;
    }

    /**
     * Should be set to true when have to move files.
     *
     * @param inMoveMode used when have to move files from one folder to another
     */
    public void setInMoveMode(boolean inMoveMode) {
        this.isInMoveMode = inMoveMode;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
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
            if (isInMoveMode) {
                viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                viewHolder.checkBox.setVisibility(View.VISIBLE);
            }
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
//                if(FileAdapter.this.getItem(position).getFileType()==1) {
//                    Intent viewImageIntent = new Intent(
//                            FileAdapter.this.getContext(), ViewImageIActivity.class);
//                    viewImageIntent.putExtra(ViewImageIActivity.IMAGE_KEY,
//                            FileAdapter.this.getItem(position));
//                    FileAdapter.this.getContext().startActivity(viewImageIntent);
//                    return;
//                }
//                if(FileAdapter.this.getItem(position).getFileType()==2){
//                    Intent intent = new Intent();
//                    intent.setAction(android.content.Intent.ACTION_VIEW);
//                    File file = new File(FileAdapter.this.getItem(position).getPathToFile());
//                    intent.setDataAndType(Uri.fromFile(file), "audio/3gp");
//                    Log.w("Uri.fromFile(file) = ", FileAdapter.this.getItem(position).getPathToFile());
//                    FileAdapter.this.getContext().startActivity(intent);
//                    return;
//                }
                if (FileAdapter.this.getItem(position).getFileType() != 3) {
                    Intent viewFileIntent = new Intent();
                    viewFileIntent.setAction(android.content.Intent.ACTION_VIEW);
                    File file = new File(FileAdapter.this.getItem(position).getPathToFile());
                    viewFileIntent.setDataAndType(Uri.fromFile(file),
                    getMimeType(file.getName()));
                    Log.w("Uri.fromFile(file) = ", file.getPath());
                    FileAdapter.this.getContext().startActivity(viewFileIntent);
                    return;

                } else {
                    if (isInMoveMode) {
                        Intent foldersActivity = new Intent(context, FoldersActivity.class);
                        foldersActivity.putExtra("isInMoveMode", true);
                        context.startActivity(foldersActivity);
                    }
                }

            }
        });
//        if (this.getItem(position).getFileType() == 1) {
//            holder.fileImage.setImageResource(R.drawable.folder);
//        } else {
//            holder.fileImage.setVisibility(View.INVISIBLE);
//        }
        holder.fileImage.setImageResource(getImageResourceId(this.getItem(position).getFileType()));
        return view;
    }

    private static String getMimeType(String url) {
        String parts[] = url.split("\\.");
        String extension = parts[parts.length - 1];
        String type = null;
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private int getImageResourceId(long fileTipe) {
        switch ((int) fileTipe) {
            case 1:
                return R.drawable.photo;
            case 2:
                return R.drawable.speaker;
            case 3:
                return R.drawable.folder_selected;
            case 4:
                return R.drawable.form_fill;
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
        CheckBox checkBox;
    }




}
