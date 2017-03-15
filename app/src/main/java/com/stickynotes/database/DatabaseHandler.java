package com.stickynotes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.stickynotes.model.StickyNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrungTV on 03/09/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "db_sticky";

    private static final String TABLE_STICKY_NOTE = "sticky_note";

    private static final String KEY_ID = "_id";
    private static final String KEY_WIDGET_ID = "widget_id";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_TEXT_SIZE = "text_size";
    private static final String KEY_TEXT_ALIGN = "text_align";
    private static final String KEY_TEXT_COLOR = "text_color";
    private static final String KEY_ROTATE = "rotate";
    private static final String KEY_BACKGROUND = "background";
    private static final String KEY_PIN = "pin";

    private static final String CREATE_TABLE_STICKY_NOTE = "CREATE TABLE " + TABLE_STICKY_NOTE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_WIDGET_ID + " INTEGER, "
            + KEY_CONTENT + " TEXT ,"
            + KEY_TEXT_SIZE + " INTEGER,"
            + KEY_TEXT_ALIGN + " INTEGER,"
            + KEY_TEXT_COLOR + " INTEGER,"
            + KEY_ROTATE + " INTEGER,"
            + KEY_BACKGROUND + " INTEGER,"
            + KEY_PIN + " INTEGER"
            + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STICKY_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STICKY_NOTE);
        onCreate(db);
    }

    /*Close DB*/
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /*Insert Note*/
    public void addNote(StickyNote note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WIDGET_ID, note.getWidgetId());
        values.put(KEY_CONTENT, note.getContent());
        values.put(KEY_TEXT_SIZE, note.getTextSize());
        values.put(KEY_TEXT_ALIGN, note.getTextAlign());
        values.put(KEY_TEXT_COLOR, note.getTextColor());
        values.put(KEY_ROTATE, note.getRotate());
        values.put(KEY_BACKGROUND, note.getBackground());
        values.put(KEY_PIN, note.getPin());

        db.insert(TABLE_STICKY_NOTE, null, values);

        db.close();
    }

    /*Get all note*/
    public List<StickyNote> getAllNote() {
        List<StickyNote> listNote = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_STICKY_NOTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                StickyNote note = new StickyNote();
                note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                note.setWidgetId(c.getInt((c.getColumnIndex(KEY_WIDGET_ID))));
                note.setContent(c.getString((c.getColumnIndex(KEY_CONTENT))));
                note.setTextSize(c.getInt((c.getColumnIndex(KEY_TEXT_SIZE))));
                note.setTextAlign(c.getInt((c.getColumnIndex(KEY_TEXT_ALIGN))));
                note.setTextColor(c.getInt((c.getColumnIndex(KEY_TEXT_COLOR))));
                note.setRotate(c.getInt((c.getColumnIndex(KEY_ROTATE))));
                note.setBackground(c.getInt((c.getColumnIndex(KEY_BACKGROUND))));
                note.setPin(c.getInt((c.getColumnIndex(KEY_PIN))));

                listNote.add(note);
            } while (c.moveToNext());
        }
        return listNote;
    }

    /*Get note by Id*/
    public StickyNote getNoteByNoteId(int id) {
        String selectQuery = "SELECT  * FROM " + TABLE_STICKY_NOTE + " WHERE " + KEY_ID + " = " + "'" + id + "'";
        StickyNote note = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            note = new StickyNote();
            note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            note.setWidgetId(c.getInt((c.getColumnIndex(KEY_WIDGET_ID))));
            note.setContent(c.getString((c.getColumnIndex(KEY_CONTENT))));
            note.setTextSize(c.getInt((c.getColumnIndex(KEY_TEXT_SIZE))));
            note.setTextAlign(c.getInt((c.getColumnIndex(KEY_TEXT_ALIGN))));
            note.setTextColor(c.getInt((c.getColumnIndex(KEY_TEXT_COLOR))));
            note.setRotate(c.getInt((c.getColumnIndex(KEY_ROTATE))));
            note.setBackground(c.getInt((c.getColumnIndex(KEY_BACKGROUND))));
            note.setPin(c.getInt((c.getColumnIndex(KEY_PIN))));
        }
        return note;
    }

    /*Get note by widgetId*/
    public StickyNote getNoteByWidgetId(int widgetId) {
        String selectQuery = "SELECT  * FROM " + TABLE_STICKY_NOTE + " WHERE " + KEY_WIDGET_ID + " = " + "'" + widgetId + "'";
        StickyNote note = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            note = new StickyNote();
            note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
            note.setWidgetId(c.getInt((c.getColumnIndex(KEY_WIDGET_ID))));
            note.setContent(c.getString((c.getColumnIndex(KEY_CONTENT))));
            note.setTextSize(c.getInt((c.getColumnIndex(KEY_TEXT_SIZE))));
            note.setTextAlign(c.getInt((c.getColumnIndex(KEY_TEXT_ALIGN))));
            note.setTextColor(c.getInt((c.getColumnIndex(KEY_TEXT_COLOR))));
            note.setRotate(c.getInt((c.getColumnIndex(KEY_ROTATE))));
            note.setBackground(c.getInt((c.getColumnIndex(KEY_BACKGROUND))));
            note.setPin(c.getInt((c.getColumnIndex(KEY_PIN))));
        }
        return note;
    }

    /*Update note by id*/
    public int updateNote(StickyNote note, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, note.getContent());
        values.put(KEY_TEXT_SIZE, note.getTextSize());
        values.put(KEY_TEXT_ALIGN, note.getTextAlign());
        values.put(KEY_TEXT_COLOR, note.getTextColor());
        values.put(KEY_ROTATE, note.getRotate());
        values.put(KEY_BACKGROUND, note.getBackground());
        values.put(KEY_PIN, note.getPin());

        return db.update(TABLE_STICKY_NOTE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /*Update note by id*/
    public int updateWidgetId(int widgetId, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WIDGET_ID, widgetId);

        return db.update(TABLE_STICKY_NOTE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /*Delete note*/
    public void deleteNote(String[] ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        String args = TextUtils.join(", ", ids);
        db.execSQL(String.format("DELETE FROM " + TABLE_STICKY_NOTE + " WHERE " + KEY_ID + " IN (%s);", args));
        db.close();
    }


}
