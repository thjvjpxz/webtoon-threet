package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.models.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.R;
import vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.databinding.ItemLevelBinding;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {
    User user;
    Context context;
    private boolean isMaxLevelReached;

    public LevelAdapter(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level, parent, false);
        return new LevelViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        if (position == 0) {
            if (user.getLevel() == null) {
                checkLoadView(holder);
                holder.binding.tvExpCurrent.setText(user.getExp().toString());
                holder.binding.seekBar.setProgress(user.getExp());
                holder.binding.seekBar.setMax(10000);
                holder.binding.tvExpMax.setText("10000");
                holder.binding.tvExpRemain.setText("Còn cần " + (10000 - user.getExp()) + " điểm kinh nghiệm để nâng Level");
            } else if (user.getNextLevel() != null) {
                checkLoadView(holder);
                holder.binding.tvCurrentLevel.setText("LV " + user.getLevel().getId().toString());
                holder.binding.tvCurrentLvNumber.setText(user.getLevel().getId().toString());
                holder.binding.tvExpDefault.setText("LV " + user.getLevel().getId().toString() + "(" + user.getLevel().getChap() + ")");
                holder.binding.tvExpCurrent.setText(user.getExp().toString());
                holder.binding.tvExpMax.setText(user.getNextLevel().getChap().toString());
                holder.binding.tvExpNext.setText("LV " + user.getNextLevel().getId().toString() + "(" + user.getNextLevel().getChap() + ")");
                holder.binding.seekBar.setProgress(user.getExp());
                holder.binding.seekBar.setMax(user.getNextLevel().getChap());
                holder.binding.tvExpRemain.setText("Còn cần " + (user.getNextLevel().getChap() - user.getExp()) + " điểm kinh nghiệm để nâng Level");
            } else {
                isMaxLevelReached = true;
                holder.binding.tvCurrentLevel.setText("LV " + user.getLevel().getId().toString());
                holder.binding.tvCurrentLvNumber.setText(user.getLevel().getId().toString());
                holder.binding.tvExpDefault.setText("LV " + user.getLevel().getId().toString() + "(" + user.getLevel().getChap() + ")");
                holder.binding.tvExpCurrent.setText(user.getExp().toString());
                holder.binding.tvExpMax.setText("MAX");
                holder.binding.tvExpNext.setText("MAX");
                holder.binding.seekBar.setProgress(100);
                holder.binding.tvExpRemain.setText("Đã đạt Level cuối cùng");
            }
        } else if (position == 1) {
            if (user.getNextLevel() != null)
            {
                checkLoadView(holder);
                holder.binding.tvCurrentLevelTitle.setText("Chưa đạt");
                holder.binding.tvCurrentLvNumber.setText(user.getNextLevel().getId().toString());
                holder.binding.tvCurrentLevel.setText("LV " + user.getNextLevel().getId());
                holder.binding.tvExpDefault.setVisibility(View.INVISIBLE);
                holder.binding.tvExpCurrent.setVisibility(View.INVISIBLE);
                holder.binding.tvExpMax.setVisibility(View.INVISIBLE);
                holder.binding.tvExpDivider.setVisibility(View.INVISIBLE);
                holder.binding.tvExpNext.setVisibility(View.INVISIBLE);
                holder.binding.seekBar.setVisibility(View.INVISIBLE);
                holder.binding.tvExpRemain.setText("Còn cần " + (user.getNextLevel().getChap() - user.getExp()) + " điểm kinh nghiệm");
            }
        } else {
            checkLoadView(holder);
            holder.binding.tvCurrentLevelTitle.setText("Hết Level");
            holder.binding.tvCurrentLvNumber.setText("MAX");
            holder.binding.tvCurrentLevel.setText("LV MAX");
            holder.binding.tvExpDefault.setVisibility(View.INVISIBLE);
            holder.binding.tvExpCurrent.setVisibility(View.INVISIBLE);
            holder.binding.tvExpMax.setVisibility(View.INVISIBLE);
            holder.binding.tvExpDivider.setVisibility(View.INVISIBLE);
            holder.binding.tvExpNext.setVisibility(View.INVISIBLE);
            holder.binding.seekBar.setVisibility(View.INVISIBLE);
            holder.binding.tvExpRemain.setText("Đã đạt Level cuối cùng");
        }
    }

    @Override
    public int getItemCount() {
        if (isMaxLevelReached) {
            return 1;
        }
        return 2;
    }

    public static class LevelViewHolder extends RecyclerView.ViewHolder {
        ItemLevelBinding binding;
        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemLevelBinding.bind(itemView);
        }
    }

    private void checkLoadView(@NonNull LevelViewHolder holder) {
        holder.itemView.post(() -> {
            int currentWidth = holder.itemView.getWidth();

            float scale = context.getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (50 * scale + 0.5f);

            int newWidth = currentWidth - dpAsPixels;

            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.width = newWidth;
            holder.itemView.setLayoutParams(params);
        });
    }
}
