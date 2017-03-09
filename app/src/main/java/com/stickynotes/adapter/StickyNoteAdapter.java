package com.stickynotes.adapter;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stickynotes.R;
import com.stickynotes.activity.CommonApplication;
import com.stickynotes.model.StickyNote;

import java.util.List;

/**
 * Created by TrungTV on 03/09/2017.
 */

public class StickyNoteAdapter extends BaseAdapter {
    private CommonApplication application;
    private Context context;
    private static LayoutInflater inflater = null;
    private List<StickyNote> listNote;

    public StickyNoteAdapter(CommonApplication application,Context context, List<StickyNote> listNote) {
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.application = application;
        this.listNote = listNote;
    }

    @Override
    public int getCount() {
        return listNote.size();
    }

    @Override
    public Object getItem(int position) {
        return listNote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_note, null);
            holder.imgPin = (ImageView) view.findViewById(R.id.imgPin);
            holder.tvEditor = (TextView) view.findViewById(R.id.tvEditor);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        StickyNote note = listNote.get(position);
        holder.imgPin.setImageResource(application.listPin[note.getPin()]);
        holder.tvEditor.setText(note.getContent());
        holder.tvEditor.setTextSize(application.textSize[note.getTextSize()]);
        if (note.getTextAlign() == 0) {
            holder.tvEditor.setGravity(Gravity.TOP | Gravity.LEFT);
        }
        if (note.getTextAlign() == 1) {
            holder.tvEditor.setGravity(Gravity.TOP | Gravity.CENTER);
        }
        if (note.getTextAlign() == 2) {
            holder.tvEditor.setGravity(Gravity.TOP | Gravity.RIGHT);
        }
        holder.tvEditor.setTextColor(Color.parseColor(application.color[note.getTextColor()]));
        holder.tvEditor.setBackgroundResource(application.listBackground[note.getBackground()]);

        return view;
    }

    class ViewHolder {
        ImageView imgPin;
        TextView tvEditor;
    }
}
