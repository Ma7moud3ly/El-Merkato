/**
 * El Merkato الميركاتو -
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ma7moud3ly.elmerkato.databinding.ItemDepartmentBinding;
import com.ma7moud3ly.elmerkato.repositories.Category;
import com.ma7moud3ly.elmerkato.repositories.ImagesRepository;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.MyViewHolder> {

    private List<Category> list;

    public DepartmentAdapter(List<Category> list) {
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDepartmentBinding binding = ItemDepartmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category category = list.get(position);
        holder.binding.categoryText.setText(category.name);
        ImagesRepository.loadImage(holder.binding.departmentImage, "company/departs/" + category.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseViewHolder {
        private final ItemDepartmentBinding binding;

        public MyViewHolder(ItemDepartmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            setHeight(binding.cardDepartment, 5);
        }


    }
}
