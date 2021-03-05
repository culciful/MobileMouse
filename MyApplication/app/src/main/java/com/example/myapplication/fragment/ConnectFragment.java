package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConnectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConnectFragment extends Fragment {

    private EditText ip;
    private EditText port;
    private Button connect;
    private FunctionFragment functionFragment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConnectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static ConnectFragment newInstance(String param1) {
        ConnectFragment fragment = new ConnectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connect = view.findViewById(R.id.connect);
        ip = view.findViewById(R.id.IP_demo);
        port = view.findViewById(R.id.Port_demo);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).startSocket(ip.getText().toString(),Integer.parseInt(port.getText().toString()));
                Log.d("ConnectFragment",ip.getText().toString());
                Log.d("ConnectFragment",port.getText().toString());
                if(functionFragment ==null){
                    functionFragment = new FunctionFragment();
                }
                Fragment fragment = getFragmentManager().findFragmentByTag("a");
                if(fragment!=null){
                    getFragmentManager().beginTransaction().hide(fragment).add(R.id.container, functionFragment).addToBackStack(null).commitAllowingStateLoss();
                } else{
                    getFragmentManager().beginTransaction().replace(R.id.container, functionFragment).addToBackStack(null).commitAllowingStateLoss();
                }
            }
        });
       /* if(getArguments()!=null){
            text.setText(getArguments().getString(ARG_PARAM1));
        }*/
    }
}