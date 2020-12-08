/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.fragments;

public interface MyCallbacks {
    public void onSearch(String query);

    public void onSearchCleared();

    public void onItemSelected(int index);

    public void onScrollToTop();

    public void onScrollToBottom();

    public void onNetworkRetry();

    public void read();
}

