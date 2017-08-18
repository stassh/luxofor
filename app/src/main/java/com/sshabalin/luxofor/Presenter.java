package com.sshabalin.luxofor;

import com.sshabalin.luxofor.LuxoforContract.View;

/**
 * Created by sshabalin on 18/08/2017.
 */

public class Presenter implements LuxoforContract.Presenter {
  private View view;
  private int status;
  private int apiWidgetId;

  /**
   *
   * @param view
   * @param status
   */
  public Presenter(LuxoforContract.View view, int apiWidgetId, int status) {
    this.view = view;
    this.status = status;
    this.apiWidgetId = apiWidgetId;
  }

  /**
   * {@inheritDoc}
   */
  public void update() {
    //// TODO: 18/08/2017 read value from repo 
    view.setStatus(apiWidgetId, State.nextState(status));
  }
}
