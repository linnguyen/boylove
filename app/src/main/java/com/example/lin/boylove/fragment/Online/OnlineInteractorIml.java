package com.example.lin.boylove.fragment.Online;

import android.content.Context;

import com.example.lin.boylove.R;
import com.example.lin.boylove.entity.Response.Error;
import com.example.lin.boylove.entity.Response.User;
import com.example.lin.boylove.localstorage.SessionManager;
import com.example.lin.boylove.service.DolaxAPIs;
import com.example.lin.boylove.utilities.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ryne on 27/10/2017.
 */

public class OnlineInteractorIml implements OnlineInteractor {
    private Context context;
    private DolaxAPIs dolaxAPIs;

    public OnlineInteractorIml(Context context) {
        this.context = context;
        dolaxAPIs = DolaxAPIs.Factory.create();
    }

    @Override
    public void getListOnline(final OnlinePresenter.OnOnlineFinishedListener listener) {
        Call<List<User>> call = dolaxAPIs.getOnlines(SessionManager.getInstance(context).getToken());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> lstOnline = response.body();
                    if (lstOnline != null) {
                        listener.onSuccess(lstOnline);
                    }
                } else {
                    Error error = DolaxAPIs.Factory.getError(response.errorBody());
                    listener.onFailure(error.getErrors());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Utils.showToast(context, context.getString(R.string.toast_st_went_wrong));
            }
        });
    }
}
