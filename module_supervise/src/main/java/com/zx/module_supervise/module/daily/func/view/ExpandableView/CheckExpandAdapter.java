package com.zx.module_supervise.module.daily.func.view.ExpandableView;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.module_supervise.R;
import com.zx.zxutils.util.ZXSystemUtil;

import java.util.List;

/**
 * Created by Xiangb on 2017/12/4.
 * 功能：
 */

public class CheckExpandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CheckExpandBean> showList;
    private Context context;
    private boolean showSelect = false;
    private boolean isMultiSelected = true;
    private boolean menuCanSelect = true;
    public MyHolder mLastViewTag = null;
    private OnExpandListener expandListener;

    public CheckExpandAdapter(Context context, List<CheckExpandBean> showList, boolean showSelect, boolean isMultiSelected, boolean menuCanSelect) {
        this.context = context;
        this.showList = showList;
        this.showSelect = showSelect;
        this.isMultiSelected = isMultiSelected;
        this.menuCanSelect = menuCanSelect;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        CheckExpandBean checkExpandBean = showList.get(position);
        myHolder.tvInfo.setText(checkExpandBean.getItemText());
        ViewGroup.LayoutParams params = myHolder.viewIndex.getLayoutParams();
        params.width = ZXSystemUtil.dp2px(30 * checkExpandBean.getIndex());
        myHolder.viewIndex.setLayoutParams(params);
        if (checkExpandBean.getChildList() == null) {
            myHolder.ivArrow.setVisibility(View.INVISIBLE);
        } else if (checkExpandBean.isShowChild()) {
            myHolder.ivArrow.setVisibility(View.VISIBLE);
            myHolder.ivArrow.setBackground(ContextCompat.getDrawable(context, R.drawable.arrow_open));
        } else {
            myHolder.ivArrow.setVisibility(View.VISIBLE);
            myHolder.ivArrow.setBackground(ContextCompat.getDrawable(context, R.drawable.arrow_close));
        }
        if (showSelect && canShowSelect(checkExpandBean)) {
            if (checkExpandBean.isSelected()) {
                myHolder.ivSelect.setVisibility(View.VISIBLE);
                myHolder.ivSelect.setBackground(ContextCompat.getDrawable(context, R.drawable.arraw_select));
            } else {
                myHolder.ivSelect.setVisibility(View.VISIBLE);
                myHolder.ivSelect.setBackground(ContextCompat.getDrawable(context, R.drawable.arraw_notselect));
            }
            if (checkExpandBean.getChildList() != null && !menuCanSelect) {
                myHolder.ivSelect.setVisibility(View.INVISIBLE);
            } else {
                myHolder.ivSelect.setVisibility(View.VISIBLE);
            }
        } else {
            myHolder.ivSelect.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvInfo;
        private View viewIndex;
        private ImageView ivArrow, ivSelect;
        private LinearLayout down, llArrow, llSelect;

        public MyHolder(View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tv_info);
            viewIndex = itemView.findViewById(R.id.view_index);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            ivSelect = itemView.findViewById(R.id.iv_select);
            llArrow = itemView.findViewById(R.id.ll_arrow);
            llSelect = itemView.findViewById(R.id.ll_select);
            down = itemView.findViewById(R.id.down);
            llArrow.setOnClickListener(v -> {
                if (expandListener != null) {
//                    expandListener.onOpenClick(getAdapterPosition());TODO 仅针对该应用作出的修改
                    expandListener.onTextClick(getAdapterPosition());
                }
            });
            llSelect.setOnClickListener(v -> {
                if (expandListener != null && ivSelect.getVisibility() == View.VISIBLE) {
                    expandListener.onSelectClick(getAdapterPosition());
                }
            });
            tvInfo.setOnClickListener(v -> {
                if (expandListener != null) {
                    expandListener.onTextClick(getAdapterPosition());
                }
            });
        }
    }


    private boolean canShowSelect(CheckExpandBean bean) {
        boolean canShowSelect = true;
        if (bean.getIndex() == 0 && bean.getChildList() != null && bean.getChildList().size() == 0) {
            canShowSelect = false;
            return canShowSelect;
        }
        if (bean.getChildList() != null) {
            for (int i = 0; i < bean.getChildList().size(); i++) {
                if (bean.getChildList().get(i).getChildList() != null) {
                    canShowSelect = false;
                    break;
                }
            }
        }
        return canShowSelect;
    }

    public void setExpandListener(OnExpandListener expandListener) {
        this.expandListener = expandListener;
    }

    public interface OnExpandListener {
        void onTextClick(int position);

        void onOpenClick(int position);

        void onSelectClick(int position);
    }

}
