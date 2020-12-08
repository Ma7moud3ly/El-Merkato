/**
 * El Merkato الميركاتو -
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-12-04
 */
package com.ma7moud3ly.elmerkato.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ma7moud3ly.elmerkato.databinding.ItemCompanyBinding;
import com.ma7moud3ly.elmerkato.observables.UiState;
import com.ma7moud3ly.elmerkato.repositories.Company;
import com.ma7moud3ly.elmerkato.repositories.ImagesRepository;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.MyViewHolder> {
    private List<Company> list;
    private UiState uiState;

    public CompaniesAdapter(List<Company> list, UiState uiState) {
        this.list = list;
        this.uiState = uiState;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCompanyBinding binding = ItemCompanyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.setUi(this.uiState);
        return new MyViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Company company = list.get(position);
        holder.binding.setCompany(company);
        if (company.images != null && company.images.size() > 0)
            ImagesRepository.loadCompanyImage(company.images.get(0), holder.binding.companyImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseViewHolder {
        private final ItemCompanyBinding binding;

        public MyViewHolder(ItemCompanyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            setHeight(binding.cardCompany,4);

        }
    }

}
