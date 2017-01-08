package com.example.achuan.wellweather.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.EditText;

/**
 * Created by achuan on 16-11-13.
 * 功能：对话框的封装功能类
 */
public class DialogUtil {

    public static ProgressDialog sProgressDialog;

    //普通窗口的接口
    public interface OnAlertDialogButtonClickListener {
        //右边按钮点击事件
        void onRightButtonClick();
        //左边按钮点击事件
        void onLeftButtonClick();
    }
    //输入文本窗口的接口
    public interface OnInputDialogButtonClickListener {
        //右边按钮点击事件
        void onRightButtonClick(String input);
        //左边按钮点击事件
        void onLeftButtonClick();
    }


    /***1-创建一个最普通的对话框***/
    public static void createOrdinaryDialog(Context context, String title, String message,
                                    String rightString, String leftString,
                                    final OnAlertDialogButtonClickListener onAlertDialogButtonClickListener){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);//先创建一个构造实例
        dialog.setTitle(title);//设置标题
        dialog.setMessage(message);//设置内容部分
        dialog.setCancelable(true);//设置是否可以通过Back键取消：false为不可以取消,true为可以取消
        //设置右边按钮的信息
        dialog.setPositiveButton(rightString, new DialogInterface.OnClickListener() {
            @Override//点击触发事件
            public void onClick(DialogInterface dialogInterface, int i) {
                if(onAlertDialogButtonClickListener!=null){
                    onAlertDialogButtonClickListener.onRightButtonClick();
                }
            }
        });
        //设置左边按钮的信息
        dialog.setNegativeButton(leftString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(onAlertDialogButtonClickListener!=null){
                    onAlertDialogButtonClickListener.onLeftButtonClick();
                }
            }
        });
        dialog.show();//将对话框显示出来
    }

    /***2-创建一个耗时等待的对话框***/
    public static void createProgressDialog(Context context,String title,
                                            String message){
        sProgressDialog=new ProgressDialog(context);
        sProgressDialog.setTitle(title);
        sProgressDialog.setMessage(message);
        sProgressDialog.setCancelable(true);//对话框可以通过Back键取消掉
        //触碰对话框以外的屏幕部分时不会取消对话框显示
        sProgressDialog.setCanceledOnTouchOutside(false);
        sProgressDialog.show();
        /*//加载完后执行关闭对话框的方法
                progressDialog.dismiss();*/
    }
    /***3-关闭耗时加载对话框***/
    public static void closeProgressDialog(){
        sProgressDialog.dismiss();
    }

    /***4-创建可输入编辑文本的对话框***/
    public static void createInputDialog(Context context, String message, String title,
                                         String rightString, String leftString,
                                         final OnInputDialogButtonClickListener onInputDialogButtonClickListener){
        /*eitText的相关处理*/
        final EditText editText = new EditText(context);//创建一个新的editext对象
        editText.setText(message);
        editText.setSelection(message.length());//让光标停留在文字的末尾

        AlertDialog.Builder inputDialog = new AlertDialog.Builder(context);
        inputDialog.setTitle(title).setView(editText);
        inputDialog.setPositiveButton(rightString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onInputDialogButtonClickListener.onRightButtonClick(
                        editText.getText().toString());
            }
        });
        inputDialog.setNegativeButton(leftString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onInputDialogButtonClickListener.onLeftButtonClick();
            }
        });
        inputDialog.show();
    }

    /***5-创建自定义的对话框,layoutId(自定义的布局文件)***/
    public static Dialog createMyselfDialog(Context context,int layoutId,int gravity){
        //先创建窗口构造者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //设置视图布局
        builder.setView(layoutId);
        //通过构造者创建对话框布局实例
        Dialog dialog=builder.create();
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog的位置
        dialogWindow.setGravity(gravity);//Gravity.BOTTOM
        /*//获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);*/
        //显示窗口布局
        dialog.show();
        return dialog;
        /*//初始化自定义布局的控件,通过dialog实例来获取
        TextView chooseMan= (TextView) dialog.findViewById(R.id.tv_choose_man);
        TextView chooseWoman= (TextView) dialog.findViewById(R.id.tv_choose_woman);
        TextView chooseCancel= (TextView) dialog.findViewById(R.id.tv_choose_cancel);*/
    }

}
