package jdr.fengshui.FengShui_FightTracker.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import jdr.fengshui.FengShui_FightTracker.R;
import jdr.fengshui.FengShui_FightTracker.fragments.CreateMookDialog;
import jdr.fengshui.FengShui_FightTracker.fragments.CreateNamedDialog;
import jdr.fengshui.FengShui_FightTracker.models.*;
import jdr.fengshui.FengShui_FightTracker.models.Character;
import jdr.fengshui.FengShui_FightTracker.utils.CharacterAdapter;

import java.util.ArrayList;

public class Tracker extends Activity {
    private Button reinitSeg;
    private Button addNamed;
    private Button addMook;
    private NumberPicker segments;
    private CharacterAdapter<Character> charAdapter;
    private ListView charListView;

    private final int ADDANAMED = 2;
    private final int ADDAMOOK = 3;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        charListView = (ListView) findViewById(R.id.charList);
        charAdapter = new CharacterAdapter<Character>(this, R.layout.create_named_dialog, new ArrayList<Character>());
        charListView.setAdapter(charAdapter);
        segments = (NumberPicker) findViewById(R.id.gen_segment);
        segments.setValue(0);
        segments.setMinValue(0);
        segments.setMaxValue(100);
        segments.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                charAdapter.reSort();
            }
        });
        reinitSeg = (Button) findViewById(R.id.init_button);
        reinitSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Character character : charAdapter.getCharacterList()){
                    character.rollInit();
                }
            }
        });
        addNamed = (Button) findViewById(R.id.add_named);
        addNamed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(ADDANAMED);
            }
        });
        addMook = (Button) findViewById(R.id.add_mook);
        addMook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(ADDAMOOK);
            }
        });

    }

    public void showDialogType(int type){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dial = null;
        switch (type){
            case ADDANAMED :
                dial = CreateNamedDialog.newInstance(R.string.create_named, charAdapter);
                break;
            case ADDAMOOK :
                dial = CreateMookDialog.newInstance(R.string.create_mook, charAdapter);
                break;
        }
        dial.show(getFragmentManager(),"dialog");
    }

    public NumberPicker getSegments() {
        return segments;
    }
    public int getCurrentSegment(){
        return segments.getValue();
    }
}
