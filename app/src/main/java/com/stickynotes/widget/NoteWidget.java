package com.stickynotes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

import com.stickynotes.R;
import com.stickynotes.activity.DetailNoteActivity;
import com.stickynotes.activity.ListNoteWidgetActivity;
import com.stickynotes.application.CommonApplication;
import com.stickynotes.database.DatabaseHandler;
import com.stickynotes.model.StickyNote;

/**
 * Created by TrungTV on 03/10/2017.
 */

public class NoteWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, NoteWidget.class);
            int[] allWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
            for (int i = 0; i < allWidgetIds.length; i++) {
                updateAppWidget(context, appWidgetManager, allWidgetIds[i]);
            }
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        DatabaseHandler db = new DatabaseHandler(context);
        CommonApplication application = ListNoteWidgetActivity.application;

        StickyNote note = db.getNoteByWidgetId(appWidgetId);
        if (note != null) {
            int _id = note.getId();
            String content = note.getContent();
            float textSize = application.textSize[note.getTextSize()];
            String textColor = application.color[note.getTextColor()];
            int pin = application.listPin[note.getPin()];
            int background = application.listBackground[note.getBackground()];
            int gravity = application.align[note.getTextAlign()];
            float rotate = application.rotateDegrees[note.getRotate()];
            RemoteViews views = null;
            if (rotate == 0) {
                views = new RemoteViews(context.getPackageName(), R.layout.item_note_widget_center);
            } else if (rotate == 10) {
                views = new RemoteViews(context.getPackageName(), R.layout.item_note_widget_left);
            } else if (rotate == -10) {
                views = new RemoteViews(context.getPackageName(), R.layout.item_note_widget_right);
            }
            int res = 0;
            if (gravity == 0) {
                views.setViewVisibility(R.id.tvEditorLeft, View.VISIBLE);
                views.setViewVisibility(R.id.tvEditorCenter, View.GONE);
                views.setViewVisibility(R.id.tvEditorRight, View.GONE);
                res = R.id.tvEditorLeft;
            } else if (gravity == 1) {
                views.setViewVisibility(R.id.tvEditorLeft, View.GONE);
                views.setViewVisibility(R.id.tvEditorCenter, View.VISIBLE);
                views.setViewVisibility(R.id.tvEditorRight, View.GONE);
                res = R.id.tvEditorCenter;
            } else if (gravity == 2) {
                views.setViewVisibility(R.id.tvEditorLeft, View.GONE);
                views.setViewVisibility(R.id.tvEditorCenter, View.GONE);
                views.setViewVisibility(R.id.tvEditorRight, View.VISIBLE);
                res = R.id.tvEditorRight;
            }
            views.setTextViewText(res, content);
            views.setTextViewTextSize(res, TypedValue.COMPLEX_UNIT_SP, textSize);
            views.setInt(res, "setBackgroundResource", background);
            views.setTextColor(res, Color.parseColor(textColor));
            views.setImageViewResource(R.id.imgPin, pin);

            Intent intent = new Intent(context, DetailNoteActivity.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra("ID", _id);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);


            PendingIntent configPendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.frNote, configPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
