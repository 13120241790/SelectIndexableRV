package com.example.beibei.selectindexablerv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beibei.selectindexablerv.view.SelectIndexableRv.IndexableAdapter;
import com.example.beibei.selectindexablerv.view.SelectIndexableRv.IndexableHeaderAdapter;
import com.example.beibei.selectindexablerv.view.SelectIndexableRv.IndexableLayout;
import com.example.beibei.selectindexablerv.view.SelectIndexableRv.SimpleFooterAdapter;
import com.example.beibei.selectindexablerv.view.SelectIndexableRv.SimpleHeaderAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.beibei.selectindexablerv.MainActivity.MODE_NORMAL;
import static com.example.beibei.selectindexablerv.MainActivity.MODE_SELECT;

/**
 * Created by YoKey on 16/10/8.
 */
public class PickContactActivity extends AppCompatActivity {
    private ContactAdapter mAdapter;
    private MenuHeaderAdapter mMenuHeaderAdapter;
    private BannerHeaderAdapter mBannerHeaderAdapter;
    private ArrayList<UserEntity> mSelectContact = new ArrayList<>();

    private int mMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);
        getSupportActionBar().setTitle("联系人");

        mMode = getIntent().getIntExtra("mode", 0);

        IndexableLayout indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);

        //设置粘性（ABC等字母在屏幕顶部悬停）
        indexableLayout.setStickyEnable(false);
        //设置Material Design OverlayView
        //indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
        //设置IOS风格的 OverlayView
        indexableLayout.setOverlayStyle_Center();


        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContactAdapter(this, mMode);
        indexableLayout.setAdapter(mAdapter);
        mAdapter.setDatas(initDatas());

        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);

        if (mMode == MODE_NORMAL) {
            indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter<>(mAdapter, "", "我的服务专员", initFavDatas()));
        }


        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {
                if(mMode == MODE_NORMAL){
                    // TODO: 2017/10/15
                    Toast.makeText(PickContactActivity.this,"跳页面",Toast.LENGTH_SHORT).show();
                }else {
                    if (originalPosition >= 0) {
                        RelativeLayout R = (RelativeLayout) v;
                        int childCount = R.getChildCount();
                        Log.d("===========", childCount + "");
                        CheckBox checkBox = (CheckBox) R.getChildAt(3);
                        checkBox.setChecked(!checkBox.isChecked());

                        if (checkBox.isChecked()) {
                            mSelectContact.add(entity);
                        } else {
                            mSelectContact.remove(entity);
                        }

                        ToastUtil.showShort(PickContactActivity.this, "选中:" + entity.getNick() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
                    } else {
                        //doSomething
                        ToastUtil.showShort(PickContactActivity.this, "选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);
                    }
                }
            }
        });

        mAdapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
            @Override
            public void onItemClick(View v, int currentPosition, String indexTitle) {
                ToastUtil.showShort(PickContactActivity.this, "选中:" + indexTitle + "  当前位置:" + currentPosition);
            }
        });



        // 添加我关心的人
//        // 添加我关心的人
//        indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter<>(mAdapter, "☆", "我关心的", initFavDatas()));
//
//        // 构造函数里3个参数,分别对应 (IndexBar的字母索引, IndexTitle, 数据源), 不想显示哪个就传null, 数据源传null时,代表add一个普通的View
//        mMenuHeaderAdapter = new MenuHeaderAdapter("↑", null, initMenuDatas());
//        // 添加菜单
//        indexableLayout.addHeaderAdapter(mMenuHeaderAdapter);
//        mMenuHeaderAdapter.setOnItemHeaderClickListener(new IndexableHeaderAdapter.OnItemHeaderClickListener<MenuEntity>() {
//            @Override
//            public void onItemClick(View v, int currentPosition, MenuEntity entity) {
//                ToastUtil.showShort(PickContactActivity.this, entity.getMenuTitle());
//            }
//        });
//
//        // 这里BannerView只有一个Item, 添加一个长度为1的任意List作为第三个参数
//        List<String> bannerList = new ArrayList<>();
//        bannerList.add("");
//        mBannerHeaderAdapter = new BannerHeaderAdapter(null, null, bannerList);
//        // 添加 Banner
//        indexableLayout.addHeaderAdapter(mBannerHeaderAdapter);
//
//        // FooterView
        indexableLayout.addFooterAdapter(new SimpleFooterAdapter<>(mAdapter, "尾", "我是FooterView", initFavDatas()));
    }

    /**
     * 自定义的MenuHeader
     */
    class MenuHeaderAdapter extends IndexableHeaderAdapter<MenuEntity> {
        private static final int TYPE = 1;

        public MenuHeaderAdapter(String index, String indexTitle, List<MenuEntity> datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            return new VH(LayoutInflater.from(PickContactActivity.this).inflate(R.layout.header_contact_menu, parent, false));
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, MenuEntity entity, int position) {
            VH vh = (VH) holder;
            vh.tv.setText(entity.getMenuTitle());
            vh.img.setImageResource(entity.getMenuIconRes());
        }

        private class VH extends RecyclerView.ViewHolder {
            private TextView tv;
            private ImageView img;

            public VH(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv_title);
                img = (ImageView) itemView.findViewById(R.id.img);
            }
        }
    }

    /**
     * 自定义的Banner Header
     */
    class BannerHeaderAdapter extends IndexableHeaderAdapter {
        private static final int TYPE = 2;

        public BannerHeaderAdapter(String index, String indexTitle, List datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(PickContactActivity.this).inflate(R.layout.header_contact_banner, parent, false);
            VH holder = new VH(view);
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showShort(PickContactActivity.this, "---点击了Banner---");


                    if (mMode == 0) {
                        mMode = MODE_SELECT;
                    } else {
                        mMode = MODE_NORMAL;
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity, int position) {
            // 数据源为null时, 该方法不用实现
        }

        private class VH extends RecyclerView.ViewHolder {
            ImageView img;

            public VH(View itemView) {
                super(itemView);
                img = (ImageView) itemView.findViewById(R.id.img);
            }
        }
    }

    private List<UserEntity> initDatas() {
        List<UserEntity> list = new ArrayList<>();
        // 初始化数据
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.contact_array));
        List<String> mobileStrings = Arrays.asList(getResources().getStringArray(R.array.mobile_array));
        for (int i = 0; i < contactStrings.size(); i++) {
            UserEntity contactEntity = new UserEntity(contactStrings.get(i), mobileStrings.get(i));
            list.add(contactEntity);
        }

        return list;
    }

    private List<UserEntity> initFavDatas() {
        List<UserEntity> list = new ArrayList<>();
        list.add(new UserEntity("张三", "10000"));
        return list;
    }

    private List<MenuEntity> initMenuDatas() {
        List<MenuEntity> list = new ArrayList<>();
        list.add(new MenuEntity("新的朋友", R.mipmap.icon_1));
        list.add(new MenuEntity("群聊", R.mipmap.icon_2));
//        list.add(new MenuEntity("标签", R.mipmap.icon_3));
//        list.add(new MenuEntity("公众号", R.mipmap.icon_4));
        return list;
    }
}
