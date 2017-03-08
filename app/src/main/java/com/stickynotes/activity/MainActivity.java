package com.stickynotes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.stickynotes.R;
import com.stickynotes.adapter.BackgroundAdapter;
import com.stickynotes.model.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rcvBackground;
    private EditText edtEditor;
    private ImageView imgPinNone;
    private ImageView imgPinBlue;
    private ImageView imgPinYellow;
    private ImageView imgPin;

    private BackgroundAdapter backgroundAdapter;
    private int[] listBackground = new int[]{R.drawable.bg_1, R.drawable.bg_2, R.drawable.bg_3, R.drawable.bg_4, R.drawable.bg_5, R.drawable.bg_6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        backgroundAdapter = new BackgroundAdapter(listBackground);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvBackground.setLayoutManager(mLayoutManager);
        rcvBackground.setItemAnimator(new DefaultItemAnimator());
        rcvBackground.setAdapter(backgroundAdapter);

        rcvBackground.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rcvBackground, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                edtEditor.setBackgroundResource(listBackground[position]);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void initUI() {
        rcvBackground = (RecyclerView) findViewById(R.id.rcvBackground);
        edtEditor = (EditText) findViewById(R.id.edtEditor);
        imgPinNone = (ImageView) findViewById(R.id.imgPinNone);
        imgPinBlue = (ImageView) findViewById(R.id.imgPinBlue);
        imgPinYellow = (ImageView) findViewById(R.id.imgPinYellow);
        imgPin = (ImageView) findViewById(R.id.imgPin);

        imgPinNone.setOnClickListener(this);
        imgPinBlue.setOnClickListener(this);
        imgPinYellow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPinNone:
                imgPin.setImageBitmap(null);
                break;
            case R.id.imgPinBlue:
                imgPin.setImageResource(R.drawable.holder_2);
                break;
            case R.id.imgPinYellow:
                imgPin.setImageResource(R.drawable.holder_3);
                break;
            default:
                /*TODO*/
                break;
        }
    }
}
