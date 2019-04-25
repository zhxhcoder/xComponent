package com.zhxh.xcomponentlib.xmenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zhxh.xcomponentlib.CTextView;
import com.zhxh.xcomponentlib.FlowLayout;
import com.zhxh.xcomponentlib.R;

/**
 * Created by zhxh on 2019/4/19
 * 下拉选择框
 */
public class CDropDownFloatMenu {

    private Activity context;
    public PopupWindow popupwindow;

    private int height = 0;
    private int width = 0;

    private String selectStr;
    private String[] stringArray;
    ItemClickTextView itemClick;

    private int selectTextColor;
    private int selectSolidColor;
    private int defaultSolidColor;
    private int defaultTextColor;


    public CDropDownFloatMenu(Activity activity, String[] stringArray, String selectStr, ItemClickTextView itemClick) {
        this.context = activity;
        this.stringArray = stringArray;
        this.selectStr = selectStr;
        this.itemClick = itemClick;

        if (stringArray != null) {
            createAutoDialog();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createAutoDialog() {
        selectTextColor = Color.parseColor("#E54749");
        selectSolidColor = Color.parseColor("#FFE8E8");

        defaultTextColor = Color.parseColor("#48484F");
        defaultSolidColor = Color.parseColor("#F0F0F0");

        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflate.inflate(R.layout.pop_common_drop_down, null);

        popupView.setOnTouchListener((v, event) -> {
            dismiss();
            return false;
        });

        final FlowLayout item_container;
        item_container = popupView.findViewById(R.id.item_container);

        final LinearLayout pop_layout;
        pop_layout = popupView.findViewById(R.id.pop_layout);

        initFloatItem(item_container);

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int widthPixels = dm.widthPixels;

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        pop_layout.measure(w, h);

        height = pop_layout.getMeasuredHeight();

        popupwindow = new PopupWindow(popupView, widthPixels, height);
        popupwindow.setTouchable(true); // 设置PopupWindow可触摸
        popupwindow.setOutsideTouchable(true);
        popupwindow.setFocusable(true);

        //WindowManager.LayoutParams lp =context.getWindow().getAttributes();
        //lp.alpha = 0.3f;
        //context.getWindow().setAttributes(lp);

        popupwindow.setOnDismissListener(() -> {
            //Todo
        });
    }


    private void initFloatItem(FlowLayout item_container) {
        item_container.removeAllViews();
        for (int i = 0; i < stringArray.length; i++) {
            CTextView textView = new CTextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setText(stringArray[i]);

            if (selectStr.equalsIgnoreCase(stringArray[i])) {
                textView.setBtnAttr(
                    selectSolidColor
                    , selectTextColor
                    , selectSolidColor
                    , selectTextColor
                    , dip2px(2)
                    , dip2px(1));
                textView.setTextColor(selectTextColor);
            } else {
                textView.setSolidAttr(defaultSolidColor, defaultSolidColor, dip2px(2));
                textView.setTextColor(defaultTextColor);
            }

            //兼容低版本可能出现的paddingLeft和paddingRight不起作用
            textView.setWidth((int) textView.getPaint().measureText(stringArray[i]) + dip2px(20));
            textView.setPadding(0, dip2px(5), 0, dip2px(5));
            item_container.addView(textView);

            int finalI = i;

            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //selectStr = stringArray[finalI];
                    //initFloatItem(item_container);
                    //item_container.invalidate();
                    itemClick.onItemClick(finalI);
                    dismiss();
                    return false;
                }
            });
        }
    }

    public void dismiss() {
        if (popupwindow != null && popupwindow.isShowing()) {
            popupwindow.dismiss();
        }
    }

    //默认调用方法
    public void showAsDropDown(View anchorView) {
        Rect visibleFrame = new Rect();
        anchorView.getGlobalVisibleRect(visibleFrame);
        int height = anchorView.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
        popupwindow.setHeight(height);
        popupwindow.showAsDropDown(anchorView, 0, 0);
    }

    public int dip2px(float dpValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 定义状态改变接口
     */
    public interface ItemClickTextView {
        void onItemClick(int index);
    }
}