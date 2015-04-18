package com.zifei.corebeau.search.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zifei.corebeau.R;
import com.zifei.corebeau.common.ui.widget.staggered.StaggeredGridView;
import com.zifei.corebeau.search.bean.RecommendPostList;
import com.zifei.corebeau.utils.StringUtil;

/**
 * Created by im14s_000 on 2015/4/2.
 */
public class RecommedPostAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<RecommendPostList> data = null;
    private DisplayImageOptions imageOptions;
    private ImageLoader imageLoader;
    private ImageLoaderConfiguration config;

    public RecommedPostAdapter(Context context, StaggeredGridView listView) {
        inflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
        config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(3).build();
        imageLoader.init(config);
        
        imageOptions = new DisplayImageOptions.Builder() //
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();
    }

    public void addData(List<RecommendPostList> data, boolean append) {
        if (append) {
            this.data.addAll(data);
        } else {
            this.data = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
    	if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public Object getItem(int position) {
    	if (data == null) {
            return null;
        }
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_search_post, null);
            holder = new ViewHolder();
//            holder.usericon = (CircularImageView) convertView.findViewById(R.id.civ_search_user_recommend);
//            holder.nickName = (TextView) convertView.findViewById(R.id.spot_user_nickname);
            holder.image = (ImageView)convertView.findViewById(R.id.iv_search_post);
            convertView.setTag(R.layout.item_search_post, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.layout.item_search_post);
        }

        final RecommendPostList p = data.get(position);

        String urlThumb = p.getPicThumbUrl();
        if (!StringUtil.isEmpty(urlThumb)) {
            imageLoader.displayImage(urlThumb, holder.image, imageOptions);
        } else {
        }
//        holder.nickName.setText(p.getNickName());
        convertView.setTag(position);
        return convertView;
    }

    private class ViewHolder {
        TextView nickName;
        ImageView image;
    }

}
