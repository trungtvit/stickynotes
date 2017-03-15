package com.stickynotes.activity;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

    public static CommonApplication application;
    private StickyNoteAdapter adapter;
    private DatabaseHandler db;
    private List<StickyNote> listNote;

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private GridView grvNote;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_list_note);
        getSupportActionBar().setTitle(getResources().getString(R.string.list_note_title));

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

        application = (CommonApplication) getApplication();
        db = new DatabaseHandler(this);

        grvNote = (GridView) findViewById(R.id.grvNote);

        listNote = db.getAllNote();
        adapter = new StickyNoteAdapter(application, ListNoteWidgetActivity.this, listNote);
        grvNote.setAdapter(adapter);

        grvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.updateWidgetId(mAppWidgetId, listNote.get(position).getId());
                createWidget(getApplicationContext());
            }
        });

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
