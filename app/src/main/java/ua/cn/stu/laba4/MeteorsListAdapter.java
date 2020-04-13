package ua.cn.stu.laba4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ua.cn.stu.laba4.database.models.Meteor;

public class MeteorsListAdapter extends RecyclerView.Adapter<MeteorsListAdapter.MeteorsViewHolder> {
    private List<Meteor> meteorsList;
    private Context context;
    private ItemClickListener itemClickListener;

    public MeteorsListAdapter( List<Meteor> list, Context context){
        this.meteorsList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MeteorsListAdapter.MeteorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_items, parent, false);
        return new MeteorsListAdapter.MeteorsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MeteorsListAdapter.MeteorsViewHolder holder, int position) {
        holder.name.setText( meteorsList.get(position).getName() );
        holder.year.setText( meteorsList.get(position).getYear() );
    }

    @Override
    public int getItemCount() {
        return meteorsList.size();
    }

    public void setItemClickListener( ItemClickListener itemClickListener ){
        this.itemClickListener = itemClickListener;
    }







    public class MeteorsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView year;

        public MeteorsViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            year = itemView.findViewById(R.id.year);
            // set onclick
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick( View view){
            if ( itemClickListener != null){
                // click from listener in mainactivity
                itemClickListener.onClick(view, getAdapterPosition());
            }
        }
    }
}
