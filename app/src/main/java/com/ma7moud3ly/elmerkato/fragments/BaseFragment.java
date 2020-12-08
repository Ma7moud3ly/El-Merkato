/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.ma7moud3ly.elmerkato.R;
import com.ma7moud3ly.elmerkato.activities.MainActivity;
import com.ma7moud3ly.elmerkato.databinding.SectionRecyclerBinding;
import com.ma7moud3ly.elmerkato.di.ViewModelFactory;
import com.ma7moud3ly.elmerkato.observables.UiState;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;
import com.ma7moud3ly.elmerkato.util.CheckInternet;
import com.ma7moud3ly.elmerkato.util.RecyclerTouchListener;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BaseFragment extends Fragment implements MyCallbacks {
    public RecyclerView recyclerView;
    public RecyclerView.Adapter recyclerAdapter;
    public SectionRecyclerBinding binding;
    public EditText searchBox;
    public UiState uiState;
    @Inject
    public ViewModelFactory viewModelFactory;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //request dependencies from dagger
        ((MainActivity)getActivity()).activityGraph.inject(this);
        super.onViewCreated(view, savedInstanceState);
        uiState = ((MainActivity) getActivity()).uiState;
        searchBox = ((MainActivity) getActivity()).appHeader.searchBox;

        initSearchBox();
        //attach click enets to retry and refresh buttons
        ((MainActivity) getActivity()).sectionContent.retry.setOnClickListener(v -> onNetworkRetry());
        ((MainActivity) getActivity()).sectionTools.bubbleRefreshBtn.setOnClickListener(v -> onNetworkRetry());
        //show search box
        //home fragments hide the search box, so others had to show it if hidden
        uiState.showSearch.set(true);
    }

    public void setTitle(String title, String subTitle, String subSubTitle) {
        uiState.title.set(title);
        uiState.subTitle.set(subTitle);
        uiState.subSubTitle.set(subSubTitle);
    }

    /**
     * this method inits the recycler view used by several fragments
     * @param recyclerView: view need to be initialized
     * @param recyclerAdapter: the recycleAdapter needed to be attached to the recyclerView
     * */
    public void initRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter recyclerAdapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
        //respond to recycler items click event and call onItemSelected method implemented by several fragments
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                onItemSelected(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //call onScrollToBottom when the recycler reach the end, this important for pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(-1)) {
                    onScrollToTop();
                } else if (!recyclerView.canScrollVertically(1)) {
                    onScrollToBottom();
                }

            }
        });

        this.recyclerView = recyclerView;
        this.recyclerAdapter = recyclerAdapter;
    }

    public void initSearchBox() {
        searchBox.setOnEditorActionListener((textView, i, keyEvent) -> {
            String query = textView.getText().toString().trim();
            if (query.length() > 1)
                onSearch(query);
            return false;
        });
        ((MainActivity) getActivity()).appHeader.btnClearSearch.setOnClickListener(v -> {
            clearSearch(false);
        });
    }

    public void clearSearch(boolean leaving) {
        searchBox.setFocusableInTouchMode(false);
        searchBox.setFocusable(false);
        searchBox.setText("");
        ((MainActivity) getActivity()).uiState.isSearch.set(false);
        ((MainActivity) getActivity()).hideKeyboard();
        if (leaving == false)
            onSearchCleared();
    }

    public boolean isSearch() {
        return searchBox.getText().toString().trim().isEmpty() == false;
    }

    public void networkState(int state) {
        uiState.state.set(state);
    }

    /**
     * change the color of selected icon in the footer depending its fragment
     * @param fragment: constant in tell the method in which fragment we are now to select its icon in the footer
     * */
    public void currentFragment(int fragment) {
        uiState.footerButton.set(fragment);
    }

    //on no internet show an error message and retry button
    public void noInternetMessage() {
        Snackbar.make(getView(), getResources().getText(R.string.no_ie_connection), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        networkState(CONSTANTS.RETRY);
    }

    @Override
    public void onSearch(String query) {

    }

    @Override
    public void onSearchCleared() {

    }

    @Override
    public void onItemSelected(int index) {

    }

    @Override
    public void onScrollToTop() {

    }

    @Override
    public void onScrollToBottom() {

    }


    @Override
    public void onNetworkRetry() {
        if (!CheckInternet.isConnected()) {
            noInternetMessage();
            return;
        }
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
        networkState(CONSTANTS.LOADING);
    }

    @Override
    public void read() {
        if (!CheckInternet.isConnected())
            noInternetMessage();
        else
            networkState(CONSTANTS.LOADING);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearSearch(true);
        networkState(CONSTANTS.LOADED);
    }
}


