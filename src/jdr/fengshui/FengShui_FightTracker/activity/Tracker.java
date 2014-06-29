package jdr.fengshui.FengShui_FightTracker.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import jdr.fengshui.FengShui_FightTracker.R;
import jdr.fengshui.FengShui_FightTracker.fragments.CreateNamedDialog;
import jdr.fengshui.FengShui_FightTracker.models.*;
import jdr.fengshui.FengShui_FightTracker.models.Character;
import jdr.fengshui.FengShui_FightTracker.utils.CharacterAdapter;

import java.util.ArrayList;

public class Tracker extends Activity {
    private ArrayList<Character> charList;
    private CharacterAdapter<Character> charAdapter;
    private Button reinitSeg;
    private Button addNamed;
    private Button addMook;

    private final int ADDANAMED = 2;
    private final int ADDAMOOK = 3;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        reinitSeg = (Button) findViewById(R.id.init_button);
        addNamed = (Button) findViewById(R.id.add_named);
        addNamed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogType(ADDANAMED);
            }
        });
        addMook = (Button) findViewById(R.id.add_mook);

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
                dial = CreateNamedDialog.newInstance(R.string.create_newNamed);
                break;
            case ADDAMOOK :
                break;
        }
        dial.show(getFragmentManager(),"dialog");
    }
}
