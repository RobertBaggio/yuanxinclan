package com.yuanxin.clan.core.activity;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.yuanxin.clan.R;

/**
 * Created by lenovo1 on 2017/5/21.
 */
public class SmallCaptureActivity extends CaptureActivity {
    @Override
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.capture_small);
        return (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);
    }
}
