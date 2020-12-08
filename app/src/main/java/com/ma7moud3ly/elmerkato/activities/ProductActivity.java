
/**
 * El Merkato الميركاتو -
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.ma7moud3ly.elmerkato.R;
import com.ma7moud3ly.elmerkato.databinding.ActivityProductBinding;
import com.ma7moud3ly.elmerkato.di.DaggerActivityGraph;
import com.ma7moud3ly.elmerkato.models.ProductViewModel;
import com.ma7moud3ly.elmerkato.observables.UiState;
import com.ma7moud3ly.elmerkato.repositories.Product;
import com.ma7moud3ly.elmerkato.util.CONSTANTS;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

// this class show a product with details
public class ProductActivity extends BaseActivity {
    private ActivityProductBinding binding;
    private ProductViewModel model;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // request dependencies from dagger
        activityGraph = DaggerActivityGraph.factory().create(this);
        activityGraph.inject(this);
        //inflate layout with data binding
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setUi(uiState);

        //make the status bar transparent and overlap the layout inside it
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.transBlack));
        //receive the product as serialized json string from intents
        if (getIntent().hasExtra("product")) {
            product = Product.deserialize(getIntent().getStringExtra("product"));
            uiState.showImages.set(getIntent().getBooleanExtra("show_images", true));
        } else {
            finish();//leave if no products send, what gonna do then ?
        }

        //attach ProductViewModel to this activity
        model = new ViewModelProvider(this, viewModelFactory).get(ProductViewModel.class);
        //observe company live data object to receive the product's company info like location,contacts etc..
        model.company.observe(this, company -> {
            if (company == null || product == null) return;
            binding.setProduct(product);
            binding.setCompany(company);
            model.isExist(product);
            //init a slider with product images
            initImageSlider(product.images, binding.productSlider, binding.productIndicator, CONSTANTS.FROM_PRODUCTS, false);
            uiState.state.set(CONSTANTS.LOADED);
        });

        //observe the existence of the product in favourite database
        model.isFaved.observe(this, exists -> {
            binding.favToggle.setChecked(exists);
        });
        //add or remove the product in favourites
        binding.favToggle.setOnClickListener(view -> {
            if (product == null) return;
            if (model.isFaved.getValue() == true)
                model.delFav(product);
            else
                model.addFav(product);
        });

        //show loading progress
        //request the company information of the product
        uiState.state.set(CONSTANTS.LOADING);
        model.readCompany(product.company_id);

    }

    public void back(View v) {
        finish();
    }

    public void call(View v) {
        String contact = binding.companyContacts.getText().toString();
        super.call(contact);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.company.setValue(null);
    }
}
