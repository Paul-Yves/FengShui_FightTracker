package jdr.fengshui.FengShui_FightTracker.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import jdr.fengshui.FengShui_FightTracker.R;
import jdr.fengshui.FengShui_FightTracker.models.*;
import jdr.fengshui.FengShui_FightTracker.models.Character;
import jdr.fengshui.FengShui_FightTracker.utils.CharacterAdapter;

/**
 * Created by paulyves on 7/1/14.
 */
public class CreateMookDialog extends DialogFragment {
    private EditText name;
    private EditText mVA;
    private EditText sVA;
    private EditText speed;
    private EditText number;
    private CharacterAdapter<Character> charAdapter;

    public static CreateMookDialog newInstance(int title, CharacterAdapter<Character> charAdapter){
        CreateMookDialog dialog = new CreateMookDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        dialog.charAdapter = charAdapter;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_mook_dialog, container, false);

        Button cancel = (Button) v.findViewById(R.id.cancel);
        Button validate = (Button) v.findViewById(R.id.validate);

        name = (EditText) v.findViewById(R.id.name);
        mVA = (EditText) v.findViewById(R.id.mVA);
        sVA = (EditText) v.findViewById(R.id.sVA);
        speed = (EditText) v.findViewById(R.id.speed);
        number = (EditText) v.findViewById(R.id.number);

        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);
        getDialog().setTitle(getArguments().getInt("title"));
        return v;
    }
    private View.OnClickListener validateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String charName = name.getText().toString();
            long result = -1;
            if(!charName.isEmpty()){
                try{
                    int mainVA = Integer.parseInt(mVA.getText().toString());
                    int secVA = Integer.parseInt(sVA.getText().toString());
                    int speedVal = Integer.parseInt(speed.getText().toString());
                    int numberVal = Integer.parseInt(number.getText().toString());
                    Mook newChar = new Mook(charName,mainVA,secVA,speedVal,numberVal);
                    result = 1;
                    charAdapter.addItem(newChar);
                    charAdapter.reSort();
                } catch(NumberFormatException e) {
                    result = -1;
                }
            }
            if(result == -1){
                Toast.makeText(getActivity(), getText(R.string.errorCreate), Toast.LENGTH_SHORT).show();
            }
            dismiss();
        }
    };

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}