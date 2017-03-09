package com.stickynotes.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.stickynotes.R;
import com.stickynotes.adapter.StickyNoteAdapter;
import com.stickynotes.database.DatabaseHandler;
import com.stickynotes.model.StickyNote;

import java.util.List;

/**
 * Created by TrungTV on 03/09/2017.
 */

public class ListNoteActivity extends Activity{

    private CommonApplication application;
    private StickyNoteAdapter adapter;
    private DatabaseHandler db;
    private List<StickyNote> listNote;

    private GridView grvNote;
    private ImageView imgAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        application = (CommonApplication) getApplication();
        db = new DatabaseHandler(this);

        initUI();

        listNote = db.getAllNote();
        adapter = new StickyNoteAdapter(application,ListNoteActivity.this, listNote);
        grvNote.setAdapter(adapter);

        grvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListNoteActivity.this,DetailNoteActivity.class);
                intent.putExtra("ID",listNote.get(position).getId());
                startActivity(intent);
            }
        });

    }

    private void initUI() {
        grvNote = (GridView) findViewById(R.id.grvNote);
        imgAddNote = (ImageView) findViewById(R.id.imgAddNote);

        imgAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNoteActivity.this,DetailNoteActivity.class);
                startActivity(intent);
            }
        });

    }

}
