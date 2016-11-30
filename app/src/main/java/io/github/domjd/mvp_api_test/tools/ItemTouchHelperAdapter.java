package io.github.domjd.mvp_api_test.tools;

/**
 * Created by Dom on 28/11/2016.
 */
public interface ItemTouchHelperAdapter {
    void onItemMoved(int fromPosition, int toPosition);
    void onItemRemoved(int position);
}
