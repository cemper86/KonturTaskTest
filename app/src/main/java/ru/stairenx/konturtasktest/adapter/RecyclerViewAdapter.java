package ru.stairenx.konturtasktest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.stairenx.konturtasktest.R;
import ru.stairenx.konturtasktest.item.ContactItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ContactViewHolder> implements Filterable {

    private Context context;
    private List<ContactItem> data;
    private ClickCard clickCard;
    private List<ContactItem> filterList;
    CustomFilter filter;

    public RecyclerViewAdapter(Context context, List<ContactItem> data, ClickCard clickCard) {
        this.context = context;
        this.data = data;
        this.filterList = data;
        this.clickCard = clickCard;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView name;
        TextView phone;
        TextView height;

        public ContactViewHolder(@NonNull View view) {
            super(view);
            card = view.findViewById(R.id.card_view);
            name = view.findViewById(R.id.card_name);
            phone = view.findViewById(R.id.card_phone);
            height = view.findViewById(R.id.card_height);
        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getName());
        holder.phone.setText(data.get(position).getPhone());
        holder.height.setText(String.valueOf(data.get(position).getHeight()));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCard.onClicked(v,data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickCard{
        void onClicked(View view, ContactItem item);
    }

    @Override
    public Filter getFilter() {
        if (filter == null) filter = new CustomFilter();
        return filter;
    }

    private class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                List<ContactItem> filters = new ArrayList<>();
                for (int i=0;i<filterList.size();i++){
                    if (filterList.get(i).getName().toUpperCase().contains(constraint)|| ObjectFormatter.getValidNumber(filterList.get(i).getPhone()).toUpperCase().contains(constraint)){
                        ContactItem part = new ContactItem(filterList.get(i).getId(),filterList.get(i).getName(),filterList.get(i).getPhone(),filterList.get(i).getHeight(),filterList.get(i).getBiography(),filterList.get(i).getTemperament(),filterList.get(i).getEducationPeriod());
                        filters.add(part);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }else{
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data = (List<ContactItem>) results.values;
            notifyDataSetChanged();
        }
    }

}
