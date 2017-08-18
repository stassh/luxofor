package com.sshabalin.luxofor;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality. App Widget Configuration implemented in {@link
 * WidgetConfigureActivity WidgetConfigureActivity}
 */
public class Widget extends AppWidgetProvider {

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId) {

    CharSequence widgetText = WidgetConfigureActivity.loadTitlePref(context, appWidgetId);
    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main_widget);

    views.setInt(R.id.apple_logo_img, "setColorFilter", Color.RED);
    views.setInt(R.id.android_logo_img, "setColorFilter", Color.GREEN);
    views.setInt(R.id.windows_logo_img, "setColorFilter", Color.YELLOW);

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    int appWidgetId = (appWidgetIds != null && appWidgetIds.length == 1) ? appWidgetIds[0] : INVALID_APPWIDGET_ID;

    Intent intent = new Intent(context, Service.class);
    intent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
    context.startService(intent);

//    for (int appWidgetId : appWidgetIds) {
//      updateAppWidget(context, appWidgetManager, appWidgetId);
//    }
  }

  @Override
  public void onDeleted(Context context, int[] appWidgetIds) {
    // When the user deletes the widget, delete the preference associated with it.
    for (int appWidgetId : appWidgetIds) {
      WidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}

