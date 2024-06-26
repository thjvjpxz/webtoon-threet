package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models.Comic;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BANNER = 1;
    private static final int TYPE_LIST = 2;
    private static final int TYPE_RANK = 3;
    private RequestOptions requestOptions =
            new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888);
    private int viewType;
    private List<Comic> listComic;

    public HomeAdapter(List<Comic> listComic, int viewType) {
        this.listComic = listComic;
        this.viewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER)
            return new BannerViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner_home, parent, false));
        else if (viewType == TYPE_LIST)
            return new ListViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_hori, parent, false));
        else
            return new RankViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comic comic = listComic.get(position);
        BannerViewHolder bannerViewHolder = null;
        if (holder instanceof BannerViewHolder) {
            bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.tvName.setText(comic.getName());
            Glide.with(bannerViewHolder.imgBanner)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(comic.getThumbnail())
                    .into(bannerViewHolder.imgBanner);
        } else if (holder instanceof ListViewHolder) {
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            Glide.with(listViewHolder.imgThumbnail)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(comic.getThumbnail())
                    .into(listViewHolder.imgThumbnail);
        } else {
            RankViewHolder rankViewHolder = (RankViewHolder) holder;
            rankViewHolder.tvName.setText(comic.getName());
            Glide.with(rankViewHolder.imgThumbnail)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(comic.getThumbnail())
                    .into(rankViewHolder.imgThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return listComic == null ? 0 : listComic.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBanner;
        TextView tvName, tvRating, tvNumChap;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.imgPoster);
            tvName = itemView.findViewById(R.id.tvName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvNumChap = itemView.findViewById(R.id.tvNumChap);
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
        }
    }

    public static class RankViewHolder extends RecyclerView.ViewHolder {
        ImageView imgThumbnail;
        TextView tvName, tvViews;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
            tvName = itemView.findViewById(R.id.tvName);
            tvViews = itemView.findViewById(R.id.tvViews);
        }
    }
}
