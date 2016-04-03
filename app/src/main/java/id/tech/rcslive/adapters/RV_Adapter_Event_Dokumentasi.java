package id.tech.rcslive.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.tech.rcslive.activity.R;
import id.tech.rcslive.models.RowData_Dokumentasi;
import id.tech.rcslive.models.Rowdata_EventUserJoined;

public class RV_Adapter_Event_Dokumentasi extends RecyclerView.Adapter<RV_Adapter_Event_Dokumentasi.ViewHolder>{
    private Context context;
    private List<RowData_Dokumentasi> datum;

    public RV_Adapter_Event_Dokumentasi(Context context, List<RowData_Dokumentasi> datum) {
        this.context = context;
        this.datum = datum;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RowData_Dokumentasi item = datum.get(position);
        Glide.with(context).load(item.getDocumentationPhoto()).placeholder(R.drawable.img_empty).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return datum.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_img_dokumentasi, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img);

        }
    }
}
