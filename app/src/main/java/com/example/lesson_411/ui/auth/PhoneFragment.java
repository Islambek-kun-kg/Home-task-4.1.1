package com.example.lesson_411.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lesson_411.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneFragment extends Fragment {
    private EditText edtPhone, edtCode;
    private Button btnContinue, btnCheck;
    private String id;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtPhone = view.findViewById(R.id.editPhoneForPhoneFragment);
        edtCode = view.findViewById(R.id.editCodePhoneFragment);
        btnContinue = view.findViewById(R.id.btnContinueForPhoneFragment);
        btnCheck = view.findViewById(R.id.btnCheckPhoneFragment);
        btnContinue.setOnClickListener(v -> {
            requestSms();
            btnContinue.setVisibility(View.GONE);
            btnCheck.setVisibility(View.VISIBLE);
            edtPhone.setVisibility(View.GONE);
            edtCode.setVisibility(View.VISIBLE);
            btnCheck.setOnClickListener(v1 -> {
                confirm();
            });
        });
        setCallback();
    }

    private void confirm() {
        String codeInSms = edtCode.getText().toString().trim();
        if (codeInSms.length() == 6 && TextUtils.isDigitsOnly(codeInSms))
            signIn(PhoneAuthProvider.getCredential(id, codeInSms));
    }

    public void setCallback() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone", "onVerificationCompleted");
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone", "onVerificationFailed" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                id = s;
            }
        };
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp();
            else
                Toast.makeText(requireContext(), "Ошибка" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void requestSms() {
        String phone = edtPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            edtPhone.setError("Напишите номер телефона!");
            edtPhone.requestFocus();
            return;
        }
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(requireActivity()).setCallbacks(mCallbacks).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}