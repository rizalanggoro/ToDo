package com.bonfire.todo.adapters;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.R;
import com.bonfire.todo.utils.FeatherIcon;
import com.bonfire.todo.utils.FeatherIcons;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IconAdapter extends RecyclerView.Adapter {
  private Activity activity;
  private FeatherIcon featherIcon;

  public IconAdapter(Activity activity) {
    this.activity = activity;
    this.featherIcon = new FeatherIcon(this.activity);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_icon, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((Holder) holder).bind(FeatherIcons.values()[position]);
  }

  @Override
  public int getItemCount() {
    return FeatherIcons.values().length;
  }

  public class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.img_icon)
    ImageView imageViewIcon;
    @BindView(R.id.tv_title)
    TextView textViewTitle;

    public Holder(@NonNull View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
      this.a();
    }

    private void bind(FeatherIcons icons) {
      int res = featherIcon.getResource(icons);
      String title = icons.toString();
      title = title.replace("_", " ");
      if (res != 0) {
        // set
        this.imageViewIcon.setImageResource(res);
        this.textViewTitle.setText(title);
      }
    }

    private void a() {
      int color = 0xff000000;
      this.imageViewIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
  }
}
