package com.FinalProject.Betelhem.EtNews.common;

import android.provider.BaseColumns;


public class NewsContract {
    private NewsContract(){}
    public static final class NewsEntery implements BaseColumns{
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_TITlE = "title";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TAG = "tag";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_ISREADEN = "readen";
    }
}
