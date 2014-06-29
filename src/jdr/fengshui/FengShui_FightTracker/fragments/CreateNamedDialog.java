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
import jdr.fengshui.FengShui_FightTracker.models.Named;

/**
 * Created by paulyves on 6/29/14.
 */
public class CreateNamedDialog extends DialogFragment {
    private EditText name;
    private EditText mVA;
    private EditText sVA;
    private EditText speed;
    private EditText toughness;


    public static CreateNamedDialog newInstance(int title){
        CreateNamedDialog dialog = new CreateNamedDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_named_dialog, container, false);

        Button cancel = (Button) v.findViewById(R.id.cancel);
        Button validate = (Button) v.findViewById(R.id.validate);

        name = (EditText) v.findViewById(R.id.name);
        mVA = (EditText) v.findViewById(R.id.mVA);
        sVA = (EditText) v.findViewById(R.id.sVA);
        speed = (EditText) v.findViewById(R.id.speed);
        toughness = (EditText) v.findViewById(R.id.tougness);

        validate.setOnClickListener(validateListener);
        cancel.setOnClickListener(cancelListener);

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
                    int toughnessVal = Integer.parseInt(toughness.getText().toString());
                    Named newChar = new Named(charName,mainVA,secVA,speedVal,toughnessVal);
                    result = 1;
                    Toast.makeText(getActivity(), newChar.toString(), Toast.LENGTH_SHORT).show();
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