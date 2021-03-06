package com.momoxiangbei.rentalcar.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.momoxiangbei.rentalcar.R;
import com.momoxiangbei.rentalcar.dialog.WaitDialog;

/**
 * Created by Administrator on 2015/9/8.
 */

public abstract class BaseTitleFragment extends Fragment {

    protected Activity mActivity;
    protected RelativeLayout left;
    protected TextView title;
    protected TextView right;

    public abstract void create(Bundle bundle);

    public abstract int getLayoutResId();

    public  void initView(View view){
        left = (RelativeLayout) view.findViewById(R.id.left);
        title = (TextView) view.findViewById(R.id.title);
        right = (TextView) view.findViewById(R.id.right);

        left.setVisibility(View.INVISIBLE);
        right.setVisibility(View.INVISIBLE);
    }

    public abstract void initParams();

    public abstract void initListeners();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        initView(view);
        initParams();
        initListeners();
        return view;
    }

    private WaitDialog dialog;

    protected void waiting(String msg, boolean cancelable) {
//        dialog = new WaitDialog(mActivity, R.style.TransparentDialog);
        dialog = new WaitDialog(mActivity);
        dialog.setCancelable(cancelable);

        Dialog.OnKeyListener keyListener = new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        };
        dialog.setOnKeyListener(keyListener);
        dialog.show();
        dialog.setWaitText(TextUtils.isEmpty(msg) ? "请稍候..." : msg);
    }

    public void waiting(String msg) {
        if (isVisible()) {
            waiting(msg, true);
        }
    }

    public void stopWaiting() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}