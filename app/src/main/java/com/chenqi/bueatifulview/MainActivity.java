package com.chenqi.bueatifulview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chenqi.gesturepasswordview.view.GesturePassWordView;

public class MainActivity extends AppCompatActivity {
    //    DeformationView view;
    GesturePassWordView gesturePassWordView;
    private GameHandleView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.a);
        view.addMoveListener(new GameHandleView.addMoveListener() {
            @Override
            public void position(GameHandleView.Position position) {
                switch (position) {
                    case TOP:
                        System.out.println("chenqi + top");
                        break;
                    case LEFT:
                        System.out.println("chenqi + left");
                        break;
                    case RIGHT:
                        System.out.println("chenqi + right");
                        break;
                    case BOTTOM:
                        System.out.println("chenqi + bottom");
                        break;
                    case LEFT_TOP:
                        System.out.println("chenqi + left_top");
                        break;
                    case RIGHT_TOP:
                        System.out.println("chenqi + right_top");
                        break;
                    case LEFT_BOTTOM:
                        System.out.println("chenqi + left_bottom");
                        break;
                    case RIGHT_BOTTOM:
                        System.out.println("chenqi + right_bottom");
                        break;
                }
            }
        });
//        view = findViewById(R.id.a);
//        view.smoothScrollTo(-400,0);
//        gesturePassWordView = findViewById(R.id.gesture);
//        gesturePassWordView.setListener(new GesturePassWordView.PwdListener() {
//            @Override
//            public void pwdBitNotEnough() {
//
//            }
//
//            @Override
//            public boolean pwdCheckError() {
//                return false;
//            }
//
//            @Override
//            public void pwdCheckSuccess() {
//
//            }
//
//            @Override
//            public void pwdSettingSuccess() {
//
//            }
//
//            @Override
//            public void pwdSettingAgain() {
//
//            }
//
//            @Override
//            public void pwdSettingError() {
//
//            }
//        });
    }
}
