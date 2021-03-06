package com.FinalProject.Betelhem.EtNews.Model;

public class Author
{
        public String name;
        public String url ;
        public String avatar ;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }

        public String getAvatar() {
                return avatar;
        }

        public void setAvatar(String avatar) {
                this.avatar = avatar;
        }

        public Author(String name, String url, String avatar) {
                this.name = name;
                this.url = url;
                this.avatar = avatar;
        }
}