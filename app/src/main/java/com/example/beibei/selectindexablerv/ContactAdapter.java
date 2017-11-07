package com.example.beibei.selectindexablerv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.beibei.selectindexablerv.view.SelectIndexableRv.IndexableAdapter;

import static com.example.beibei.selectindexablerv.MainActivity.MODE_NORMAL;


/**
 * Created by YoKey on 16/10/8.
 */
public class ContactAdapter extends IndexableAdapter<UserEntity> {
    private LayoutInflater mInflater;
    private int mMode = 0;

    // 存储勾选框状态的map集合
//    private HashMap<String,Boolean> checkboxUserIdList = new HashMap<>();
    SparseBooleanArray checkboxUserIdList = new SparseBooleanArray();

    public ContactAdapter(Context context , int mode) {
        mInflater = LayoutInflater.from(context);
        mMode = mode;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, final UserEntity entity, int position) {
        final ContentVH vh = (ContentVH) holder;
        vh.tvName.setText(entity.getNick());
        vh.tvMobile.setText(entity.getMobile());
//        vh.cb.setChecked(entity.getSelect());

        if(mMode==MODE_NORMAL){
            vh.cb.setVisibility(View.GONE);
        }else {

            vh.cb.setVisibility(View.VISIBLE);
            vh.cb.setTag(position);


            vh.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int tag = (int) buttonView.getTag();

                    if (isChecked) {
                        checkboxUserIdList.put(tag, true);
                    } else {
                        checkboxUserIdList.delete(tag);
                    }
                }
            });

            vh.cb.setChecked(checkboxUserIdList.get(position, false));
        }


    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName, tvMobile;
        CheckBox cb;
        RelativeLayout rlItem;

        public ContentVH(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            cb = (CheckBox) itemView.findViewById(R.id.cb);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item_contact);
        }
    }
}
