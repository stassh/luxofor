package com.sshabalin.luxofor;

/**
 * Created by sshabalin on 16/08/2017.
 */
class LuxoforContract {
  interface View {
    void setStatus(int widgetId, int state);
  }

  interface Presenter {
    void update();
  }
}
