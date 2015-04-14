package com.zifei.corebeau.test;

import com.zifei.corebeau.post.bean.Comment;
import com.zifei.corebeau.post.bean.Post;
import com.zifei.corebeau.search.bean.RecommendPostList;
import com.zifei.corebeau.search.bean.RecommendUserList;
import com.zifei.corebeau.spot.bean.SpotList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by im14s_000 on 2015/3/31.
 */
public class TestData {

    public static List<SpotList> getSpotList(){

        ArrayList<SpotList> list = new ArrayList<SpotList>();
        SpotList list0 = new SpotList();
        list0.setPostId(0);
        list0.setUserId(0);
        list0.setUserNickname("zebra");
        list0.setMessage("this is hen hao yifu duiduiduiduiduidui" +
                "ahahahahahah");
        list0.setPic("http://e1.vingle.net/t_ca_xl/pov2p85puxn26hsavykh.jpg");
        list0.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        list.add(list0);

        SpotList list1 = new SpotList();
        list1.setPostId(1);
        list1.setUserId(0);
        list1.setUserNickname("zebra");
        list1.setMessage("today my look");
        list1.setPic("http://e1.vingle.net/pmjfnxfkuwfwtvgb5m3s");
        list1.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        list.add(list1);

        SpotList list2 = new SpotList();
        list2.setPostId(1);
        list2.setUserId(0);
        list2.setUserNickname("zebra");
        list2.setMessage("today my look");
        list2.setPic("http://e1.vingle.net/pmjfnxfkuwfwtvgb5m3s");
        list2.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        list.add(list2);

        SpotList list3 = new SpotList();
        list3.setPostId(1);
        list3.setUserId(0);
        list3.setUserNickname("zebra");
        list3.setMessage("today my look");
        list3.setPic("http://e1.vingle.net/pmjfnxfkuwfwtvgb5m3s");
        list3.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        list.add(list3);


        return list;
    }
    
    public static List<String> getPostImages(){
    	ArrayList<String> picList = new ArrayList<String>();
        picList.add("http://e1.vingle.net/pmjfnxfkuwfwtvgb5m3s");
        
        picList.add("http://e1.vingle.net/t_ca_xl/aiica28ptmovaid1g1j7.jpg");
        
        picList.add("http://e1.vingle.net/t_ca_xl/bhvfa9wtb6sjbinnzz2g.jpg");
        picList.add("http://e1.vingle.net/t_ca_xl/mghlctvsxdep1oh5dv5p.jpg");
        picList.add("http://e1.vingle.net/t_ca_xl/h3t1sdj903oevpovb1q6.jpg");
		return picList;
    }

    public static Post getPost(){
        Post post = new Post();
        post.setPostId(1);
        post.setUserId(1);
        post.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        post.setUserNickname("zebra");
        post.setMessage("today my look");
        ArrayList<String> picList = new ArrayList<String>();
        picList.add("http://e1.vingle.net/pmjfnxfkuwfwtvgb5m3s");
        picList.add("http://e1.vingle.net/t_ca_xl/mghlctvsxdep1oh5dv5p.jpg");
        picList.add("http://e1.vingle.net/t_ca_xl/aiica28ptmovaid1g1j7.jpg");
        picList.add("http://e1.vingle.net/t_ca_xl/h3t1sdj903oevpovb1q6.jpg");
        picList.add("http://e1.vingle.net/t_ca_xl/bhvfa9wtb6sjbinnzz2g.jpg");
        post.setPic(picList);


//        post.setPicThumb();
        ArrayList<String> picSubList = new ArrayList<String>();
        picSubList.add("http://e1.vingle.net/pmjfnxfkuwfwtvgb5m3s");
        picSubList.add("http://e1.vingle.net/t_ca_xl/mghlctvsxdep1oh5dv5p.jpg");
        picSubList.add("http://e1.vingle.net/t_ca_xl/aiica28ptmovaid1g1j7.jpg");
        picSubList.add("http://e1.vingle.net/t_ca_xl/h3t1sdj903oevpovb1q6.jpg");
        picSubList.add("http://e1.vingle.net/t_ca_xl/bhvfa9wtb6sjbinnzz2g.jpg");
        post.setPicSub(picSubList);


        post.setCountLike(215);
        post.setCountHit(1236);
        post.setCountComment(43);
        post.setVisible(Post.POST_VISIVLE);
        post.setRepliable(Post.POST_REPLIABLE);
        post.setDate("2015-1-18");
        post.setHashTag("");
        return post;
    }

    public static List<Comment>  getCommentList(){
        ArrayList<Comment> list = new ArrayList<Comment>();

        Comment comment0 = new Comment();
        comment0.setPostId(1);
        comment0.setCommentId(0);
        comment0.setUserId(1);
        comment0.setUserNickname("xifei");
        comment0.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        comment0.setMessage("goooooooooooood!");
        list.add(comment0);

        Comment comment1 = new Comment();
        comment1.setPostId(1);
        comment1.setCommentId(0);
        comment1.setUserId(1);
        comment1.setUserNickname("xifei");
        comment1.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        comment1.setMessage("goooooooooooood!");
        list.add(comment1);

        Comment comment2 = new Comment();
        comment2.setPostId(1);
        comment2.setCommentId(0);
        comment2.setUserId(1);
        comment2.setUserNickname("xifei");
        comment2.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        comment2.setMessage("goooooooooooood!");
        list.add(comment2);

        Comment comment3 = new Comment();
        comment3.setPostId(1);
        comment3.setCommentId(0);
        comment3.setUserId(1);
        comment3.setUserNickname("xifei");
        comment3.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        comment3.setMessage("goooooooooooood!");
        list.add(comment3);

        Comment comment4 = new Comment();
        comment4.setPostId(1);
        comment4.setCommentId(0);
        comment4.setUserId(1);
        comment4.setUserNickname("xifei");
        comment4.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        comment4.setMessage("goooooooooooood!");
        list.add(comment4);

        Comment comment5 = new Comment();
        comment5.setPostId(1);
        comment5.setCommentId(0);
        comment5.setUserId(1);
        comment5.setUserNickname("xifei");
        comment5.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        comment5.setMessage("goooooooooooood!");
        list.add(comment5);

        Comment comment6 = new Comment();
        comment6.setPostId(1);
        comment6.setCommentId(0);
        comment6.setUserId(1);
        comment6.setUserNickname("xifei");
        comment6.setUserIcon("http://e0.vingle.net/t_us_m/opdr3ab94hxosfpwyl2x");
        comment6.setMessage("goooooooooooood!");
        list.add(comment6);
        return list;
    }


    /////////////////////////////////////////////////////

    public static List<RecommendUserList>  getRecommendUserList() {
        ArrayList<RecommendUserList> list = new ArrayList<RecommendUserList>();
        RecommendUserList list0 = new RecommendUserList();
        list0.setUserId(43);
        list0.setNickName("daad12d");
        list0.setPicThumbUrl("http://dbscthumb.phinf.naver.net/0727_000_20/20150203193533185_J4NXDP97U.png/1374_14229581515.png?type=appG1");
        list.add(list0);

        RecommendUserList list6 = new RecommendUserList();
        list6.setUserId(573);
        list6.setNickName("zebra");
        list6.setPicThumbUrl("http://dbscthumb.phinf.naver.net/0727_000_22/20150314100549505_AY3XAL86W.png/25682_1426076163.png?type=appG1");
        list.add(list6);

        RecommendUserList list1 = new RecommendUserList();
        list1.setUserId(234);
        list1.setNickName("zzzzzqq");
        list1.setPicThumbUrl("http://imgnews.naver.net/image/origin/5270/2015/04/02/175193.jpg?type=nf80_80");
        list.add(list1);

        RecommendUserList list2 = new RecommendUserList();
        list2.setUserId(222);
        list2.setNickName("fuckkk");
        list2.setPicThumbUrl("http://www.edumobile.org/android/wp-content/uploads/2012/08/thumb-150x150.png");
        list.add(list2);

        RecommendUserList list3 = new RecommendUserList();
        list3.setUserId(869);
        list3.setNickName("lll1121");
        list3.setPicThumbUrl("http://www.edumobile.org/kitkatsmallbox.jpg");
        list.add(list3);

        RecommendUserList list4 = new RecommendUserList();
        list4.setUserId(886);
        list4.setNickName("iiyyg3");
        list4.setPicThumbUrl("http://a.disquscdn.com/uploads/users/9254/4221/avatar92.jpg?1408324906");
        list.add(list4);

        RecommendUserList list5 = new RecommendUserList();
        list5.setUserId(44367);
        list5.setNickName("vvvgr66");
        list5.setPicThumbUrl("http://a.disquscdn.com/uploads/users/12043/9068/avatar92.jpg?1409636527");
        list.add(list5);

        return list;
    }

    public static ArrayList<RecommendPostList>  getRecommendPostList() {

        ArrayList<RecommendPostList> list = new ArrayList<RecommendPostList>();
        RecommendPostList list0 = new RecommendPostList();
        list0.setPostId(457);
        list0.setCommentCount(123);
        list0.setLikeCount(64);
        list0.setMessage("retertbrb" +
                "retbewrtb" +
                "yrbtye");
        list0.setPicThumbUrl("http://e1.vingle.net/t_ca_xl/mghlctvsxdep1oh5dv5p.jpg");
        list.add(list0);

        RecommendPostList list1 = new RecommendPostList();
        list1.setPostId(235);
        list1.setCommentCount(222);
        list1.setLikeCount(445);
        list1.setMessage("yttt  ttte ggg");
        list1.setPicThumbUrl("http://e1.vingle.net/t_ca_xl/aiica28ptmovaid1g1j7.jpg");
        list.add(list1);

        RecommendPostList list2 = new RecommendPostList();
        list2.setPostId(47443);
        list2.setCommentCount(878);
        list2.setLikeCount(46);
        list2.setMessage(" yyyyy wwww  aaa gogogogogo");
        list2.setPicThumbUrl("http://e1.vingle.net/t_ca_xl/h3t1sdj903oevpovb1q6.jpg");
        list.add(list2);

        RecommendPostList list3 = new RecommendPostList();
        list3.setPostId(23425);
        list3.setCommentCount(4554);
        list3.setLikeCount(234);
        list3.setMessage(" fuckfuckfuckfuckfuckfu");
        list3.setPicThumbUrl("http://e1.vingle.net/t_ca_xl/bhvfa9wtb6sjbinnzz2g.jpg");
        list.add(list3);

        return list;
    }
}
