package com.example.lianchuang_hw.jsonbaseadapter;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    private List<Map<String,Object>> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);
    }
    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object>map = new HashMap<String,Object>();
        InputStream inputStream;

        try{
            inputStream = this.getAssets().open("my_home_friends");
            String json = readTextFile(inputStream);
            JSONArray array = new JSONArray(json);
            for(int i = 0;i<array.length();i++){
                map = new HashMap<String,Object>();
                map.put("name",array.getJSONObject(i).getString("name"));
                map.put("info",array.getJSONObject(i).getString("info"));
                map.put("img",array.getJSONObject(i).getString("photo"));
                list.add(map);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }

    public final class ViewHolder{
        public ImageView img;
        public TextView name;
        public  TextView info;
    }

    public class MyAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return mData.size();
        }

        @Override
        public Object getItem(int arg0){
            return null;
        }

        @Override
        public long getItemId(int arg0){
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.vlist,null);
                holder.img = (ImageView)convertView.findViewById(R.id.img);
                holder.name =(TextView) convertView.findViewById(R.id.name);
                holder.info = (TextView) convertView.findViewById(R.id.info);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.img.setImageBitmap(getHome((String)mData.get(position).get("img")));
            holder.name.setText((String)mData.get(position).get("name"));
            holder.info.setText((String)mData.get(position).get("info"));
            return convertView;
        }
    }

    public Bitmap getHome(String photo){
        String homeName = photo+".jpg";
        InputStream is = null;
        try{
           is = getAssets().open("home/"+homeName);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String readTextFile(InputStream inputStream){
        String readedStr = "";
        BufferedReader br;
        try{
            br = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String tmp;
            while ((tmp = br.readLine())!= null){
                readedStr += tmp;
            }
            br.close();
            inputStream.close();

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return readedStr;
    }

}
