package com.zx.module_supervise.module.daily.func.view.ExpandableView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Xiangb on 2017/12/4.
 * 功能：
 */

public class CheckRecyclerHelper {

    private Context context;
    private RecyclerView recyclerView;
    private CheckExpandAdapter adapter;
    private boolean showSelect = true;
    private boolean isMultiSelected = false;
    private List<CheckExpandBean> dataList = new ArrayList<>();
    private List<CheckExpandBean> showList = new ArrayList<>();
    private CheckExpandItemListener itemClickListener;
    private boolean showMenuSelectView = false;
    private boolean selectChild = true;
    private boolean menuCanSelect = true;
    private CheckExpandAdapter.OnExpandListener expandListener;

    public static CheckRecyclerHelper getInstance(Context context) {
        CheckRecyclerHelper zxCheckRecyclerHelper = new CheckRecyclerHelper(context);
        return zxCheckRecyclerHelper;
    }

    public CheckRecyclerHelper(Context context) {
        this.context = context;
    }

    public CheckRecyclerHelper withRecycler(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    //刷新
    private void setSelect(List<CheckExpandBean> dataList, boolean select) {
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setSelected(select);
            if (dataList.get(i).getChildList() != null && dataList.get(i).getChildList().size() > 0 && dataList.get(i).isShowChild()) {
                setSelect(dataList.get(i).getChildList(), select);
            }
        }
    }

    //刷新
    private void refresh(List<CheckExpandBean> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            showList.add(dataList.get(i));
            if (dataList.get(i).getChildList() != null && dataList.get(i).getChildList().size() > 0 && dataList.get(i).isShowChild()) {
                refresh(dataList.get(i).getChildList());
            }
        }
    }

    //根据id寻找位置
    private void setShowById(List<CheckExpandBean> dataList, String id) {
        for (int i = 0; i < dataList.size(); i++) {
            if (id.equals(dataList.get(i).getId())) {
                dataList.get(i).setShowChild(!dataList.get(i).isShowChild());
                //如果关闭item，就关闭它的所有子集
                if (!dataList.get(i).isShowChild()) {
                    closeChild(dataList.get(i));
                }
                return;
            }
            if (dataList.get(i).getChildList() != null && dataList.get(i).getChildList().size() > 0) {
                setShowById(dataList.get(i).getChildList(), id);
            }
        }
    }

    //关闭子集
    private void closeChild(CheckExpandBean zxCheckExpandBean) {
        if (zxCheckExpandBean.getChildList() == null) {
            return;
        }
        for (int i = 0; i < zxCheckExpandBean.getChildList().size(); i++) {
            zxCheckExpandBean.getChildList().get(i).setShowChild(false);
            if (zxCheckExpandBean.getChildList().get(i).getChildList() != null && zxCheckExpandBean.getChildList().get(i).getChildList().size() > 0) {
                closeChild(zxCheckExpandBean.getChildList().get(i));
            }
        }
    }

    //设置数据
    public CheckRecyclerHelper setData(List<CheckExpandBean> dataList) {
        this.dataList = dataList;
        showList.clear();
        setIndex(this.dataList, 0);
        refresh(dataList);
        return this;
    }

    //循环设置层级
    private void setIndex(List<CheckExpandBean> dataList, int index) {
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setIndex(index);
            String uuid = UUID.randomUUID().toString();
            dataList.get(i).setId(uuid);
//          dataList.get(i).setShowChild(false);
            if (dataList.get(i).getChildList() != null && dataList.get(i).getChildList().size() > 0) {
                int item = index + 1;
                setIndex(dataList.get(i).getChildList(), item);
            }
        }
    }

    //是否可以多选
    public CheckRecyclerHelper showSelect(boolean show, boolean isMultiSelected) {
        this.showSelect = show;
        this.isMultiSelected = isMultiSelected;
        return this;
    }

    //设置item的点击事件（非菜单）
    public CheckRecyclerHelper setItemClickListener(CheckExpandItemListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

//    //是否显示菜单的选择栏
//    public ZXExpandRecyclerHelper showMenuSelectView(boolean showMenuSelectView) {
//        this.showMenuSelectView = showMenuSelectView;
//        return this;
//    }

    //构建
    public void build() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CheckExpandAdapter(context, showList, showSelect, isMultiSelected, menuCanSelect);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        expandListener = new CheckExpandAdapter.OnExpandListener() {
            @Override
            public void onTextClick(int position) {
                try {
                    String id = showList.get(position).getId();
                    if (id != null && id.length() > 0) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClick(showList.get(position), position);//数据点击事件
                        } else {
                            onOpenClick(position);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onOpenClick(int position) {
                try {
                    String id = showList.get(position).getId();
                    if (id != null && id.length() > 0) {
                        setShowById(dataList, id);
                        showList.clear();
                        refresh(dataList);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSelectClick(int position) {
                try {
                    String id = showList.get(position).getId();
                    if (id != null && id.length() > 0) {
                        if (!isMultiSelected) {
                            setSelect(dataList, false);
                        } else {
                            if (showList.get(position).getChildList() != null && showList.get(position).getChildList().size() > 0) {
                                setSelect(showList.get(position).getChildList(), !showList.get(position).isSelected());
                            }
                        }
                        showList.get(position).setSelected(!showList.get(position).isSelected());
                        showList.clear();
                        refresh(dataList);
                        adapter.notifyDataSetChanged();
                        if (itemClickListener != null) {
                            itemClickListener.onItemSelect(showList.get(position), position);//数据选择事件
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        adapter.setExpandListener(expandListener);
    }

    public void changeOpenStatus(int showPosition) {
        if (expandListener != null) {
            expandListener.onOpenClick(showPosition);
        }
    }

    public void cahngeSelectStatus(int showPosition) {
        if (expandListener != null) {
            expandListener.onSelectClick(showPosition);
        }
    }

    public CheckRecyclerHelper setChildSelectWhenMenuSelect(boolean selectChild) {
        this.selectChild = selectChild;
        return this;
    }

    public CheckRecyclerHelper setMenuCanSelect(boolean canSelect) {
        this.menuCanSelect = canSelect;
        return this;
    }

    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

}
