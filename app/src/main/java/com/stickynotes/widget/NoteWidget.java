package com.stickynotes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.stickynotes.R;
import com.stickynotes.activity.DetailNoteActivity;
import com.stickynotes.activity.ListNoteWidgetActivity;

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



    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        CharSequence content = ListNoteWidgetActivity.content;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_note_widget);
        views.setTextViewText(R.id.tvEditor, content);
        views.setTextViewTextSize(R.id.tvEditor, TypedValue.COMPLEX_UNIT_SP, ListNoteWidgetActivity.textSize);
        views.setTextColor(R.id.tvEditor, Color.parseColor(ListNoteWidgetActivity.textColor));
        views.setImageViewResource(R.id.imgPin, ListNoteWidgetActivity.pin);
        views.setImageViewResource(R.id.imgBackground, ListNoteWidgetActivity.background);

        Intent intent = new Intent(context, DetailNoteActivity.class);
//        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra("ID", ListNoteWidgetActivity._id);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);


        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.frNote, configPendingIntent);

        Intent intentWidget = new Intent(context,NoteWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");

        int[] ids = {appWidgetId};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        context.sendBroadcast(intentWidget);

//        AppWidgetManager appWidgetManager2 = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(new ComponentName(context.getPackageName(), NoteWidget.class.getName()), views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
