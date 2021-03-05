package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FunctionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FunctionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String TAG = "FunctionFragment";
    private FrameLayout left,right,slide,move;

    public FunctionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static FunctionFragment newInstance(String param1, String param2) {
        FunctionFragment fragment = new FunctionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_function, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        left = view.findViewById(R.id.left);
        right = view.findViewById(R.id.right);
        slide = view.findViewById(R.id.slide);
        move = view.findViewById(R.id.move);
        //监听左键
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String action = null ;
                //监听动作类型
                switch (motionEvent.getAction()) {

                    //如果动作类型是点下
                    case MotionEvent.ACTION_DOWN:
                        //将action这个字符串赋值为MOUSEEVENTF_LEFTDOWN
                        action = "MOUSEEVENTF_LEFTDOWN";
                        break;
                    //如果动作类型是离开
                    case MotionEvent.ACTION_UP:
                        //将action这个字符串赋值为MOUSEEVENTF_LEFTUP
                        action = "MOUSEEVENTF_LEFTUP";
                        break;
                    default:
                        break;
                }
                //如果action祖父传不为空
                if (action != null) {
                    //调用sendSocketMessage函数，将action这个字符串发给服务器
                    Log.d(TAG,action);
                    ((MainActivity)getActivity()).sendMessage(action);
                }
                return true;
            }
        });
        //监听右键
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String action = null;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        action = "MOUSEEVENTF_RIGHTDOWN";
                        break;
                    case MotionEvent.ACTION_UP:
                        action = "MOUSEEVENTF_RIGHTUP";
                        break;
                    default:
                        break;
                }
                if (action != null) {
                    Log.d(TAG,action);
                    ((MainActivity)getActivity()).sendMessage(action);
                }
                return true;
            }
        });
        //监听滚轮
        slide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String action = null ;
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        action = "MOUSEEVENTF_MIDDLEDOWN";
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        action = "MOUSEEVENTF_MIDDLEUP";
                        break;
                    default:
                        break;
                }
                if (action != null) {
                    Log.d(TAG,action);
                    ((MainActivity)getActivity()).sendMessage(action);
                }
                return true;
            }
        });
        //监听触摸屏
        move.setOnTouchListener(new View.OnTouchListener() {
            //对位置坐标的定义
            float lastX;
            float lastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {//当点击面板时
                String actionString = null;
                //检测用户动作
                switch (event.getAction()) {

                    //如果动作类型是点下
                    case MotionEvent.ACTION_DOWN:
                        //actionString = String.format("DOWN:(%f,%f)", event.getX(),event.getY());
                        //记录当前点下的位置坐标
                        lastX = event.getX();
                        lastY = event.getY();
                        break;
                    //如果动作是在屏幕上移动
                    case MotionEvent.ACTION_MOVE:
                        //actionString字符串记录下来的就是终点坐标减去起点坐标，这个记录的频率好像跟系统定义有关，并且对这个信息格式化一下，有利于服务器接收数据之后操作。
                        //String.format就是个格式化函数，格式化的形势类似MOUSEEVENTF_MOVE %f %f
                        actionString = String.format("MOUSEEVENTF_MOVE %f %f", event.getX() - lastX,event.getY() - lastY);
                        lastX = event.getX();
                        lastY = event.getY();

                        break;

                    //如果动作是用户从屏幕离开
                    case MotionEvent.ACTION_UP:
                        //actionString = String.format("UP:(%f,%f)", event.getX(),event.getY());
                        break;

                    default:
                        break;
                }
                if (actionString!= null) {
                    Log.d(TAG,actionString);
                    ((MainActivity)getActivity()).sendMessage(actionString);
                }
                return true;
            }
        });
    }

}