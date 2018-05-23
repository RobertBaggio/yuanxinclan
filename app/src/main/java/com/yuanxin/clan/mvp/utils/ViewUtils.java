package com.yuanxin.clan.mvp.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.Utils;

import java.util.regex.Pattern;

/**
 * Created by Walker on 2016/10/9.
 */

public class ViewUtils {
    public static boolean isEmpty(TextView contentView, String errorInfo) {
        return isEmpty(contentView, errorInfo, false);
    }

    public static boolean isEmpty(TextView contentView, String errorInfo, boolean showToast) {
        boolean isEmpty = TextUtils.isEmpty(contentView.getText());
        if (isEmpty && showToast) {
            ToastUtil.showLong(errorInfo);
        } else if (isEmpty && !showToast) {
            contentView.setError(errorInfo);
            contentView.performClick();
        }
        return isEmpty;
    }

    public static boolean isValidLength(TextView contentView, int validLength, String errorInfo) {
        String content = contentView.getText().toString();
        if (TextUtils.isEmpty(content) || content.trim().length() < validLength) {
            contentView.setError(errorInfo);
            contentView.performClick();
            return false;
        }

        return true;
    }

    public static boolean passwordValidate(EditText passwordEt, EditText confirmPasswordEt, String errorInfo) {
        String password = passwordEt.getText().toString();
        String confirmPassword = confirmPasswordEt.getText().toString();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || !password.equals(confirmPassword)) {
            confirmPasswordEt.setError(errorInfo);
            confirmPasswordEt.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean isPhoneNumber(TextView contentView, String errorInfo) {
        String content = contentView.getText().toString();
        if (TextUtils.isEmpty(content) || !Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(content).matches()) {
            contentView.setError(errorInfo);
            contentView.performClick();
            return false;
        }

        return true;
    }

    //更新dialog
    public static void AlertDialog(final Context context, String title, String content, String confirm, String cancel, final DialogCallback dc) {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_layout, null);
        TextView titleTextView = (TextView) view.findViewById(R.id.title);
        TextView contentTextView = (TextView) view.findViewById(R.id.content);
        TextView confirmTextView = (TextView) view.findViewById(R.id.confirm);
        TextView cancelTextView = (TextView) view.findViewById(R.id.cancel);
        titleTextView.setText(title);
        contentTextView.setText(content);
        confirmTextView.setText(confirm);
        cancelTextView.setText(cancel);
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);

        lp.width = Utils.getScreenWidth(context) - 40; // 宽度
        lp.height = Utils.getViewMeasureHeight(view) + 100; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
        if (dc != null)
        confirmTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.onConfirm();
                dialog.dismiss();
            }
        });
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.onCancel();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public interface DialogCallback {
        void onConfirm();
        void onCancel();
    }
}
