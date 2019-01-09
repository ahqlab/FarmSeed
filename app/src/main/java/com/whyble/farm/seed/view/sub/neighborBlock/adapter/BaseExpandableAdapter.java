package com.whyble.farm.seed.view.sub.neighborBlock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.domain.neighborBlock.Block1;
import com.whyble.farm.seed.domain.neighborBlock.Block2;

import java.util.ArrayList;

public class BaseExpandableAdapter extends BaseExpandableListAdapter {

    private ArrayList<Block1> groupList = null;
    private ArrayList<ArrayList<Block2>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public BaseExpandableAdapter(Context c, ArrayList<Block1> groupList,
                                 ArrayList<ArrayList<Block2>> childList){
        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
    }

    // 그룹 포지션을 반환한다.
    @Override
    public Block1 getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.neighbor_block_listview_item, parent, false);
            viewHolder.userId = (TextView) v.findViewById(R.id.user_id);
            viewHolder.userCount = (TextView) v.findViewById(R.id.user_count);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
       /* if(isExpanded){
            viewHolder.iv_image.setBackgroundColor(Color.GREEN);
        }else{
            viewHolder.iv_image.setBackgroundColor(Color.WHITE);
        }*/
        viewHolder.userId.setText(getGroup(groupPosition).getUser_id());
        viewHolder.userCount.setText(getGroup(groupPosition).getUser_count());
        return v;
    }

    // 차일드뷰를 반환한다.
    @Override
    public Block2 getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.neighbor_neighbor_block_listview_item, null);
            viewHolder.userId = (TextView) v.findViewById(R.id.user_id);
            viewHolder.userCount = (TextView) v.findViewById(R.id.user_count);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.userId.setText(getChild(groupPosition, childPosition).getUser_id2());
        //count 가 없음...
        viewHolder.userCount.setText("0");
        return v;
    }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {
        public TextView userId;
        public TextView userCount;
    }
}

