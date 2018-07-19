package com.chenqi.bueatifulview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //    DeformationView view;
    private GameHandleView view;
    PushRefreshView refreshView;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        refreshView.setListener(new PushRefreshView.RefreshStatusListener() {
//            @Override
//            public void start(PushRefreshView view) {
//                showToast("刷新开始");
//            }
//
//            @Override
//            public void stop() {
//                showToast("刷新结束");
//            }
//        });
//        view = findViewById(R.id.a);
//        view.addMoveListener(new GameHandleView.addMoveListener() {
//            @Override
//            public void position(GameHandleView.Position position) {
//                switch (position) {
//                    case TOP:
//                        System.out.println("chenqi + top");
//                        break;
//                    case LEFT:
//                        System.out.println("chenqi + left");
//                        break;
//                    case RIGHT:
//                        System.out.println("chenqi + right");
//                        break;
//                    case BOTTOM:
//                        System.out.println("chenqi + bottom");
//                        break;
//                    case LEFT_TOP:
//                        System.out.println("chenqi + left_top");
//                        break;
//                    case RIGHT_TOP:
//                        System.out.println("chenqi + right_top");
//                        break;
//                    case LEFT_BOTTOM:
//                        System.out.println("chenqi + left_bottom");
//                        break;
//                    case RIGHT_BOTTOM:
//                        System.out.println("chenqi + right_bottom");
//                        break;
//                }
//            }
//        });
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

    Handler handler = new Handler();

    private void showToast(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (new Object()) {
                    if (toast != null) {
                        toast.cancel();

                    }
                    toast = Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT);
                    toast.setText(text);
                    toast.show();
                }
            }
        });
    }

    public static void main(String[] args) {
        String dex = "[\\u4e00-\\u9fa5]{1,5}$";//只是汉字
        String dex3 = "[(\\u4e00-\\u9fa5)|(\\w)]{1,9}$";//汉字和其他字符
        String match = "jimme_sinn";
        System.out.println(" match = " + match.matches(dex) + " 校验结果是 --- " + match.matches(dex3) + " --- ");
    }
}
