package com.lpf.playlight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Nick on 15/12/7.
 */
public class SwitchButton extends View implements View.OnTouchListener {
    //开关开启时的背景，关闭时的背景，滑动按钮
    private Bitmap switchBg, switchOn, switchOff;
    private Rect on_Rect, off_Rect;

    //是否正在滑动
    private boolean isSlipping = false;
    //当前开关状态，true为开启，false为关闭
    private boolean isSwitchOn = false;

    //手指按下时的水平坐标X，当前的水平坐标X
//    private float previousX, currentX;
    private float previousY, currentY;

    //开关监听器
    private OnSwitchListener onSwitchListener;
    //是否设置了开关监听器
    private boolean isSwitchListenerOn = false;


    public SwitchButton(Context context) {
        super(context);
        init();
    }


    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        setImageResource();
        setOnTouchListener(this);
    }

    public void setImageResource() {
        switchBg = BitmapFactory.decodeResource(getResources(), R.mipmap.button_toggle_bg);
        switchOn = BitmapFactory.decodeResource(getResources(), R.mipmap.button_toggle_on);
        switchOff = BitmapFactory.decodeResource(getResources(), R.mipmap.button_toggle_off);

//        //右半边Rect，即滑动按钮在右半边时表示开关开启
//        on_Rect = new Rect(switchBg.getWidth() - switchOn.getWidth(), 0, switchBg.getWidth(), switchOn.getHeight());
//        //左半边Rect，即滑动按钮在左半边时表示开关关闭
//        off_Rect = new Rect(0, 0, switchOn.getWidth(), switchOn.getHeight());

        //上边半边Rect，即滑动按钮在上半边时表示开关开启
        on_Rect = new Rect(0, 0, switchOn.getWidth(), switchBg.getHeight());
        //下半边Rect，即滑动按钮在下半边时表示开关关闭
        off_Rect = new Rect(0, switchBg.getHeight() - switchOn.getHeight(), switchOn.getWidth(), switchOn.getHeight());
    }


    public void setSwitchState(boolean switchState) {
        isSwitchOn = switchState;
    }


    public boolean getSwitchState() {
        return isSwitchOn;
    }


    public void updateSwitchState(boolean switchState) {
        isSwitchOn = switchState;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        //滑动按钮的上边坐标
        float top_SlipBtn;

        //判断当前是否正在滑动
        if (isSlipping) {
            if (currentY > switchBg.getHeight()) {
                top_SlipBtn = switchBg.getHeight() - switchOn.getHeight();
            } else {
                top_SlipBtn = currentY - switchOn.getHeight() / 2;
            }
        } else {
            //根据当前的开关状态设置滑动按钮的位置
            if (isSwitchOn) {
                top_SlipBtn = on_Rect.top;
            } else {
                top_SlipBtn = off_Rect.top;
            }
        }

        //对滑动按钮的位置进行异常判断
        if (top_SlipBtn < 0) {
            top_SlipBtn = 0;
        } else if (top_SlipBtn > switchBg.getHeight() - switchOn.getHeight()) {
            top_SlipBtn = switchBg.getHeight() - switchOn.getHeight();
        }

        //手指滑动到下半边的时候表示开关为关闭状态，滑动到上半边的时候表示开关为开启状态
        canvas.drawBitmap(switchBg, matrix, paint);
        if (currentY < (switchBg.getHeight() / 2)) {
            canvas.drawBitmap(switchOn, 0, top_SlipBtn, paint);
        } else {
            canvas.drawBitmap(switchOff, 0, top_SlipBtn, paint);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchBg.getWidth(), switchBg.getHeight());
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            //滑动
            case MotionEvent.ACTION_MOVE:
//                if(event.getY() < 0)
//                    currentY = 0;
//                else if(event.getY() > switchBg.getHeight())
//                    currentY = switchBg.getHeight();
//                else
                    currentY = event.getY();
                Log.i("currentY ==== ", "" + currentY);
                Log.i("Top ==== ", "" + getY());
                break;

            //按下
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > switchBg.getWidth() || event.getY() > switchBg.getHeight()) {
                    return false;
                }

                isSlipping = true;
                previousY = event.getY();
                currentY = previousY;
                break;

            //松开
            case MotionEvent.ACTION_UP:
                isSlipping = false;
                //松开前开关的状态
                boolean previousSwitchState = isSwitchOn;

                if (event.getY() >= (switchBg.getHeight() / 2)) {
                    isSwitchOn = false;
                } else {
                    isSwitchOn = true;
                }

                //如果设置了监听器，则调用此方法
                if (isSwitchListenerOn && (previousSwitchState != isSwitchOn)) {
                    onSwitchListener.onSwitched(isSwitchOn);
                }
                break;

            default:
                break;
        }

        //重新绘制控件
        invalidate();
        return true;
    }


    public void setOnSwitchListener(OnSwitchListener listener) {
        onSwitchListener = listener;
        isSwitchListenerOn = true;
    }


    public interface OnSwitchListener {
        abstract void onSwitched(boolean isSwitchOn);
    }
}