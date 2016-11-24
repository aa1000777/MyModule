package com.cjj.zxinglibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 扫码结果处理
 */

public class URLUtils {
    public static final Pattern PATTERN_URL = Pattern.compile(
            "(?:http|https)://([^/]+)(.+)"
    );
    public static final Pattern PATTERN_IMAGE = Pattern.compile(
            ".*?(gif|jpeg|png|jpg|bmp)"
    );
    /**
     * 解析跳转链接, 使用对应应用打开
     * @param context Context
     * @param uri give me a uri
     */
    public static void parseUrl(Context context, String uri){
        if (TextUtils.isEmpty(uri)) return;

        String url = uri;
        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "https://" + url;
        }
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }

        Matcher matcher;

        // image url ?
        matcher = PATTERN_IMAGE.matcher(url);
        if (matcher.matches()){
//            ImageGalleryActivity.show(context, url);
            return;
        }

        matcher = PATTERN_URL.matcher(url);
        if (!matcher.find()) {
            // other ?
            parseNonstandardUrl(context, uri);
            return;
        }

        // own ?
        String host = matcher.group(1);
        String path = matcher.group(2);

        if (TextUtils.isEmpty(host) || TextUtils.isEmpty(path)) return;
        switch (host){
            case "www.oschina.net":
                break;
            case "team.oschina.net":
                break;
            case "git.oschina.net":
                break;
            case "my.oschina.net":
                break;
            case "city.oschina.net":
                break;
            default:
                // pass
                openExternalBrowser(context, url);
        }

    }

    /**
     * 打开外置的浏览器
     *
     * @param context
     * @param url
     */
    public static void openExternalBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(Intent.createChooser(intent, "选择打开的应用"));
    }

    public static void parseNonstandardUrl(Context context, String url) {
        if (url.startsWith("mailto:")){
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            context.startActivity(Intent.createChooser(intent, "选择发送应用"));
            return;
        }
    }
}
