package com.ma7moud3ly.elmerkato.di.modules;

import com.ma7moud3ly.elmerkato.di.ViewModelFactory;
import com.ma7moud3ly.elmerkato.models.CategoriesViewModel;
import com.ma7moud3ly.elmerkato.models.CompaniesViewModel;
import com.ma7moud3ly.elmerkato.models.FavouritesViewModel;
import com.ma7moud3ly.elmerkato.models.HomeViewModel;
import com.ma7moud3ly.elmerkato.models.ProductViewModel;
import com.ma7moud3ly.elmerkato.models.ProductsViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel provideHomeViewModel(HomeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel.class)
    abstract ViewModel provideCategoriesViewModel(CategoriesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CompaniesViewModel.class)
    abstract ViewModel provideCompaniesViewModel(CompaniesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel.class)
    abstract ViewModel provideProductsViewModel(ProductsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel.class)
    abstract ViewModel provideProductViewModel(ProductViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel.class)
    abstract ViewModel provideFavouritesViewModel(FavouritesViewModel viewModel);
}