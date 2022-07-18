package com.example.cardflix.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cardflix.GlobalCardList;
import com.example.cardflix.LoginActivity;
import com.example.cardflix.R;
import com.example.cardflix.RegisterActivity;
import com.example.cardflix.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    private Button btn_SignOut;

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        btn_SignOut = root.findViewById(R.id.btn_AccountFragment_Signout);

        btn_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                GlobalCardList globalList = GlobalCardList.getInstance(root.getContext());
                globalList.removeListener();

                mAuth.signOut();

                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}