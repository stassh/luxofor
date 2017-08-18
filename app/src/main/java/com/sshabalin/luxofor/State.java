package com.sshabalin.luxofor;

import android.graphics.Color;

/**
 * Created by sshabalin on 16/08/2017.
 */

class State {
  static final int CLEAR = 1;
  static final int ERROR = 2;
  static final int BUILDING = 3;
  static final int UNKNOWN = 4;

  static int getColor(int stateValue) {
    switch (stateValue) {
      case CLEAR:
        return Color.GREEN;
      case ERROR:
        return Color.RED;
      case BUILDING:
        return Color.BLUE;
      default:
        return Color.YELLOW;
    }
  }

  static int nextState(int state) {
    state++;
    if (state > UNKNOWN) state = CLEAR;
    return state;
  }
}
