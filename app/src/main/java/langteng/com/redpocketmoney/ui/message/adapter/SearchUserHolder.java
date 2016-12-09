package langteng.com.redpocketmoney.ui.message.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import langteng.com.redpocketmoney.R;
import langteng.com.redpocketmoney.ui.base.ImageLoaderFactory;
import langteng.com.redpocketmoney.ui.login._User;
import langteng.com.redpocketmoney.ui.message.adapter.base.BaseViewHolder;

public class SearchUserHolder extends BaseViewHolder {

  @Bind(R.id.avatar)
  public ImageView avatar;
  @Bind(R.id.name)
  public TextView name;
  @Bind(R.id.btn_add)
  public Button btn_add;

  public SearchUserHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
    super(context, root, R.layout.item_search_user,onRecyclerViewListener);
  }

  @Override
  public void bindData(Object o) {
    final _User user =(_User)o;
    ImageLoaderFactory.getLoader().loadAvator(avatar,user.userIcon, R.mipmap.head);
    name.setText(user.getUsername());
    btn_add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {//查看个人详情
          Bundle bundle = new Bundle();
          bundle.putSerializable("u", user);
//          startActivity(UserInfoActivity.class,bundle);
        }
    });
  }
}