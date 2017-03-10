package com.stickynotes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.stickynotes.R;
import com.stickynotes.adapter.StickyNoteAdapter;
import com.stickynotes.application.CommonApplication;
import com.stickynotes.database.DatabaseHandler;
import com.stickynotes.model.StickyNote;

import java.util.List;

/**
 * Created by TrungTV on 03/09/2017.
 */

public class ListNoteActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_UPDATE = 101;

    private CommonApplication application;
    private StickyNoteAdapter adapter;
    private DatabaseHandler db;
    private List<StickyNote> listNote;

    private GridView grvNote;
    private ImageView imgAddNote;

    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        getSupportActionBar().setTitle(getResources().getString(R.string.list_note_title));

        application = (CommonApplication) getApplication();
        db = new DatabaseHandler(this);

        initUI();

        listNote = db.getAllNote();
        adapter = new StickyNoteAdapter(application, ListNoteActivity.this, listNote);
        grvNote.setAdapter(adapter);

        grvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListNoteActivity.this, DetailNoteActivity.class);
                intent.putExtra("ID", listNote.get(position).getId());
                startActivityForResult(intent, REQUEST_CODE_UPDATE);
            }
        });

    }

    private void initUI() {
        grvNote = (GridView) findViewById(R.id.grvNote);
        imgAddNote = (ImageView) findViewById(R.id.imgAddNote);

        imgAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNoteActivity.this, DetailNoteActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE) {
            if (resultCode == RESULT_OK) {
                isUpdate = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUpdate) {
            listNote = db.getAllNote();
            adapter.setData(listNote);
            isUpdate = false;
        }
    }
}
