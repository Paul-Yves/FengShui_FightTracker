package jdr.fengshui.FengShui_FightTracker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import jdr.fengshui.FengShui_FightTracker.R;
import jdr.fengshui.FengShui_FightTracker.models.*;
import jdr.fengshui.FengShui_FightTracker.models.Character;

import java.util.List;

/**
 * Created by paulyves on 6/29/14.
 */
public class CharacterAdapter<T> extends ArrayAdapter<T> {
    private final Context context;
    private final List<T> values;

    public CharacterAdapter(Context context,int layoutResourceId, List<T> values) {
        super(context, layoutResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getItemViewType(int position) {
        Character character = (Character) values.get(position);
        if (character instanceof Named){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (getItemViewType(position)==0){
                convertView = inflater.inflate(R.layout.named, parent, false);
            }else{
                convertView = inflater.inflate(R.layout.mook, parent, false);
            }
        }
        return convertView;
    }
}
