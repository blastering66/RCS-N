package id.tech.rcslive.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.tech.rcslive.activity.R;
import java.util.List;
import id.tech.rcslive.models.Rowdata_EventHighlight;

public class RV_Adapter_Highlight extends RecyclerView.Adapter<RV_Adapter_Highlight.ViewHolder>{
    private Context context;
    private List<Rowdata_EventHighlight> datum;

    public RV_Adapter_Highlight(Context context, List<Rowdata_EventHighlight> datum) {
        this.context = context;
        this.datum = datum;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Rowdata_EventHighlight item = datum.get(position);

        holder.tv_tgl.setText(item.getTvTgl());
        holder.tv_judul.setText(item.getTvJudul());
        holder.tv_alamat.setText(item.getTvAlamat());
        holder.tv_kategori.setText(item.getTvKategori());
        holder.tv_joined.setText(item.getJoined());
        Glide.with(context).load(item.getEventPhoto()).into(holder.img);
//        if(position == 0){
//            holder.img.setImageResource(R.drawable.img_vp_02);
//        }else{
//            holder.img.setImageResource(R.drawable.img_vp_03);
//        }

    }

    @Override
    public int getItemCount() {
        return datum.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_highlight, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img, btn_share;
        public TextView tv_tgl, tv_judul,tv_alamat,tv_kategori, tv_joined;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img);
            btn_share = (ImageView)itemView.findViewById(R.id.btn_share);

            tv_tgl = (TextView)itemView.findViewById(R.id.tv_tgl);
            tv_judul = (TextView)itemView.findViewById(R.id.tv_judul);
            tv_alamat = (TextView)itemView.findViewById(R.id.tv_alamat);
            tv_kategori = (TextView)itemView.findViewById(R.id.tv_kategori);
            tv_joined = (TextView)itemView.findViewById(R.id.tv_joined);

        }
    }
}
