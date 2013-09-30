package com.arkami.myidkey.activity.web;

import android.content.Context;
import android.widget.ArrayAdapter;
import org.json.JSONObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-9-26
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public class KeyCardsListAdapter extends ArrayAdapter<JSONObject> {

    public KeyCardsListAdapter(Context context, int textViewResourceId, List<JSONObject> objects) {
        super(context, textViewResourceId, objects);
    }
}
