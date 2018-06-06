package com.sjy.network.http;


import com.sjy.network.bean.BannerBean;
import com.sjy.network.bean.CommonBean;
import com.sjy.network.bean.CommonListBean;
import com.sjy.network.bean.LoginBean;
import com.sjy.network.bean.PhotoBean;
import com.sjy.network.bean.TextBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by jingbin on 16/11/21.
 * Rxjava+retrofit的封装，用于调用详细接口信息
 * Rxjava的响应式流程+retrofit的注解机制
 * 具体的操作，需要看MVP下的M层操作
 * <p>
 * Observable引用：import rx.Observable
 */

public interface MyHttpService {
    /**
     ********************************************--构建不同的网络框架，满足不同的接口需求--********************************************************
     */

    /**
     * （1）retrofit+okhttp+Rxjava默认构建样式（没有复杂的样式要求）
     */
    class Builder {
        public static MyHttpService getHttpServer() {
            return HttpUtils.getInstance().getDefaultServer(MyHttpService.class);
        }
    }

    /**
     * （2）retrofit+okhttp+Rxjava样式+接口要求带token验证
     */
    class TokenBuilder {
        public static MyHttpService getHttpServer() {
            return HttpUtils.getInstance().getTokenServer(MyHttpService.class);
        }
    }

    /**
     * （3）retrofit+okhttp+Rxjava样式+特殊缓存接口
     */
    class CacheBuilder {
        public static MyHttpService getHttpServer() {
            return HttpUtils.getInstance().getHeaderServer(MyHttpService.class);
        }
    }

    /**
     * （4）retrofit+okhttp+Rxjava样式+特殊缓存接口
     */
    class HeaderBuilder {
        public static MyHttpService getHttpServer() {
            return HttpUtils.getInstance().getHeaderServer(MyHttpService.class);
        }
    }
    //...需要的样式 灵活添加


    /**
     ********************************************--接口相关--********************************************************
     */


    /**
     * 01 登录为例，表单的使用方式，post
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.LOGIN)
    Observable<CommonBean<LoginBean>> postLogin(
            @Field("phone") String phone
            , @Field("password") String password);


    /**
     * 01轮播图 表单的使用方式，post
     *
     * @param city_name 默认全国
     * @return 200 404
     */

    @FormUrlEncoded
    @POST(URLUtils.Home.BANNER)
    Observable<CommonListBean<BannerBean>> getBanner(
            @Field("city_name") String city_name
            , @Field("device") int device);

    /**
     * 02 头像上传/修改头像
     * 图片/文件 上传
     *
     * @return
     */
    @Multipart
    @POST(URLUtils.Mine.CHANGENAME_PHOTO)
    Observable<CommonBean<PhotoBean>> changePhotoPost(
            @Part List<MultipartBody.Part> list);

    /**
     * 03获取省市区数据
     */
    @GET(URLUtils.InstitutionOrFilter.AREA)
    Observable<TextBean> getInstitutionAreaData();


    /**
     * 02 机构评论 发表评论 (有图上传)
     *
     * @return
     */
    @Multipart
    @POST(URLUtils.InstitutionDetail.COMMENT_INSTITUTE_CREATE)
    Observable<CommonListBean<TextBean>> postCommentWithPic(
            @Part("id") int id,
            @Part("order_id") int order_id,
            @Part("token") String token,
            @Part("agency_score") int agency_score,
            @Part("service_score") int servoce_score,
            @Part("content") String content,
            @Part("type") int type,
            @Part MultipartBody.Part file
    );


    /**
     * 微信授权
     *
     * @return
     */
    @FormUrlEncoded
    @POST(URLUtils.Mine.WEICHAT_LOG)
    Observable<CommonBean<TextBean>> postWeichatMessage(
            @Field("nickname") String nickname
            , @Field("openid") String openid
            , @Field("headimgurl") String headimgurl
            , @Field("unionid") String unionid
            , @Field("device") int device);

    //    /**
    //     * 添加访客
    //     * <p>
    //     * 文本和图片上传
    //     * post
    //     *
    //     * @return
    //     */
    //    @Multipart
    //    @POST(URLUtils.ADD_VISITOR)
    //    Observable<BaseBean> addVisitor(
    //            @Part("obj") String obj
    //            , @Part MultipartBody.Part file);
}