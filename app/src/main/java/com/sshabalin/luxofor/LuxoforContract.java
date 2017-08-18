package com.sshabalin.luxofor;

/**
 * Created by sshabalin on 16/08/2017.
 */
public class LuxoforContract {
  public interface View {
    void setStatus(int widgetId, int state);
  }

  public interface Presenter {
    void update();
  }
}
