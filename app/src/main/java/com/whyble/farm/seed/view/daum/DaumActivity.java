package com.whyble.farm.seed.view.daum;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.whyble.farm.seed.R;
import com.whyble.farm.seed.common.base.BaseActivity;
import com.whyble.farm.seed.databinding.ActivityDaumBinding;

public class DaumActivity extends BaseActivity<DaumActivity> {

    ActivityDaumBinding binding;

    private Handler handler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daum);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_daum);
        binding.setActivity(DaumActivity.this);
        init_webView();
        handler = new Handler();
    }

    @Override
    protected BaseActivity<DaumActivity> getActivityClass() {
        return DaumActivity.this;
    }

    public void init_webView() {
        // WebView 설정
        // JavaScript 허용
        binding.daumWebview.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        binding.daumWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        binding.daumWebview.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        binding.daumWebview.setWebChromeClient(new WebChromeClient());
        // webview url load. php 파일 주소
        binding.daumWebview.loadUrl("http://farmseed.whyble.gethompy.com/APP/API/daum_map.php");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {

                    binding.daumResult.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    Intent intent = getIntent();
                    intent.putExtra("zipcorde", String.format("%s", arg1));
                    intent.putExtra("address", String.format("%s %s", arg2, arg3));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }
}
