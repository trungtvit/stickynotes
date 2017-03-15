package com.stickynotes.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.stickynotes.R;
import com.stickynotes.adapter.StickyNoteAdapter;
import com.stickynotes.application.CommonApplication;
import com.stickynotes.database.DatabaseHandler;
import com.stickynotes.model.StickyNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrungTV on 03/09/2017.
 */

public class ListNoteActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_UPDATE = 101;
    private static final int REQUEST_CODE_ADD = 102;

    private CommonApplication application;
    private StickyNoteAdapter adapter;
    private DatabaseHandler db;
    private List<StickyNote> listNote;

    private GridView grvNote;
    private ImageView imgAddNote;

    private boolean isUpdate = false;
    private boolean isAdd = false;
    private List<String> listIds = new ArrayList<>();

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
        grvNote.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);

        grvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListNoteActivity.this, DetailNoteActivity.class);
                intent.putExtra("ID", listNote.get(position).getId());
                startActivityForResult(intent, REQUEST_CODE_UPDATE);
            }
        });

        grvNote.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle(grvNote.getCheckedItemCount() + " Selected");
                if (checked) {
                    listIds.add(String.valueOf(listNote.get(position).getId()));
                } else {
                    for (int i = 0; i < listIds.size(); i++) {
                        if (listIds.get(i).toString().equals(String.valueOf(listNote.get(position).getId()))) {
                            listIds.remove(i);
                        }
                    }

                }
                adapter.toggleSelection(position);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionDelete:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ListNoteActivity.this);
                        dialog.setTitle(getResources().getString(R.string.title_dialog_delete));
                        dialog.setMessage(getResources().getString(R.string.msg_dialog_delete));
                        dialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SparseBooleanArray selected = adapter
                                        .getSelectedIds();
                                for (int i = (selected.size() - 1); i >= 0; i--) {
                                    if (selected.valueAt(i)) {
                                        StickyNote note = (StickyNote) adapter
                                                .getItem(selected.keyAt(i));
                                        adapter.remove(note);
                                    }
                                }
                                String[] ids = new String[listIds.size()];
                                ids = listIds.toArray(ids);
                                db.deleteNote(ids);
                                mode.finish();
                            }
                        });
                        dialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.removeSelection();
                for (int i = 0; i < listIds.size(); i++) {
                    listIds.remove(i);
                }
            }
        });

    }

    private void initUI() {
        grvNote = (GridView) findViewById(R.id.grvNote);
        imgAddNote = (ImageView) findViewById(R.id.imgAddNote);

        imgAddNote.setVisibility(View.VISIBLE);

        imgAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNoteActivity.this, DetailNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
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

        if (requestCode == REQUEST_CODE_ADD) {
            if (resultCode == RESULT_OK) {
                isAdd = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isUpdate || isAdd) {
            listNote = db.getAllNote();
            adapter.setData(listNote);
            isUpdate = false;
            isAdd = false;
        }
    }
}
