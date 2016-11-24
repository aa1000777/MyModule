package com.cjj.mytoolsapplication.viewpagerlibrary;


public class Project {

    public String created_at;
    public String default_branch;
    public String description;
    public boolean fork;
    public int forks_count;
    public int id;
    public boolean issues_enabled;
    public String language;
    public String last_push_at;
    public String name;
    public NamespaceBean namespace;
    public OwnerBean owner;
    public String path;
    public String path_with_namespace;
    public boolean publicX;
    public boolean pull_requests_enabled;
    public int recomm;
    public int stars_count;
    public int watches_count;
    public boolean wiki_enabled;


    public static class NamespaceBean {

        public String address;
        public String avatar;
        public String created_at;
        public String description;
        public String email;
        public int enterprise_id;
        public int id;
        public int level;
        public String location;
        public String name;
        public int owner_id;
        public String path;
        public boolean publicX;
        public String updated_at;
        public String url;

    }

    public static class OwnerBean {

        public String created_at;
        public String email;
        public int id;
        public String name;
        public String new_portrait;
        public String portrait;
        public String state;
        public String username;
    }
}
