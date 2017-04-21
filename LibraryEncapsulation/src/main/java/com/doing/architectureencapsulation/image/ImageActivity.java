package com.doing.architectureencapsulation.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.doing.architectureencapsulation.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;

import static android.R.attr.id;

public class    ImageActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "ImageActivity";

    public static final String IMAGE_URL = "http://www.fzlqqqm.com/uploads/allimg/20150724/201507242218455783.jpg";


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_DEFAUTL, MODE_UIL, MODE_FRESCO, MODE_NETWORK})
    public @interface UrlMode {
    }

    public static final int MODE_DEFAUTL = 0;
    public static final int MODE_FRESCO = 1;
    public static final int MODE_UIL = 2;
    public static final int MODE_NETWORK = 3;

    @BindView(R.id.activity_iv_imageloader)
    protected ImageView mActivityIvImageloader;
    @BindView(R.id.activity_btn_imageloader)
    protected Button mActivityBtnImageloader;
    @BindView(R.id.activity_iv_picasso)
    protected ImageView mActivityIvPicasso;
    @BindView(R.id.activity_btn_picasso)
    protected Button mActivityBtnPicasso;
    @BindView(R.id.activity_iv_glide)
    protected ImageView mActivityIvGlide;
    @BindView(R.id.activity_btn_glide)
    protected Button mActivityBtnGlide;
    @BindView(R.id.activity_iv_fresco)
    protected SimpleDraweeView mActivityIvFresco;
    @BindView(R.id.activity_btn_fresco)
    protected Button mActivityBtnFresco;
    @BindView(R.id.activity_cb_resource)
    protected CheckBox mActivityCbResource;
    @BindView(R.id.activity_cb_network)
    protected CheckBox mActivityCbNetwork;
    @BindView(R.id.activity_iv_center)
    protected ImageView mActivityIvCenter;
    private ImageLoader mImageLoader;
    private Unbinder mBind;

    public static void start(Context context) {
        Fresco.initialize(context);
        context.startActivity(new Intent(context, ImageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mBind = ButterKnife.bind(this);

        mActivityBtnImageloader.setOnClickListener(this);
        mActivityBtnPicasso.setOnClickListener(this);
        mActivityBtnGlide.setOnClickListener(this);
        mActivityBtnFresco.setOnClickListener(this);

        Observable.combineLatest(RxCompoundButton.checkedChanges(mActivityCbNetwork),
                RxCompoundButton.checkedChanges(mActivityCbResource), new Func2<Boolean, Boolean, CompoundButton>() {

                    private int flag;

                    @Override
                    public CompoundButton call(Boolean cbNetWork, Boolean cbRecouce) {
                        if (cbRecouce && !cbNetWork) {
                            flag = 0;
                        } else if (!cbRecouce && cbNetWork) {
                            flag = 1;
                        } else {
                            return flag == 0 ? mActivityCbResource : mActivityCbNetwork;
                        }
                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CompoundButton>() {
                    @Override
                    public void call(CompoundButton compoundButton) {
                        if (compoundButton != null) {
                            compoundButton.setChecked(!compoundButton.isChecked());
                        }
                    }
                });


        Picasso.with(this).load(IMAGE_URL).into(mActivityIvCenter);
    }

    @Override
    protected void onDestroy() {
        if (mBind != null) {
            mBind.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_btn_imageloader:
                setImageWithUIL(mActivityIvImageloader, getUrl(MODE_UIL));
                break;

            case R.id.activity_btn_picasso:
                setImageWithPicasso(mActivityIvPicasso, getUrl(MODE_DEFAUTL));
                break;

            case R.id.activity_btn_glide:
                setImageWithGlide(mActivityIvGlide, getUrl(MODE_DEFAUTL));
                break;

            case R.id.activity_btn_fresco:
                setImageWithFresco(mActivityIvFresco, getUrl(MODE_FRESCO));
                break;
        }
    }


    private String getUrl(@UrlMode int mode) {
        if (mActivityCbNetwork.isChecked()) {
            mode = MODE_NETWORK;
        }

        switch (mode) {
            case MODE_DEFAUTL:
                return R.drawable.portrait + "";
            case MODE_UIL:
                return "drawable://" + R.drawable.portrait;
            case MODE_FRESCO:
                return "res://" + getPackageName() + File.separator + R.drawable.portrait;
            default:
                return IMAGE_URL;
        }
    }

    private void setImageWithUIL(ImageView iv, String url) {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }
        mImageLoader.displayImage(url, iv);
    }

    private void setImageWithPicasso(ImageView iv, int id) {
        Picasso.with(this).load(id).into(iv);
    }

    private void setImageWithPicasso(ImageView iv, String url) {
        try {
            int id = Integer.parseInt(url);
            setImageWithPicasso(iv, id);
        } catch (Exception e) {
            Picasso.with(this).load(url).into(iv);
        }
    }

    private void setImageWithGlide(ImageView iv, int id) {
        ImageUtils.setResId(iv, id);
    }

    private void setImageWithGlide(ImageView iv, String url) {
        try {
            int id = Integer.parseInt(url);
            setImageWithGlide(iv, id);
        } catch (Exception e) {
            ImageUtils.setCircleUrl(iv, url);
        }

    }

    private void setImageWithFresco(SimpleDraweeView iv, String url) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(iv.getController())
                .setImageRequest(imageRequest)
                .build();
        mActivityIvFresco.setController(controller);
    }
}
