package online.meetforyou.whatsappapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import online.meetforyou.whatsappapp.R;
import online.meetforyou.whatsappapp.WebViewController;
import online.meetforyou.whatsappapp.databinding.FragmentStatusBinding;


public class StatusFragment extends Fragment {



    public StatusFragment() {
        // Required empty public constructor
    }

private FragmentStatusBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        binding = FragmentStatusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        WebView webView = root.findViewById(R.id.google);
        webView.loadUrl("https://www.google.com/");
        webView.setWebViewClient(new WebViewController());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);

    return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}