/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ma7moud3ly.elmerkato.databinding.ItemCategoryBinding;
import com.ma7moud3ly.elmerkato.observables.UiState;
import com.ma7moud3ly.elmerkato.repositories.Category;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Category> list;
    private UiState uiState;

    public CategoryAdapter(List<Category> list, UiState uiState) {
        this.list = list;
        this.uiState=uiState;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.setUi(uiState);
        return new MyViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category category = list.get(position);
        holder.binding.setCategory(category);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseViewHolder {
        private final ItemCategoryBinding binding;

        public MyViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            setWH(binding.itemCategoryImage,3);
            setHeight(binding.categoryImage,4);

        }


    }
}
