package no.array.android.permissionbug;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
    Context mContext;
    Activity mActivity;
    String DIALOG_TAG = "mydialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mActivity = this;

        setContentView(R.layout.activity_main);
        WebView webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new myWebViewClient());

        if (Build.VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.loadUrl("file:///android_asset/index.html");
    }

    private class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains("second")) {
                String permission = Manifest.permission.READ_PHONE_STATE;

                if(Build.VERSION.SDK_INT >= 23) {
                    if (mActivity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                        mActivity.requestPermissions(new String[]{permission}, 1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Already had READ_PHONE_STATE permission", Toast.LENGTH_SHORT).show();
                    }
                }
                openMyDialog();
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public void openMyDialog() {
        FragmentManager fm = mActivity.getFragmentManager();
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            DialogFragment loginDialogFragment = MyDialogFragment.newInstance();
            loginDialogFragment.show(fm, DIALOG_TAG);
        }
    }
    
    @SuppressWarnings("NullableProblems")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 1) {
            // oh my, our question has been answered
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Got READ_PHONE_STATE permission", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Did not get READ_PHONE_STATE permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
