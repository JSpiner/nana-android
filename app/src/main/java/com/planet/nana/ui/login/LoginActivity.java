package com.planet.nana.ui.login;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import static android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.planet.nana.R;
import com.planet.nana.api.Api;
import com.planet.nana.databinding.ActivityLoginBinding;
import com.planet.nana.model.Contact;
import com.planet.nana.ui.base.BaseActivity;
import com.planet.nana.ui.main.MainActivity;
import com.planet.nana.util.Prefer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    private UploadDialog uploadDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentBinding(R.layout.activity_login);

        init();
    }

    private void init() {
        binding.login.setOnClickListener(__ -> {
            if (isValidInput()) {
                requestLogin();
            }
            else {
                showToast("id/pw를 입력해주세요.");
            }
        });
    }

    private boolean isValidInput() {
        return !TextUtils.isEmpty(binding.userId.getText()) &&
                !TextUtils.isEmpty(binding.userPw.getText());
    }

    private void requestLogin() {
        uploadDialog = new UploadDialog(this);
        uploadDialog.show();

        Api.getInstance().login(
                binding.userId.getText().toString(),
                binding.userPw.getText().toString()
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                            Prefer.putString(Prefer.KEY_LOGINED_ID, loginResponse.getId());
                            showToast("로그인 되셨습니다.");
                            uploadContacts();
                            putPushToken();
                        },
                        throwable -> showToast("id/pw를 확인해주세요."));
    }

    private void uploadContacts() {
        loadContacts().map(Contact::getName)
                .toList()
                .flatMapCompletable(contactList -> Api.getInstance().addContact("1", contactList))
                .delay(1000 * 4, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            uploadDialog.dismiss();
                            showToast("주소록 동기화가 완료되었습니다.");
                            startMainActivity();
                        },
                        throwable -> showToast("주소록 동기화를 실패하였습니다.")
                );
    }

    private Observable<Contact> loadContacts() {
        Observable<Contact> contactObservable = Observable.create(
                emitter -> {
                    ContentResolver resolver = getContentResolver();
                    Cursor cursor = resolver.query(CONTENT_URI, PROJECTION, null, null, null);
                    final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    String name;
                    String number;
                    while (cursor.moveToNext()) {
                        name = cursor.getString(nameIndex);
                        number = cursor.getString(numberIndex)
                                .replace("-", "")
                                .replace("+82", "");

                        emitter.onNext(new Contact(name, number));
                    }
                    cursor.close();
                    emitter.onComplete();
                }
        );
        return contactObservable.observeOn(Schedulers.io());
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void putPushToken() {
        Api.getInstance().putPushToken(
                binding.userId.getText().toString(),
                Prefer.getString(Prefer.KEY_PUSH_TOKEN)
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
