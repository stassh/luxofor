package com.sshabalin.luxofor;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.RemoteViews;
import java.sql.Time;

/**
 * Created by sshabalin on 16/08/2017.
 */

public class Service extends IntentService implements LuxoforContract.View {
  private static final String PACKAGE_NAME = "com.sshabalin.luxofor";
  private static final String METHOD_NAME = "setColorFilter";
  private static final String TAG = "Service";
  private AppWidgetManager appWidgetManager;
  public Service() {
    super(TAG);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onHandleIntent(@Nullable Intent intent) {

    appWidgetManager =
        AppWidgetManager.getInstance(this);
    final Context context = Service.this;

    int incomingAppWidgetId = intent.getIntExtra(
        EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);
    if (incomingAppWidgetId != INVALID_APPWIDGET_ID)
    {
      updateOneAppWidget(context, appWidgetManager,
          incomingAppWidgetId);
    }
    else
    {
      updateAllAppWidgets(context, appWidgetManager);
    }

    scheduleNextUpdate();
  }

  private void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager)
  {
    Log.d(TAG, "updateAllAppWidgets start");

    ComponentName appWidgetProvider = new ComponentName(this,
        Widget.class);
    int[] appWidgetIds =
        appWidgetManager.getAppWidgetIds(appWidgetProvider);
    int N = appWidgetIds.length;
    for (int i = 0; i < N; i++)
    {
      int appWidgetId = appWidgetIds[i];
      updateOneAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  public void updateOneAppWidget(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId) {
    Log.d(TAG, "updateOneAppWidget start " + appWidgetId);


    int state = State.CLEAR;

    LuxoforContract.Presenter presenter = new Presenter(this, state, appWidgetId);
    presenter.update();
  }

  private void scheduleNextUpdate() {
    Intent checkStateIntent =
        new Intent(this, this.getClass());
    // A content URI for this Intent may be unnecessary.
    checkStateIntent.setData(Uri.parse("content://" +
        PACKAGE_NAME + "/widget"));
    PendingIntent pendingIntent =
        PendingIntent.getService(this, 0, checkStateIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

    // The update frequency should be user configurable.
    long nextUpdate = System.currentTimeMillis() + DateUtils.SECOND_IN_MILLIS * 10;

    Log.d(TAG, "Schedule new update " + DateUtils.formatElapsedTime(nextUpdate));
    AlarmManager alarmManager =
        (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    alarmManager.setWindow(AlarmManager.RTC, nextUpdate, nextUpdate + 2000, pendingIntent);
  }

  @Override
  public void setStatus(int appWidgetId, int state) {
    CharSequence widgetText = WidgetConfigureActivity.loadTitlePref(this, appWidgetId);
    // Construct the RemoteViews object

    RemoteViews views = new RemoteViews(PACKAGE_NAME, R.layout.main_widget);
    Log.d(TAG, "State:" + state + " " + widgetText);

    views.setInt(R.id.apple_logo_img, METHOD_NAME, State.getColor(state));
    views.setInt(R.id.android_logo_img, METHOD_NAME, State.getColor(state));
    views.setInt(R.id.windows_logo_img, METHOD_NAME, State.getColor(state));

    //WidgetConfigureActivity.saveStatePref(this, appWidgetId, State.nextState(state));
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);

  }
}
