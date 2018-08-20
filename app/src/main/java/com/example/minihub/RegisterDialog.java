package com.example.minihub;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.minihub.utils.Utils;


public class RegisterDialog extends Dialog {

    private EditText user;
    private EditText passwordEdit;
    private EditText rePasswordEdit;
    private Button registerBtn;
    private ImageView closeImg;

    private OnRegisterListener mListener;

    public RegisterDialog(@NonNull Context context, OnRegisterListener listener) {
        super(context);
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_register,null);
        setContentView(view);
        initView(view);
        setLayoutParam();
        setCanceledOnTouchOutside(false);
    }

    private void setLayoutParam(){
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = Utils.dp2px(300);
        layoutParams.height = Utils.dp2px(400);
        window.setAttributes(layoutParams);

    }

    private void initView(View view){
        user = view.findViewById(R.id.edit_user);
        passwordEdit = view.findViewById(R.id.edit_register_password);
        rePasswordEdit = view.findViewById(R.id.edit_rePassword);
        registerBtn = view.findViewById(R.id.dialog_register_btn);
        closeImg = view.findViewById(R.id.close_img);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    String username = user.getText().toString();
                    String password = passwordEdit.getText().toString();
                    String repassword = rePasswordEdit.getText().toString();
                    mListener.register(username,password,repassword);
                }
            }
        });
    }




    public interface OnRegisterListener{
        void register(String usrname,String password, String repassword);
    }
}
