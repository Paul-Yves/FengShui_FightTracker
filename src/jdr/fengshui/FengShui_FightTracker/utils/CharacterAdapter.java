package jdr.fengshui.FengShui_FightTracker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import jdr.fengshui.FengShui_FightTracker.R;
import jdr.fengshui.FengShui_FightTracker.models.*;
import jdr.fengshui.FengShui_FightTracker.models.Character;

import java.util.List;

/**
 * Created by paulyves on 6/29/14.
 */
public class CharacterAdapter<T> extends ArrayAdapter<T> {
    private final Context context;
    private final List<T> characterList;

    private final int NAMED = 0;
    private final int MOOK = 1;

    public CharacterAdapter(Context context,int layoutResourceId, List<T> characterList) {
        super(context, layoutResourceId, characterList);
        this.context = context;
        this.characterList = characterList;
    }

    public void addItem(T item){
        characterList.add(item);
        reSort();
    }
    public void delItem(T item){
        characterList.remove(item);
        reSort();
    }
    /**
     * Sort the list and notify data change
     */
    public void reSort(){
        java.util.Collections.sort((List<Character>)characterList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Character character = (Character) this.characterList.get(position);
        if (character instanceof Named){
            return NAMED;
        } else {
            return MOOK;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder holder = null;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new ViewHolder();
            if (type==NAMED){
                convertView = inflater.inflate(R.layout.named, parent, false);
                holder.wounds = (NumberPicker) convertView.findViewById(R.id.wounds);
                holder.toughness = (TextView) convertView.findViewById(R.id.tougness);
                holder.eatThat = (Button) convertView.findViewById(R.id.eatThat_button);
                holder.eatThatVal = (EditText) convertView.findViewById(R.id.eatThat_value);
            }else{
                convertView = inflater.inflate(R.layout.mook, parent, false);
                holder.number = (NumberPicker) convertView.findViewById(R.id.number);
            }
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.segments = (NumberPicker) convertView.findViewById(R.id.segment);
            holder.mainVA = (TextView) convertView.findViewById(R.id.mVA);
            holder.secVA = (TextView) convertView.findViewById(R.id.sVA);
            holder.speed = (TextView) convertView.findViewById(R.id.speed);
            holder.rollMain = (Button) convertView.findViewById(R.id.rollmain_button);
            holder.rollMainRes = (TextView) convertView.findViewById(R.id.rollmain_res);
            holder.rollSec = (Button) convertView.findViewById(R.id.rollsec_button);
            holder.rollSecRes = (TextView) convertView.findViewById(R.id.rollsec_res);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Character character = (Character) this.characterList.get(position);
        holder.name.setText(character.getName());

        return convertView;
    }

    public List<Character> getCharacterList() {
        return (List<Character>) characterList;
    }

    public static class ViewHolder{
        public NumberPicker segments;
        public NumberPicker wounds;
        public NumberPicker number;
        public TextView name;
        public TextView mainVA;
        public TextView secVA;
        public TextView speed;
        public TextView toughness;
        public Button rollMain;
        public TextView rollMainRes;
        public Button rollSec;
        public TextView rollSecRes;
        public Button eatThat;
        public EditText eatThatVal;
        public Button delete;
    }
}
