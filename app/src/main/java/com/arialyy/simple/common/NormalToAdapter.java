package com.arialyy.simple.common;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import com.arialyy.simple.R;
import com.arialyy.simple.base.adapter.AbsHolder;
import com.arialyy.simple.base.adapter.AbsRVAdapter;
import com.arialyy.simple.to.NormalTo;
import java.util.List;

/**
 * normalto实体对应的简单适配器
 */
public class NormalToAdapter extends AbsRVAdapter<NormalTo, NormalToAdapter.Holder> {

  public NormalToAdapter(Context context, List<NormalTo> data) {
    super(context, data);
  }

  @Override protected NormalToAdapter.Holder getViewHolder(View convertView, int viewType) {
    return new NormalToAdapter.Holder(convertView);
  }

  @Override protected int setLayoutId(int type) {
    return R.layout.item_download;
  }

  @Override protected void bindData(NormalToAdapter.Holder holder, int position, NormalTo item) {
    holder.text.setText(item.title);
    holder.image.setImageResource(item.icon);
  }

  class Holder extends AbsHolder {
    TextView text;
    AppCompatImageView image;

    Holder(View itemView) {
      super(itemView);
      text = findViewById(R.id.title);
      image = findViewById(R.id.image);
    }
  }
}