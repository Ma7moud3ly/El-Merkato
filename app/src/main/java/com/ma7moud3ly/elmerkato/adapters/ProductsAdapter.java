/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ma7moud3ly.elmerkato.databinding.ItemProductBinding;
import com.ma7moud3ly.elmerkato.observables.UiState;
import com.ma7moud3ly.elmerkato.repositories.ImagesRepository;
import com.ma7moud3ly.elmerkato.repositories.Product;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private List<Product> list;
    private UiState uiState;

    public ProductsAdapter(List<Product> list, UiState uiState) {
        this.list = list;
        this.uiState = uiState;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.setUi(this.uiState);
        return new MyViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ProductsAdapter.MyViewHolder holder, int position) {
        Product product = list.get(position);
        holder.binding.setProduct(product);

        if (product.images.size() > 0)
            ImagesRepository.loadProductImage(product.images.get(0), holder.binding.productItemImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseViewHolder {
        public ItemProductBinding binding;

        public MyViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            setHeight(binding.productItemImage, 5);
        }
    }
}
