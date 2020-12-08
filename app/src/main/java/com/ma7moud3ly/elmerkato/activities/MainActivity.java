
/**
 * El Merkato الميركاتو -
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;
import com.ma7moud3ly.elmerkato.R;
import com.ma7moud3ly.elmerkato.databinding.ActivityMainBinding;
import com.ma7moud3ly.elmerkato.databinding.SectionAppContentBinding;
import com.ma7moud3ly.elmerkato.databinding.SectionAppHeaderBinding;
import com.ma7moud3ly.elmerkato.databinding.SectionFooterBinding;
import com.ma7moud3ly.elmerkato.databinding.SectionToolsBinding;
import com.ma7moud3ly.elmerkato.di.DaggerActivityGraph;
import com.ma7moud3ly.elmerkato.fragments.CompaniesFragment;
import com.ma7moud3ly.elmerkato.fragments.FavouritesFragment;
import com.ma7moud3ly.elmerkato.fragments.CategoriesFragment;
import com.ma7moud3ly.elmerkato.fragments.HomeFragment;
import com.ma7moud3ly.elmerkato.fragments.ProductsFragment;
import com.ma7moud3ly.elmerkato.repositories.ImagesRepository;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public ActivityMainBinding binding;
    public SectionAppContentBinding sectionContent;
    public SectionAppHeaderBinding appHeader;
    public SectionFooterBinding sectionFooter;
    public SectionToolsBinding sectionTools;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //request the dependencies from dagger
        activityGraph = DaggerActivityGraph.factory().create(this);
        activityGraph.inject(this);

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //restore saved settings in the shared prefs
        getSettings();

        initHeader();
        initNavigationLayout();
        //navigate HomeFragment as home page
        navigateTo(new HomeFragment(), null, false);
    }

    //init app bar, and search box
    private void initHeader() {
        appHeader = binding.appHeader;
        sectionContent = binding.appContent;
        sectionTools = sectionContent.sectionTools;
        sectionFooter = sectionContent.sectionFooter;
        sectionContent.setUi(uiState);
        appHeader.setUi(uiState);
        setSupportActionBar(appHeader.toolbar);

        appHeader.searchBox.setFocusableInTouchMode(false);
        appHeader.searchBox.setFocusable(false);
        appHeader.searchBox.setOnTouchListener((view, motionEvent) -> {
            view.setFocusableInTouchMode(true);
            view.setFocusable(true);
            uiState.isSearch.set(true);
            return false;
        });
        //don't show the keyboard when launched the activity
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void initNavigationLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, appHeader.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            back(new View(this));
        }
    }

    //on back return to the previous fragment if exists
    public void back(View v) {
        try {
            getSupportFragmentManager().popBackStack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();

        if (id == R.id.nav_share) share();
        else if (id == R.id.nav_rate) rate();
        else if (id == R.id.nav_home) fragment = new HomeFragment();
        else if (id == R.id.nav_categories) fragment = new CategoriesFragment();
        else if (id == R.id.nav_companies) fragment = new CompaniesFragment();
        else if (id == R.id.nav_products) fragment = new ProductsFragment();
        else if (id == R.id.nav_favourite) fragment = new FavouritesFragment();
        else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else return true;

        navigateTo(fragment, null, true);
        return true;
    }

    /**
     * Navigate to the given fragment.
     * @param fragment: Fragment to navigate to.
     * @param bundle: to pass arguments with the fragment
     * @param addToBackStack: Whether or not the current fragment should be added to the backStack.
     */
    public void navigateTo(Fragment fragment, Bundle bundle, boolean addToBackStack) {
        if (bundle != null) fragment.setArguments(bundle);
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public void navigateFragment(View v) {
        int id = v.getId();
        if (id == R.id.home) fragment = new HomeFragment();
        else if (id == R.id.categories || id == R.id.chip_categories || id == R.id.more_categories)
            fragment = new CategoriesFragment();
        else if (id == R.id.companies || id == R.id.chip_companies || id == R.id.more_companies)
            fragment = new CompaniesFragment();
        else if (id == R.id.products || id == R.id.chip_products)
            fragment = new ProductsFragment();
        else if (id == R.id.favourites || id == R.id.chip_favourite)
            fragment = new FavouritesFragment();
        navigateTo(fragment, null, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pref.put("split", uiState.split.get());
        pref.put("show_images", uiState.showImages.get());
    }

    private void getSettings() {
        uiState.split.set(pref.get("split", CONSTANTS.DOUBLE));
        uiState.showImages.set(pref.get("show_images", true));
        ImagesRepository.loadImages = uiState.showImages.get();

    }
}
