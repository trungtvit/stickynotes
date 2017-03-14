package com.stickynotes.activity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stickynotes.R;
import com.stickynotes.adapter.BackgroundAdapter;
import com.stickynotes.adapter.PinAdapter;
import com.stickynotes.application.CommonApplication;
import com.stickynotes.database.DatabaseHandler;
import com.stickynotes.model.RecyclerTouchListener;
import com.stickynotes.model.StickyNote;
import com.stickynotes.widget.NoteWidget;

/**
 * Created by TrungTV on 03/09/2017.
 */

public class DetailNoteActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rcvBackground;
    private RecyclerView rcvPin;
    private EditText edtEditor;
    private ImageView imgPin;
    private ImageView imgEditor;
    private ImageView imgAlign;
    private ImageView imgTextSize;
    private ImageView imgTextColor;
    private ImageView imgRotate;
    private ImageView imgSave;
    private LinearLayout lnEditor;
    private LinearLayout lnRotate;

    private BackgroundAdapter backgroundAdapter;
    private PinAdapter pinAdapter;
    private DatabaseHandler db;
    private CommonApplication application;


    private int id = -1;
    private int appWidgetId;
    private int textSizePosition = 0;
    private int alignPosition = 0;
    private int colorPosition = 0;
    private int degreesPosition = 0;
    private int pinPosition = 0;
    private int backgroundPosition = 0;
    private String contentNote = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.note_title));

        application = (CommonApplication) getApplication();
        db = new DatabaseHandler(this);
        backgroundAdapter = new BackgroundAdapter(application.listBackground);
        pinAdapter = new PinAdapter(application.listPin);

        id = getIntent().getIntExtra("ID", -1);
        appWidgetId = getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

        initUI();

        if (id != -1) {
            showNoteDetail(id);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvBackground.setLayoutManager(mLayoutManager);
        rcvBackground.setItemAnimator(new DefaultItemAnimator());
        rcvBackground.setAdapter(backgroundAdapter);
        rcvBackground.scrollToPosition(backgroundPosition);

        rcvBackground.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvBackground, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                backgroundPosition = position;
                edtEditor.setBackgroundResource(application.listBackground[position]);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        RecyclerView.LayoutManager mLayoutManagerPin = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvPin.setLayoutManager(mLayoutManagerPin);
        rcvPin.setItemAnimator(new DefaultItemAnimator());
        rcvPin.setAdapter(pinAdapter);
        rcvPin.scrollToPosition(pinPosition);

        rcvPin.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvPin, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                pinPosition = position;
                imgPin.setImageResource(application.listPin[position]);
                if (pinPosition == 0) {
                    imgPin.setImageResource(0);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initUI() {
        rcvBackground = (RecyclerView) findViewById(R.id.rcvBackground);
        rcvPin = (RecyclerView) findViewById(R.id.rcvPin);
        edtEditor = (EditText) findViewById(R.id.edtEditor);
        imgPin = (ImageView) findViewById(R.id.imgPin);
        imgEditor = (ImageView) findViewById(R.id.imgEditor);
        imgAlign = (ImageView) findViewById(R.id.imgAlign);
        imgTextSize = (ImageView) findViewById(R.id.imgTextSize);
        imgTextColor = (ImageView) findViewById(R.id.imgTextColor);
        imgRotate = (ImageView) findViewById(R.id.imgRotate);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        lnEditor = (LinearLayout) findViewById(R.id.lnEditor);
        lnRotate = (LinearLayout) findViewById(R.id.lnRotate);

        imgTextSize.setImageResource(application.listTextSize[textSizePosition]);
        imgAlign.setImageResource(application.listAlign[alignPosition]);
        imgTextColor.setImageResource(application.listColor[colorPosition]);
        lnRotate.setRotation(application.rotateDegrees[degreesPosition]);

        imgEditor.setOnClickListener(this);
        imgAlign.setOnClickListener(this);
        imgTextSize.setOnClickListener(this);
        imgTextColor.setOnClickListener(this);
        imgRotate.setOnClickListener(this);
        imgSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgEditor:
                if (lnEditor.isShown()) {
                    Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_out_left);
                    lnEditor.startAnimation(hyperspaceJumpAnimation);
                    lnEditor.setVisibility(View.GONE);
                    lnEditor.setClickable(false);
                } else {
                    Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_in_left);
                    lnEditor.startAnimation(hyperspaceJumpAnimation);
                    lnEditor.setVisibility(View.VISIBLE);
                    lnEditor.setClickable(true);
                }
                break;
            case R.id.imgTextSize:
                textSizePosition += 1;
                if (textSizePosition == 5) {
                    textSizePosition = 0;
                }
                imgTextSize.setImageResource(application.listTextSize[textSizePosition]);
                edtEditor.setTextSize(application.textSize[textSizePosition]);
                break;
            case R.id.imgAlign:
                alignPosition += 1;
                if (alignPosition == 3) {
                    alignPosition = 0;
                }
                imgAlign.setImageResource(application.listAlign[alignPosition]);
                if (alignPosition == 0) {
                    edtEditor.setGravity(Gravity.TOP | Gravity.LEFT);
                }
                if (alignPosition == 1) {
                    edtEditor.setGravity(Gravity.TOP | Gravity.CENTER);
                }
                if (alignPosition == 2) {
                    edtEditor.setGravity(Gravity.TOP | Gravity.RIGHT);
                }

                break;
            case R.id.imgTextColor:
                colorPosition += 1;
                if (colorPosition == 6) {
                    colorPosition = 0;
                }
                imgTextColor.setImageResource(application.listColor[colorPosition]);
                edtEditor.setTextColor(Color.parseColor(application.color[colorPosition]));
                break;
            case R.id.imgRotate:
                degreesPosition += 1;
                if (degreesPosition == 3) {
                    degreesPosition = 0;
                }
                lnRotate.setRotation(application.rotateDegrees[degreesPosition]);
                imgRotate.setImageResource(application.rotate[degreesPosition]);
                break;
            case R.id.imgSave:
                if (id == -1) {
                    addNote();
                    Toast.makeText(this, getResources().getString(R.string.msg_add_complete), Toast.LENGTH_SHORT).show();
                } else {
                    updateNote();
                    Toast.makeText(this, getResources().getString(R.string.msg_update_complete), Toast.LENGTH_SHORT).show();
                    updateAllWidgets();
                }

                finish();
                break;
            default:
                /*TODO*/
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateAllWidgets() {
        Intent intent = new Intent(this, NoteWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = {appWidgetId};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    /*Show detail note*/
    private void showNoteDetail(int id) {
        StickyNote note = db.getNoteByNoteId(id);
        pinPosition = note.getPin();
        textSizePosition = note.getTextSize();
        alignPosition = note.getTextAlign();
        colorPosition = note.getTextColor();
        backgroundPosition = note.getBackground();
        degreesPosition = note.getRotate();
        contentNote = note.getContent();

        edtEditor.setText(contentNote);

        imgPin.setImageResource(application.listPin[pinPosition]);
        if (pinPosition == 0) {
            imgPin.setImageResource(0);
        }

        edtEditor.setTextSize(application.textSize[textSizePosition]);
        imgTextSize.setImageResource(application.listTextSize[textSizePosition]);

        imgAlign.setImageResource(application.listAlign[alignPosition]);
        if (alignPosition == 0) {
            edtEditor.setGravity(Gravity.TOP | Gravity.LEFT);
        }
        if (alignPosition == 1) {
            edtEditor.setGravity(Gravity.TOP | Gravity.CENTER);
        }
        if (alignPosition == 2) {
            edtEditor.setGravity(Gravity.TOP | Gravity.RIGHT);
        }

        imgTextColor.setImageResource(application.listColor[colorPosition]);
        edtEditor.setTextColor(Color.parseColor(application.color[colorPosition]));

        lnRotate.setRotation(application.rotateDegrees[degreesPosition]);
        imgRotate.setImageResource(application.rotate[degreesPosition]);

        edtEditor.setBackgroundResource(application.listBackground[backgroundPosition]);
        backgroundAdapter.setSelectedItem(backgroundPosition);

    }

    /*Add note*/
    private void addNote() {
        StickyNote note = new StickyNote();
        note.setContent(edtEditor.getText().toString());
        note.setTextSize(textSizePosition);
        note.setTextAlign(alignPosition);
        note.setTextColor(colorPosition);
        note.setRotate(degreesPosition);
        note.setBackground(backgroundPosition);
        note.setPin(pinPosition);
        db.addNote(note);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
    }

    /*Update note*/
    private void updateNote() {
        StickyNote note = new StickyNote();
        note.setWidgetId(appWidgetId);
        note.setContent(edtEditor.getText().toString());
        note.setTextSize(textSizePosition);
        note.setTextAlign(alignPosition);
        note.setTextColor(colorPosition);
        note.setRotate(degreesPosition);
        note.setBackground(backgroundPosition);
        note.setPin(pinPosition);
        db.updateNote(note, id);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
    }
}
