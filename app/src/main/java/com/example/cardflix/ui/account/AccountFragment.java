package com.example.cardflix.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cardflix.GlobalCardList;
import com.example.cardflix.LoginActivity;
import com.example.cardflix.R;
import com.example.cardflix.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btn_SignOut = root.findViewById(R.id.btn_AccountFragment_Signout);
        btn_SignOut.setOnClickListener(view -> {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            GlobalCardList globalList = GlobalCardList.getInstance(root.getContext());
            globalList.deleteGlobalCardListObject();
            mAuth.signOut();
            getActivity().finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            startActivity(intent);
            getActivity().overridePendingTransition(0,0);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}