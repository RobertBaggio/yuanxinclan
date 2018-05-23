package com.yuanxin.clan.core.market.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.lypeer.fcpermission.FcPermissions;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.BusinessDistrictClassifyActviity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.GongXuEntity;
import com.yuanxin.clan.core.extendsclass.BecomePicktask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/11/9 0009 15:46
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class OutputGongyActivity extends BaseActivity {

    @BindView(R.id.rlLeft)
    LinearLayout mRlLeft;
    @BindView(R.id.gyyouxqte)
    TextView gyyouxqte;
    @BindView(R.id.gydiqute)
    TextView gydiqute;
    @BindView(R.id.gyhyte)
    TextView gyhyte;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView activity_yuan_xin_crowd_middle_text;
    @BindView(R.id.gytitleet)
    EditText gytitleet;
    @BindView(R.id.gynumberet)
    EditText gynumberet;
    @BindView(R.id.gymoneyet)
    EditText gymoneyet;
    @BindView(R.id.gyzhouqiet)
    EditText gyzhouqiet;
    @BindView(R.id.gydetailet)
    EditText gydetailet;
    @BindView(R.id.gyyxqli)
    LinearLayout gyyxqli;
    @BindView(R.id.gyhyli)
    LinearLayout gyhyli;
    @BindView(R.id.gydiquli)
    LinearLayout gydiquli;
    @BindView(R.id.gyimage1)
    ImageView gyimage1;
    @BindView(R.id.gyimage2)
    ImageView gyimage2;
    @BindView(R.id.gyimage3)
    ImageView gyimage3;
    @BindView(R.id.gyimage4)
    ImageView gyimage4;
    @BindView(R.id.gyimage5)
    ImageView gyimage5;
    @BindView(R.id.gyimage6)
    ImageView gyimage6;
    @BindView(R.id.outputgy_button)
    Button outputgy_button;


    private int chooseImageIndex = -1;//要选择的图片的序号
    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_INDUSTRY = 11;//行业分类
    private SubscriberOnNextListener fileUploadOnNextListener;
    private String  image1, image2, image3, image4, image5, image6, image7, image8, image9,image10;
    private String proviceOne,cityOne,areaOne,industryId;
    private String type;
    private String addressId;
    private GongXuEntity mGongXuEntity;
    private More_LoadDialog mMore_loadDialog;

    @Override
    public int getViewLayout() {
        return R.layout.output_gongying;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        mMore_loadDialog = new More_LoadDialog(this);
        type = getIntent().getStringExtra("type");
        if (type.equals("my")){
            activity_yuan_xin_crowd_middle_text.setText("编辑供应");
            outputgy_button.setText("确认发布");
            mGongXuEntity = (GongXuEntity) getIntent().getSerializableExtra("datas");
            showdata();
        }
    }

    private void initOnNext() {
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    List<String> listString = JSON.parseArray(t.getData().toString(), String.class);
                    for (int i = 0; i < listString.size(); i++) {
                        saveImageUrl(chooseImageIndex, listString.get(i));
                    }
                } else {
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
    }

    private void showdata(){
        gytitleet.setText(mGongXuEntity.getTitle());
        gynumberet.setText(mGongXuEntity.getNumber());
        gymoneyet.setText(mGongXuEntity.getBudget());
        gyzhouqiet.setText(mGongXuEntity.getDeliveryCycle());
        gyyouxqte.setText(mGongXuEntity.getEndTime());
        gydiqute.setText(mGongXuEntity.getAddress());
        gyhyte.setText(mGongXuEntity.getIndustryNm());
        gydetailet.setText(mGongXuEntity.getContent());
        proviceOne = mGongXuEntity.getProvince();
        cityOne = mGongXuEntity.getCity();
        areaOne =mGongXuEntity.getArea();
        addressId =mGongXuEntity.getAddressId();
        industryId = mGongXuEntity.getIndustryId();
        if (!mGongXuEntity.getImage1().equals("no")){
            ImageManager.load(this, mGongXuEntity.getImage1(), R.drawable.banner01, gyimage1);//
            image1 = mGongXuEntity.getImage11();
        }
        if (!mGongXuEntity.getImage2().equals("no")){
            ImageManager.load(this, mGongXuEntity.getImage2(), R.drawable.banner01, gyimage2);//
            image2 = mGongXuEntity.getImage22();
        }
        if (!mGongXuEntity.getImage3().equals("no")){
            ImageManager.load(this, mGongXuEntity.getImage3(), R.drawable.banner01, gyimage3);//
            image3 = mGongXuEntity.getImage33();
        }
        if (!mGongXuEntity.getImage4().equals("no")){
            ImageManager.load(this, mGongXuEntity.getImage4(), R.drawable.banner01, gyimage4);//
            image4 = mGongXuEntity.getImage44();
        }
        if (!mGongXuEntity.getImage5().equals("no")){
            ImageManager.load(this, mGongXuEntity.getImage5(), R.drawable.banner01, gyimage5);//
            image5 = mGongXuEntity.getImage55();
        }
        if (!mGongXuEntity.getImage6().equals("no")){
            ImageManager.load(this, mGongXuEntity.getImage6(), R.drawable.banner01, gyimage6);//
            image6 = mGongXuEntity.getImage66();
        }
    }



    @OnClick({R.id.rlLeft,R.id.gyimage1,R.id.gyimage2,R.id.gyimage3,R.id.gyimage4,R.id.gyimage5,R.id.gyimage6,R.id.outputgy_button,
            R.id.gyhyli,R.id.gydiquli,R.id.gyyxqli})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                onBackPressed();
                break;
            case R.id.gyimage1:
                findImage(1);
                break;
            case R.id.gyimage2:
                findImage(2);
                break;
            case R.id.gyimage3:
                findImage(3);
                break;
            case R.id.gyimage4:
                findImage(4);
                break;
            case R.id.gyimage5:
                findImage(5);
                break;
            case R.id.gyimage6:
                findImage(6);
                break;
            case R.id.outputgy_button:
                if (ifokOutput()){
                    addInfo();
                }
                break;
            case R.id.gydiquli:
                onAddressPicker();
                break;
            case R.id.gyyxqli:
                birthSetting();
                break;
            case R.id.gyhyli:
                Intent publishIntent = new Intent(OutputGongyActivity.this, BusinessDistrictClassifyActviity.class);
                startActivityForResult(publishIntent, REQUEST_INDUSTRY);
                break;
        }
    }
    public boolean ifokOutput(){
        if (TextUtil.isEmpty(gytitleet.getText().toString())){
            ToastUtil.showInfo(this, "请输入供应具体物资", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(gynumberet.getText().toString())){
            ToastUtil.showInfo(this, "请输入数量", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(gyyouxqte.getText().toString())){
            ToastUtil.showInfo(this, "请完善有效日期", Toast.LENGTH_SHORT);
            return false;
        }
//        if (TextUtil.isEmpty(gyzhouqiet.getText().toString())){
//            Toast.makeText(this, "请完善交付周期", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (TextUtil.isEmpty(gydiqute.getText().toString())){
            ToastUtil.showInfo(this, "请完善交付地区", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(gyhyte.getText().toString())){
            ToastUtil.showInfo(this, "请选择行业", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(gydetailet.getText().toString())){
            ToastUtil.showInfo(this, "请输入供应具体物资详情", Toast.LENGTH_SHORT);
            return false;
        }
//        if (TextUtil.isEmpty(image1)||TextUtil.isEmpty(image2)||TextUtil.isEmpty(image3)||TextUtil.isEmpty(image4)||TextUtil.isEmpty(image5)){
//            Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }

    /**
     * 查找根据对应控件去找图片
     *
     * @param i
     */
    private void findImage(int i) {
        chooseImageIndex = i;
        requestPermission();
        MultiImageSelector.create(this)
                .showCamera(true) // 是否显示相机. 默认为显示
//                .count(int) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                .single() // 单选模式
//                    .multi() // 多选模式, 默认模式;
//                    .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
                .start(this, REQUEST_IMAGE);
    }
    private void requestPermission() {
        FcPermissions.requestPermissions(this, getString(R.string.prompt_request_storage), FcPermissions.REQ_PER_CODE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    /**
     * 找到对应的图片控件
     *
     * @param chooseImageIndex
     * @return
     */
    private ImageView chooseImageView(int chooseImageIndex) {
        switch (chooseImageIndex) {
            case 0:
                return null;
            case 1:
                return gyimage1;
            case 2:
                return gyimage2;
            case 3:
                return gyimage3;
            case 4:
                return gyimage4;
            case 5:
                return gyimage5;
            case 6:
                return gyimage6;
            default:
                return null;
        }
    }
    /**
     * 保存上传后的图片地址
     *
     * @param chooseImageIndex
     * @param imageUrl
     */
    private void saveImageUrl(int chooseImageIndex, String imageUrl) {
        switch (chooseImageIndex) {
            case 0:
//                image1 = imageUrl;
                break;
            case 1:
                image1 = imageUrl;
                break;
            case 2:
                image2 = imageUrl;
                break;
            case 3:
                image3 = imageUrl;
                break;
            case 4:
                image4 = imageUrl;
                break;
            case 5:
                image5 = imageUrl;
                break;
            case 6:
                image6 = imageUrl;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("lgq","requestCode==="+requestCode);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int i = 0; i < pathImage.size(); i++) {
                    String path = pathImage.get(0);
                    File compressedImage1File = Compressor.getDefault(this).compressToFile(new File(path));//压缩图片文件
                    ImageManager.loadFilePath(this, path, R.drawable.list_img, chooseImageView(chooseImageIndex));
                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, this), "image", compressedImage1File);
                }
            }
        }else if (requestCode == REQUEST_INDUSTRY) {
            if (resultCode == RESULT_OK) {//行业分类
                String industryName = data.getStringExtra("industryName");//行业名称
                industryId = data.getStringExtra("industryId");//对应id
                gyhyte.setText(industryName);
            }
        }

    }

    private void addInfo() {
        mMore_loadDialog.show();
        String title = gytitleet.getText().toString().trim();//名称
        String number = gynumberet.getText().toString().trim();//简介
        String money = gymoneyet.getText().toString().trim();
        String numbertime = gyzhouqiet.getText().toString().trim();
        if (TextUtil.isEmpty(gymoneyet.getText().toString().trim())){
            money="议价";
        }
        if (TextUtil.isEmpty(gyzhouqiet.getText().toString().trim())){
            numbertime="待定";
        }
        String riqitime = gyyouxqte.getText().toString().trim();
        String nr = gydetailet.getText().toString().trim();
        String url= Url.supplyDemand;
        RequestParams params = new RequestParams();
        params.put("title", title);//商圈名称
        params.put("number", number);//商圈类型
        params.put("budget", money);//商圈名称
        params.put("deliveryCycle", numbertime);//商圈描述
        params.put("endTime", riqitime);//商圈名称
        params.put("address.province", proviceOne);//省
        params.put("address.city", cityOne);//市
        params.put("address.area", areaOne);//区
        params.put("industryId", industryId);
        params.put("content", nr);
        if (type.equals("my")){
            params.put("del", "false");
            params.put("addressId", addressId);
            params.put("supplyDemandId", mGongXuEntity.getSupplyDemandId());
            url =Url.supplyDemandupdate;
        }

//        params.put("user.userNm", UserNative.getName());
//        params.put("user.userId", UserNative.getId());
        params.put("userNm", UserNative.getName());
        params.put("userId", UserNative.getId());
        params.put("supplyDemand", 0);//需求id
        if (!TextUtil.isEmpty(image1)) {
            params.put("image1", image1);
        }
        if (!TextUtil.isEmpty(image2)) {
            params.put("image2", image2);
        }
        if (!TextUtil.isEmpty(image3)) {
            params.put("image3", image3);
        }
        if (!TextUtil.isEmpty(image4)) {
            params.put("image4", image4);
        }
        if (!TextUtil.isEmpty(image5)) {
            params.put("image5", image5);
        }
        if (!TextUtil.isEmpty(image6)) {
            params.put("image6", image6);
        }

        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
                mMore_loadDialog.dismiss();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    public void onAddressPicker() {
        BecomePicktask task = new BecomePicktask(this);
        task.setCallback(new BecomePicktask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showWarning(getApplicationContext(), "数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    gydiqute.setText(null);
                } else {
                    proviceOne = province.getAreaName();
                    cityOne = city.getAreaName();
                    areaOne = county.getAreaName();
                    gydiqute.setText(proviceOne + "-" + cityOne + "-" + areaOne);
                }
            }
        });
        task.execute("广东", "东莞", "东城");
    }

    private void birthSetting() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month = (monthOfYear + 1) < 10 ? "0" + String.valueOf(monthOfYear + 1) : String.valueOf(monthOfYear + 1);
                gyyouxqte.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(dayOfMonth));//tvBirth就是选择日期结果textview
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
