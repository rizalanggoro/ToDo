package com.bonfire.todo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bonfire.todo.AppConfiguration;
import com.bonfire.todo.R;
import com.bonfire.todo.datas.TaskCategoryData;
import com.bonfire.todo.utils.FeatherIcon;
import com.bonfire.todo.utils.Font;
import com.bonfire.todo.utils.Size;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTaskCategoryAdapter extends RecyclerView.Adapter {
  private Activity activity;
  private int maxCard = 2;
  private AppConfiguration appConfiguration;
  private String selectedCategoryKey = "";
  private FeatherIcon featherIcons;

  private ArrayList<TaskCategoryData> taskCategoryDataArrayList;

  public interface onCategoryListener {
    void onCategoryChanged(TaskCategoryData data);
  }

  private onCategoryListener onCategoryListener;
  public MyTaskCategoryAdapter setOnCategoryListener(onCategoryListener listener) {
    if (listener != null) this.onCategoryListener = listener;
    return this;
  }

  public MyTaskCategoryAdapter(Activity activity) {
    this.activity = activity;
    this.appConfiguration = new AppConfiguration(this.activity);
    this.featherIcons = new FeatherIcon(this.activity);

    this.a();
  }

  private void a() {
    this.selectedCategoryKey = this.appConfiguration.getSelectedTaskCategoryKey();
  }

  public void setTaskCategoryDataArrayList(ArrayList<TaskCategoryData> taskCategoryDataArrayList) {
    this.taskCategoryDataArrayList = taskCategoryDataArrayList;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_task_category, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((Holder) holder).bind(position, this.taskCategoryDataArrayList.get(position));
    ((Holder) holder).frameLayout.setOnClickListener(view -> {
      String categoryKey = this.taskCategoryDataArrayList.get(position).getKey();
      if (!this.appConfiguration.getSelectedTaskCategoryKey().equals(categoryKey)) {
        this.appConfiguration.setSelectedTaskCategoryKey(categoryKey);
        this.selectedCategoryKey = categoryKey;
        this.notifyDataSetChanged();
        if (this.onCategoryListener != null)
          this.onCategoryListener.onCategoryChanged(this.taskCategoryDataArrayList.get(position));
      }
    });
  }

  @Override
  public int getItemCount() {
    return this.taskCategoryDataArrayList.size();
  }

  public class Holder extends RecyclerView.ViewHolder {
    @BindView(R.id.card)
    CardView cardView;
    @BindView(R.id.fr)
    FrameLayout frameLayout;
    @BindView(R.id.ln)
    LinearLayout linearLayout;

    @BindView(R.id.img_icon)
    ImageView imageViewIcon;
    @BindView(R.id.img_background_icon)
    ImageView imageViewBackgroundIcon;
    @BindView(R.id.tv_title)
    TextView textViewTitle;
    @BindView(R.id.tv_subtitle)
    TextView textViewSubtitle;

    public Holder(@NonNull View itemView) {
      super(itemView);

      ButterKnife.bind(this, itemView);
      this.a();
    }

    private int backgroundIconColor = 0;
    private void bind(int position, TaskCategoryData taskCategoryData) {
      this.textViewTitle.setText(taskCategoryData.getCategoryName());
      this.textViewSubtitle.setText(
          activity.getString(R.string.task_count).replace("%count%", taskCategoryData.getCategoryTotalTask())
      );

      /* int categoryIcon = Integer.parseInt(taskCategoryData.getCategoryIcon());
      this.imageViewIcon.setImageResource(categoryIcon); */

      this.imageViewIcon.setImageResource(featherIcons.getResource(taskCategoryData.getCategoryIcon()));

      int categoryIconColor = Integer.parseInt(taskCategoryData.getCategoryIconColor());
      this.imageViewIcon.setColorFilter(categoryIconColor);
      this.backgroundIconColor = Integer.parseInt(taskCategoryData.getCategoryIconBackgroundColor());
      this.imageViewBackgroundIcon.setColorFilter(backgroundIconColor);

      this.b(position, taskCategoryData);
    }

    private void b(int position, TaskCategoryData data) {
      if (selectedCategoryKey.equals("")) {
        // default is all task
        if (position == 0) {
          this.setCardBackground(true);
        } else {
          this.setCardBackground(false);
        }
      } else if (selectedCategoryKey.equals(data.getKey())) {
        this.setCardBackground(true);
      } else {
        this.setCardBackground(false);
      }
    }

    private void setCardBackground(boolean isSelected) {
      int selectedCardColor = this.backgroundIconColor; // 0xfff5f5f5
      if (isSelected)
        this.linearLayout.setBackgroundColor(selectedCardColor);
      else
        this.linearLayout.setBackgroundResource(R.drawable.background_unselected_category);
    }

    private void a() {
      Font.setGilroyBold(this.textViewTitle);

      int widthPixel = Size.getWidthPixels(activity);
      int divWidth = 16;
      int divWidthPx = (int) Size.dpToPx(divWidth, activity);
      int totalDivPx = divWidthPx * (maxCard + 1);
      int cardWidthPx = (widthPixel - totalDivPx) / maxCard;
      this.cardView.setLayoutParams(new LinearLayout.LayoutParams(cardWidthPx, cardWidthPx));
      this.frameLayout.setLayoutParams(new CardView.LayoutParams(cardWidthPx, cardWidthPx));
      this.linearLayout.setLayoutParams(new CardView.LayoutParams(cardWidthPx, cardWidthPx));
    }
  }
}
