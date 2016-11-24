package com.lzy.okgo;

/**
 * URL
 */
public class Urls {
    //http://git.oschina.net/api/v3/projects/featured
    //http://git.oschina.net/api/v3/projects/search/
    public static final String URL_IP = "http://git.oschina.net/api/v3/";

    ////////////////FORM 方法///////////////////////
    //用户登录
    public static final String USER_FEATURED = "projects/featured";
    public static final String USER_POPULAR = "projects/popular";
    public static final String USER_LATEST = "projects/latest";

    public static String formUrl(String method) {
        return getUrl(URL_IP, method);
    }


    /**
     * 处理url
     * 如果不是以http://或者https://开头，就添加http://
     *
     * @param url 被处理的url
     * @return
     */
    public static String getUrl(String url, String method) {
        url = url + method;
        if (url == null) {
            return null;
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            url.replaceAll(" ", "%20");
            return url;
        } else {
            return "http://" + url;
        }
    }
}
