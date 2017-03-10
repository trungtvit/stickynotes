package com.stickynotes.activity;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.stickynotes.R;
import com.stickynotes.adapter.StickyNoteAdapter;
import com.stickynotes.application.CommonApplication;
import com.stickynotes.database.DatabaseHandler;
import com.stickynotes.model.StickyNote;
import com.stickynotes.widget.NoteWidget;

import java.util.List;

/**
 * Created by TrungTV on 03/10/2017.
 */

public class ListNoteWidgetActivity extends AppCompatActivity {
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private static final String PREFS_NAME = "AppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget";

    private CommonApplication application;
    private StickyNoteAdapter adapter;
    private DatabaseHandler db;
    private List<StickyNote> listNote;

    public static int _id;
    public static String content = "";
    public static float textSize ;
    public static String textColor="#000000";
    public static int pin=0;
    public static int background=0;

    private GridView grvNote;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_list_note);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        application = (CommonApplication) getApplication();
        db = new DatabaseHandler(this);

        grvNote = (GridView) findViewById(R.id.grvNote);

        listNote = db.getAllNote();
        adapter = new StickyNoteAdapter(application, ListNoteWidgetActivity.this, listNote);
        grvNote.setAdapter(adapter);

        grvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _id = listNote.get(position).getId();
                content = listNote.get(position).getContent();
                textSize = application.textSize[listNote.get(position).getTextSize()];
                textColor = application.color[listNote.get(position).getTextColor()];
                pin = application.listPin[listNote.get(position).getPin()];
                background = application.listBackground[listNote.get(position).getBackground()];
                createWidget(getApplicationContext());
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

    }

    private void createWidget(Context context) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        NoteWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }


}
