package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.Comic;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.activity.MainActivity;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BANNER = 1;
    private static final int TYPE_LIST = 2;
    private static final int TYPE_RANK = 3;
    private RequestOptions requestOptions =
            new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888);
    private int viewType;
    private List<Comic> listComic;
    private final MainActivity mainActivity;

    public HomeAdapter(MainActivity mainActivity, List<Comic> listComic, int viewType) {
        this.listComic = listComic;
        this.viewType = viewType;
        this.mainActivity = mainActivity;
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
            bannerViewHolder.tvRating.setText(String.valueOf(comic.getRating()));
            bannerViewHolder.tvNumChap.setText(String.valueOf(comic.getChapters_count()));
            Glide.with(bannerViewHolder.imgBanner).load(comic.getThumbnail()).into(bannerViewHolder.imgBanner);
            bannerViewHolder.imgBanner.setOnClickListener(v -> {
                mainActivity.openDetailActivity(String.valueOf(comic.getId()));
            });
        } else if (holder instanceof ListViewHolder) {
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            Glide.with(listViewHolder.imgThumbnail).load(comic.getThumbnail()).into(listViewHolder.imgThumbnail);
            listViewHolder.imgThumbnail.setOnClickListener(v -> {
                mainActivity.openDetailActivity(String.valueOf(comic.getId()));
            });
        } else {
            RankViewHolder rankViewHolder = (RankViewHolder) holder;
            rankViewHolder.tvName.setText(comic.getName());
            rankViewHolder.tvViews.setText(String.valueOf(comic.getViews()));
            Glide.with(rankViewHolder.imgThumbnail).load(comic.getThumbnail()).into(rankViewHolder.imgThumbnail);
            rankViewHolder.itemRank.setOnClickListener(v -> {
                mainActivity.openDetailActivity(String.valueOf(comic.getId()));
            });
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
        ConstraintLayout itemRank;
        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
            tvName = itemView.findViewById(R.id.tvName);
            tvViews = itemView.findViewById(R.id.tvViews);
            itemRank = itemView.findViewById(R.id.item_ranking);
        }
    }

}
