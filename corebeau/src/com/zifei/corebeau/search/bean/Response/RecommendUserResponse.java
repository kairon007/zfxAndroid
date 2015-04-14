package com.zifei.corebeau.search.bean.Response;

import com.zifei.corebeau.common.net.Response;
import com.zifei.corebeau.search.bean.RecommendUserList;

import java.util.List;

/**
 * Created by im14s_000 on 2015/4/2.
 */
public class RecommendUserResponse extends Response {

    private List<RecommendUserList> recommendUserList;

    public List<RecommendUserList> getRecommendUserList() {
        return recommendUserList;
    }

    public void setRecommendUserList(List<RecommendUserList> recommendUserList) {
        this.recommendUserList = recommendUserList;
    }
}
