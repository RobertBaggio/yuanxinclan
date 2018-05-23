package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/5/7.
 */
public class FileTestActivity extends BaseActivity {
    private static final int REQUEST_CHOOSER = 1234;
    @BindView(R.id.activity_file_test_button)
    Button activityFileTestButton;


    @Override
    public int getViewLayout() {
        return R.layout.activity_file_test;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

    }

    @OnClick(R.id.activity_file_test_button)
    public void onClick() {
        // Create the ACTION_GET_CONTENT Intent
        Intent getContentIntent = FileUtils.createGetContentIntent();

        Intent intent = Intent.createChooser(getContentIntent, "Select a file");
        startActivityForResult(intent, REQUEST_CHOOSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHOOSER:
                if (resultCode == RESULT_OK) {

                    final Uri uri = data.getData();

                    // Get the File path from the Uri
                    String path = FileUtils.getPath(this, uri);
                    // Alternatively, use FileUtils.getFile(Context, Uri)
                    if (path != null && FileUtils.isLocal(path)) {
                        File file = new File(path);
                    }
                }
                break;
        }
    }
}
