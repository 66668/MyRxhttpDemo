package com.sjy.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sjy.network._interface.p.IDemoPresenter;
import com.sjy.network._interface.v.OnChangePhotoListener;
import com.sjy.network._interface.v.OnDemoListener;
import com.sjy.network.bean.BannerBean;
import com.sjy.network.bean.LoginBean;
import com.sjy.network.bean.PhotoBean;
import com.sjy.network.module.DemoModuleImpl;
import com.sjy.network.presenter.DemoPresenterImpl;
import com.sjy.network.util.MLog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * MVP框架的使用示例
 * 本实例将演示最全的表单 文件及表单，图片文件上传的使用
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnDemoListener, OnChangePhotoListener {
    private TextView tv_content;
    private Button btn_test1, btn_test2;

    //mvp框架
    private IDemoPresenter presenter;
    //登录
    private LoginBean bean;
    List<BannerBean> list;

    PhotoBean photoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_content = findViewById(R.id.tv_content);
        btn_test1 = findViewById(R.id.btn_test1);
        btn_test2 = findViewById(R.id.btn_test2);
        btn_test1.setOnClickListener(this);
        btn_test2.setOnClickListener(this);

        //初始化presenter
        presenter = new DemoPresenterImpl(this, this,this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test1:
                Toast.makeText(this, "触发接口", Toast.LENGTH_LONG).show();
//                logTest();
                bannerTest();
                break;
            case R.id.btn_test2:
                changePicTest();
                break;
        }
    }

    //================================p层调用========================================

    /**
     * 登录测试
     */
    private void logTest() {
        tv_content.setText(null);
        presenter.pLoginByPhoneAndPs("18210196639", "qqq111");
    }

    /**
     * 获取测试
     */
    private void bannerTest() {
        tv_content.setText(null);
        presenter.pBanner();
    }

    /**
     * 表单+文件
     */
    private void changePicTest() {
        tv_content.setText(null);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.about_logo);
        File file = saveBitmapFile(bitmap);
        presenter.pChangePic("hdgfabngoireahgoiang", file);//token对应用户，每30min更新一次，本人就不演示了，接口能调通，有返回说明 成功了
    }


    //=================================p层返回=======================================

    //接口回调
    //登录
    @Override
    public void onLogSuccess(Object obj) {
        bean = (LoginBean) obj;
        MLog.d("成功返回=" + bean.toString());
        tv_content.setText(bean.toString());
    }

    @Override
    public void onLogFailed(int code, String msg, Exception e) {
        MLog.d("失败返回--code=" + code + "--msg=" + msg);
        tv_content.setText("返回失败：" + code + msg);
    }

    //banner图
    @Override
    public void onLooperImgSuccess(List<BannerBean> list) {
        list = (List<BannerBean>) list;
        MLog.d("成功返回=" + list.size());
        tv_content.setText(list.size() + list.get(0).toString());
    }

    @Override
    public void onLooperImgFailed(int code, String msg, Exception e) {
        MLog.d("失败返回--code=" + code + "--msg=" + msg);
        tv_content.setText("返回失败：" + code + msg);
    }

    //图片
    @Override
    public void onChangePhoteSuccess(Object obj) {
        photoBean = (PhotoBean) obj;
        MLog.d("成功返回=" + list.size());
        tv_content.setText(bean.toString());
    }

    @Override
    public void onChangePhoteFailed(int code, String msg, Exception e) {
        MLog.d("失败返回--code=" + code + "--msg=" + msg);
        tv_content.setText("返回失败：" + code + msg);
    }

    public File saveBitmapFile(Bitmap bitmap) {
        File file = new File(this.getCacheDir()+"/011.png");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
