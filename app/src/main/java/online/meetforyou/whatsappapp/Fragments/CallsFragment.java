package online.meetforyou.whatsappapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import online.meetforyou.whatsappapp.R;
import online.meetforyou.whatsappapp.WebViewController;
import online.meetforyou.whatsappapp.databinding.FragmentCallsBinding;
import online.meetforyou.whatsappapp.databinding.FragmentStatusBinding;


public class CallsFragment extends Fragment {

    public CallsFragment() {
        // Required empty public constructor
    }

    private FragmentCallsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentCallsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        WebView webView = root.findViewById(R.id.youtube);
        webView.loadUrl("https://www.youtube.com/");
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