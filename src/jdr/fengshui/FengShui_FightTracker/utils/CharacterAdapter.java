package jdr.fengshui.FengShui_FightTracker.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import jdr.fengshui.FengShui_FightTracker.R;
import jdr.fengshui.FengShui_FightTracker.activity.Tracker;
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
            holder.delete = (Button) convertView.findViewById(R.id.delButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Character character = (Character) this.characterList.get(position);
        holder.name.setText(character.getName());
        holder.segments.setValue(character.getSegment());
        holder.segments.setMinValue(0);
        holder.segments.setMaxValue(100);
        final View finalConvertView = convertView;
        holder.segments.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                character.setSegment(newVal);
                colorChar(character, finalConvertView);
                reSort();
            }
        });
        holder.mainVA.setText(String.valueOf(character.getMainVA()));
        final ViewHolder finalHolder = holder;
        holder.rollMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.rollMainRes.setText(String.valueOf(character.rollSkill(1)));
                finalHolder.segments.setValue(character.getSegment());
                reSort();
            }
        });
        holder.secVA.setText(String.valueOf(character.getSecondaryVA()));
        holder.rollSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.rollSecRes.setText(String.valueOf(character.rollSkill(2)));
                finalHolder.segments.setValue(character.getSegment());
                reSort();
            }
        });
        holder.speed.setText(String.valueOf(character.getSpeed()));
        if (type == NAMED){
            final Named namedChar = (Named) character;
            holder.toughness.setText(String.valueOf(namedChar.getToughness()));
            holder.wounds.setMinValue(0);
            holder.wounds.setMaxValue(100);
            holder.wounds.setValue(namedChar.getWounds());
            holder.wounds.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    namedChar.setWounds(newVal);
                }
            });
            holder.eatThat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int dmg = Integer.parseInt(finalHolder.eatThatVal.getText().toString());
                    namedChar.eatThat(dmg);
                    finalHolder.wounds.setValue(namedChar.getWounds());
                    finalHolder.eatThatVal.setText("0");
                }
            });
        }else{
            holder.number.setValue(((Mook)character).getNumber());
            holder.number.setMinValue(0);
            holder.number.setMaxValue(((Mook)character).getNumber()+50);
            holder.number.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    ((Mook)character).setNumber(newVal);
                }
            });
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.delChar_dialog_title);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delItem((T)character);
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        colorChar(character, convertView);
        return convertView;
    }

    public List<Character> getCharacterList() {
        return (List<Character>) characterList;
    }

    private void colorChar(Character character, View convertView){
        if(character.getSegment() > ((Tracker)context).getCurrentSegment()){
            convertView.setBackgroundColor(0xFFB40404);
        }else if(character.getSegment()==((Tracker)context).getCurrentSegment()){
            convertView.setBackgroundColor(0xFF088A08);
        }else{
            convertView.setBackgroundColor(0x00000000);
        }
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
