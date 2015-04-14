package com.zifei.corebeau.spot.bean.response;

import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.spot.bean.SpotList;

import java.util.List;

/**
 * Created by im14s_000 on 2015/3/28.
 */
public class SpotListResponse extends Response {

    private List<SpotList> spotPostList;

    public List<SpotList> getSpotPostList() {
        return spotPostList;
    }

    public void setSpotPostList(List<SpotList> spotPostList) {
        this.spotPostList = spotPostList;
    }
}
