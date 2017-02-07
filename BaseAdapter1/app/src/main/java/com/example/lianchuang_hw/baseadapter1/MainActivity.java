package com.example.lianchuang_hw.baseadapter1;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.vlist);
        MyAdatper adatper = new MyAdatper(this,getData());
        setListAdapter(adatper);
    }

    private List<Map<String,Object>>getData(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("title","G1");
        map.put("info","google 1");
        map.put("img",R.drawable.i1);
        list.add(map);

        map  = new HashMap<String,Object>();
        map.put("title","G2");
        map.put("info","google 2");
        map.put("img",R.drawable.i2);
        list.add(map);

        map = new HashMap<String,Object>();
        map.put("title","G3");
        map.put("info","google 3");
        map.put("img",R.drawable.i3);
        list.add(map);

        return list;
    }

    public void showInfo(){
        new AlertDialog.Builder(this)
                .setTitle("我的listview")
                .setMessage("介绍。。。")
                .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){

                    }
                })
                .show();
    }

    //ViewHolder静态类
    public final class ViewHolder{
        public ImageView img;
        public TextView title;
        public TextView info;
        public Button viewBtn;
    }

    public class MyAdatper extends BaseAdapter{
        private LayoutInflater mInflater;
        private List<Map<String,Object>> mData;
        public MyAdatper(Context context, List<Map<String,Object>> mData){

            //根据context上下文加载布局
            this.mInflater = LayoutInflater.from(context);
            //将传入的数据保存在mData中
             this.mData = mData;

        }

        @Override
        public int getCount(){
            return mData.size();
        }

        @Override
        public Object getItem(int position){
            return position;
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        //获取一个在数据集中指定索引的视图来显示数据
        public View getView(int position,View convertView,ViewGroup parent){
            ViewHolder holder = null;
            //如果缓存convertView为空，则需要创建view
            if(convertView == null){
                //自定义的一个类用来缓存convertView
                holder = new ViewHolder();
                //根据自定义的item布局加载布局
                convertView  = mInflater.inflate(R.layout.vlist,null);
                holder.img = (ImageView)convertView.findViewById(R.id.img);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.info = (TextView)convertView.findViewById(R.id.info);
                holder.viewBtn = (Button)convertView.findViewById(R.id.btn);
                //将设置好的布局保存道缓存中，并将其设置在Tag里
                convertView.setTag(holder);

            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.img.setBackgroundResource((Integer) mData.get(position).get("img"));
            holder.title.setText((String)mData.get(position).get("title"));
            holder.info.setText((String)mData.get(position).get("info"));

            holder.viewBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    showInfo();
                }

            });

            return convertView;
        }


    }
}
