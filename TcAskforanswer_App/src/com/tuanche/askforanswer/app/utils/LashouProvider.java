/*
 * Copyright (C) 2010 mAPPn.Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tuanche.askforanswer.app.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;


/**
 * lashou Content Provider
 * 
 * @author bichao
 * @date    2013-4-25
 * @since  Version 0.7.0
 */
public class LashouProvider extends ContentProvider {
	
	private static final String authorities = "com.lashou.groupurchasing.provider";
    /** The database that lies underneath this content provider */
    private SQLiteOpenHelper mOpenHelper = null;
    
    /** Database filename */
    private static final String DB_NAME = "lashouBusinesses.db";
    /** Current database version */
    private static final int DB_VERSION = 2;
  
    /** Name of log table in the database */
    public static final String TABLE_LOG = "log";
    
    /** MIME type for the entire list */
    private static final String LIST_TYPE = "vnd.android.cursor.dir/";
    /** MIME type for an individual item */
    private static final String ITEM_TYPE = "vnd.android.cursor.item/";
    
    /** URI matcher used to recognize URIs sent by applications */
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    
    /** URI matcher constant for the URI of an search_history */
    private static final int LOG = 1;
    
    private static final int URI_CACHE = 2;
    
    private static final int URI_HISTORY = 3;
    /**选择位置  历史记录*/
    private static final int URI_ADDRESS_HISTORY = 4;
    
    private static final int URI_RECENTLY_GOODS = 5;
    
    static {
        sURIMatcher.addURI(authorities, "log", LOG);
        
        sURIMatcher.addURI(authorities, "cache", URI_CACHE);
        
        sURIMatcher.addURI(authorities, "historyRecord", URI_HISTORY);
        
        sURIMatcher.addURI(authorities, "addressHistoryRecord", URI_ADDRESS_HISTORY);
        
        sURIMatcher.addURI(authorities,"recentlyGoods" , URI_RECENTLY_GOODS);
    }
    
   
    /**
     * The content:// URI to access log
     */
    public static final Uri LOG_CONTENT_URI = Uri.parse("content://"+authorities+"/log");
    
    public static final Uri CACHE_CONTENT_URI = Uri.parse("content://"+authorities+"/cache");
    
    public static final Uri HISTORY_RECORD_CONTENT_URI = Uri.parse("content://"+authorities+"/historyRecord");
    
    public static final Uri ADDRESS_HISTORY_RECORD_CONTENT_URI = Uri.parse("content://"+authorities+"/addressHistoryRecord");
 
    public static final Uri RECENTLY_GOODS_URI = Uri.parse("content://"+authorities+"/recentlyGoods");
  
    
    /** Table ID */
    public static final String COLUMN_ID = "_id";
    
    /** Log表 */
    public static final String COLUMN_LOG_CONTENT = "content";
    public static final String COLUMN_LOG_TIME = "create_time";
    public static final String COLUMN_LOG_MODULE = "module";
    public static final String COLUMN_LOG_LEVEL = "level";
    public static final String COLUMN_LOG_NETWORK = "network";
    
    
    
//    /**历史记录表History**/
//    public static final String TABLE_HISTORY = "historyRecord";
//    public static final String COLUMN_HISTORY_CONTENT = "content";
//    public static final String COLUMN_HISTORY_USERID = "userid";
    
    /**
     * This class encapsulates a SQL where clause and its parameters. It makes it possible for
     * shared methods (like {@link DownloadProvider#getWhereClause(Uri, String, String[], int)}) to
     * return both pieces of information, and provides some utility logic to ease piece-by-piece
     * construction of selections.
     */
    private static class SqlSelection {
        public StringBuilder mWhereClause = new StringBuilder();
        public List<String> mParameters = new ArrayList<String>();

        public <T> void appendClause(String newClause, final T... parameters) {
            if (TextUtils.isEmpty(newClause)) {
                return;
            }
            if (mWhereClause.length() != 0) {
                mWhereClause.append(" AND ");
            }
            mWhereClause.append("(");
            mWhereClause.append(newClause);
            mWhereClause.append(")");
            if (parameters != null) {
                for (Object parameter : parameters) {
                    mParameters.add(parameter.toString());
                }
            }
        }

        public String getSelection() {
            return mWhereClause.toString();
        }

        public String[] getParameters() {
            String[] array = new String[mParameters.size()];
            return mParameters.toArray(array);
        }
    }
    
    /**
     * Creates and updated database on demand when opening it.
     * Helper class to create database the first time the provider is
     * initialized and upgrade it when a new version of the provider needs
     * an updated version of the database.
     */
    private final class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(final Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            onUpgrade(db, 0, DB_VERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            
            if (oldVersion < newVersion) {
                // 删除0.7.0版本之前的设置
                db.execSQL("DROP TABLE IF EXISTS installed");
            }
            createLogTable(db);
            createCacheTable(db);
            createHistoryRecordTable(db);
            createAddressHistoryRecordTable(db);
            createRecentlyGoodsTable(db);
        }
        
        private void createRecentlyGoodsTable(SQLiteDatabase db) {
        	 try {
                 db.execSQL("DROP TABLE IF EXISTS " + RecentlyGoodsTable.RECENTLY_GOODS_TABLE);
                 db.execSQL("CREATE TABLE " + RecentlyGoodsTable.RECENTLY_GOODS_TABLE + " (" + 
                         RecentlyGoodsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
                         RecentlyGoodsTable.COLUMN_BOUGHT + " TEXT," +
                         RecentlyGoodsTable.COLUMN_IMAGE_BIG + " TEXT," +
                         RecentlyGoodsTable.COLUMN_IMAGE_MID + " TEXT," +
                         RecentlyGoodsTable.COLUMN_IMAGE_SMALL + " TEXT," +
                         RecentlyGoodsTable.COLUMN_PRICE + " TEXT," +
                         RecentlyGoodsTable.COLUMN_PRODUCT + " TEXT," +
                         RecentlyGoodsTable.COLUMN_SHORT_TITLE + " TEXT," +
                         RecentlyGoodsTable.COLUMN_TITLE + " TEXT," +
                         RecentlyGoodsTable.COLUMN_GOODS_ID + " TEXT," +
                         RecentlyGoodsTable.COLUMN_GOODS_SHOW_TYPE + " TEXT," +
                         RecentlyGoodsTable.COLUMN_GOODS_IS_LOTTERY + " TEXT," +
                         RecentlyGoodsTable.COLUMN_GOODS_PRICE_TITLE + " TEXT," +
                         RecentlyGoodsTable.COLUMN_GOODS_PRICE + " TEXT," +
                         RecentlyGoodsTable.COLUMN_IS_APPOINTMENT + " TEXT," +
                         RecentlyGoodsTable.COLUMN_VALUE + " TEXT);");
             } catch (SQLException ex) {
                 throw ex;
             }
		}

		/*
         * 创建日志表
         */
        private void createLogTable(SQLiteDatabase db) {
            
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
                db.execSQL("CREATE TABLE " + TABLE_LOG + " (" + 
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
                        COLUMN_LOG_MODULE + " TEXT," +
                        COLUMN_LOG_LEVEL + " INTEGER," +
                        COLUMN_LOG_CONTENT + " TEXT," +
                        COLUMN_LOG_NETWORK + " TEXT," +
                        COLUMN_LOG_TIME + " INTEGER);");
            } catch (SQLException ex) {
                throw ex;
            }
        }
        
        /*
         * 创建缓存数据表
         */
        private void createCacheTable(SQLiteDatabase db) {
            
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TableCache.TABLE_CACHE);
                db.execSQL("CREATE TABLE " + TableCache.TABLE_CACHE + " (" + 
                		TableCache.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
                		TableCache.COLUMN_CACHE_KEY + " TEXT," +
                		TableCache.COLUMN_CACHE_VALUE + " TEXT," +
                		TableCache.COLUMN_CACHE_MD5 + " TEXT," +
                		TableCache.COLUMN_CACHE_TIME + " TEXT);");
            } catch (SQLException ex) {
                throw ex;
            }
        }
        /**
         *	创建搜索的历史记录表
         * */
        private void createHistoryRecordTable(SQLiteDatabase db) {
        	try {
        		db.execSQL("DROP TABLE IF EXISTS " + TableHistoryRecord.TABLE_HISTORY);
        		db.execSQL("CREATE TABLE " + TableHistoryRecord.TABLE_HISTORY + " (" + 
        				COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
        				TableHistoryRecord.COLUMN_HISTORY_USERID + " TEXT," +
        				TableHistoryRecord.COLUMN_HISTORY_CONTENT + " TEXT);");
        	} catch (SQLException ex) {
        		throw ex;
        	}
        }
        
        /**
         *	创建选择地址的历史记录表
         * */
        private void createAddressHistoryRecordTable(SQLiteDatabase db) {
        	try {
        		db.execSQL("DROP TABLE IF EXISTS " + TableAddressHistoryRecord.TABLE_ADDRESS_HISTORY);
        		db.execSQL("CREATE TABLE " + TableAddressHistoryRecord.TABLE_ADDRESS_HISTORY + " (" + 
        				COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
        				TableAddressHistoryRecord.COLUMN_ADDRESS_HISTORY_LAT + " TEXT," +
        				TableAddressHistoryRecord.COLUMN_ADDRESS_HISTORY_LNG + " TEXT," +
        				TableAddressHistoryRecord.COLUMN_ADDRESS_HISTORY_ADDRESS + " TEXT," +
        				TableAddressHistoryRecord.COLUMN_ADDRESS_HISTORY_DESC + " TEXT," +
        				TableAddressHistoryRecord.COLUMN_ADDRESS_HISTORY_CITY_ID + " TEXT);");
        	} catch (SQLException ex) {
        		throw ex;
        	}
        }
    }
    
    /* (non-Javadoc)
     * @see android.content.ContentProvider#onCreate()
     */
    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }
    
    /* (non-Javadoc)
     * @see android.content.ContentProvider#getType(android.net.Uri)
     */
    @Override
    public String getType(Uri uri) {
        
        int match = sURIMatcher.match(uri);
        switch (match) {
        case LOG:
            
            return LIST_TYPE + TABLE_LOG;
            
            
        default:
            break;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int match = sURIMatcher.match(uri);

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final String table = getTableFromUri(uri);
        long rowID = db.insert(table, null, values);
        if (rowID == -1) {
            return null;
        }

        Uri inserResult = ContentUris.withAppendedId(uri, rowID);
        notifyContentChanged(uri, match);
        return inserResult;
    }
    
    /* (non-Javadoc)
     * @see android.content.ContentProvider#delete(android.net.Uri, java.lang.String, java.lang.String[])
     */
    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {

        int match = sURIMatcher.match(uri);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final String table = getTableFromUri(uri);
        SqlSelection selection = getWhereClause(uri, where, whereArgs);
        int count = db.delete(table, selection.getSelection(), selection.getParameters());

        if (count == 0) {
            return count;
        }

        notifyContentChanged(uri, match);
        return count;
    }
    
    /* (non-Javadoc)
     * @see android.content.ContentProvider#update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[])
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        int match = sURIMatcher.match(uri);
        if (match == -1) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        final String table = getTableFromUri(uri);
        return db.update(table, values, selection, selectionArgs);
    }
    
    /* (non-Javadoc)
     * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        int match = sURIMatcher.match(uri);
        if (match == -1) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SqlSelection fullSelection = getWhereClause(uri, selection, selectionArgs);

        logVerboseQueryInfo(projection, selection, selectionArgs, sortOrder, db);

        final String table = getTableFromUri(uri);
        
        Cursor ret = null;
        
        try {
            ret = db.query(table, projection, fullSelection.getSelection(),
                    fullSelection.getParameters(), null, null, sortOrder);
        } catch (SQLiteException e) {
            // 2011/8/12 fix the no such table exception
        }

        if (ret == null) {
        }
        return ret;
    }
    
    /**
     * Notify of a change through both URIs
     */
    private void notifyContentChanged(final Uri uri, int uriMatch) {
        getContext().getContentResolver().notifyChange(uri, null);
    }
    
    /**
     * 从URI中获取表名
     * @param uri 目标Uri
     * @return 操作目标的表名
     */
    private static String getTableFromUri(final Uri uri) {
        return uri.getPathSegments().get(0);
    }
    
    /**
     * 获取SQL条件的工具方法
     * @param uri Content URI
     * @param where 条件
     * @param whereArgs 参数
     * @param uriMatch 类型
     * @return 合成的SqlSelection对象
     */
    private static SqlSelection getWhereClause(final Uri uri, final String where,
            final String[] whereArgs) {
        SqlSelection selection = new SqlSelection();
        selection.appendClause(where, whereArgs);
        return selection;
    }
    
    /**
     * 打印 【查询SQL】 详细信息
     */
    private static void logVerboseQueryInfo(String[] projection, final String selection,
            final String[] selectionArgs, final String sort, SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("starting query, database is ");
        if (db != null) {
            sb.append("not ");
        }
        sb.append("null; ");
        if (projection == null) {
            sb.append("projection is null; ");
        } else if (projection.length == 0) {
            sb.append("projection is empty; ");
        } else {
            for (int i = 0; i < projection.length; ++i) {
                sb.append("projection[");
                sb.append(i);
                sb.append("] is ");
                sb.append(projection[i]);
                sb.append("; ");
            }
        }
        sb.append("selection is ");
        sb.append(selection);
        sb.append("; ");
        if (selectionArgs == null) {
            sb.append("selectionArgs is null; ");
        } else if (selectionArgs.length == 0) {
            sb.append("selectionArgs is empty; ");
        } else {
            for (int i = 0; i < selectionArgs.length; ++i) {
                sb.append("selectionArgs[");
                sb.append(i);
                sb.append("] is ");
                sb.append(selectionArgs[i]);
                sb.append("; ");
            }
        }
        sb.append("sort is ");
        sb.append(sort);
        sb.append(".");
    }
    
    public static class TableCache {
    	
    	public static final String TABLE_CACHE = "cache";
    	/** Table ID */
        public static final String COLUMN_ID = "_id";
        
        /** Log表 */
        public static final String COLUMN_CACHE_KEY = "key";
        public static final String COLUMN_CACHE_VALUE = "value";
        public static final String COLUMN_CACHE_MD5 = "md5";
        public static final String COLUMN_CACHE_TIME = "create_time";

    }
    public static class TableHistoryRecord{
    	/**历史记录表History**/
    	public static final String TABLE_HISTORY = "historyRecord";
    	public static final String COLUMN_ID = "_id";
    	public static final String COLUMN_HISTORY_CONTENT = "content";
    	public static final String COLUMN_HISTORY_USERID = "userid";
    	
    }
    
    /**选择地址 历史记录*/
    public static class TableAddressHistoryRecord{
    	public static final String TABLE_ADDRESS_HISTORY = "addressHistoryRecord";
    	public static final String COLUMN_ID = "_id";
    	public static final String COLUMN_ADDRESS_HISTORY_LAT = "lat";
    	public static final String COLUMN_ADDRESS_HISTORY_LNG = "lng";
    	public static final String COLUMN_ADDRESS_HISTORY_ADDRESS = "address";
    	public static final String COLUMN_ADDRESS_HISTORY_DESC = "desc";
    	public static final String COLUMN_ADDRESS_HISTORY_CITY_ID = "city_id";
    	
    }
    
    /**最近浏览商品*/
    public static class RecentlyGoodsTable {
    	private static final String RECENTLY_GOODS_TABLE = "recentlyGoods";
    	public static final String COLUMN_ID = "_id";
    	/**大图*/
    	public static final String COLUMN_IMAGE_BIG = "imageBigUrl";
    	/**商品id*/
    	public static final String COLUMN_GOODS_ID = "goods_id";
    	/**中图*/
    	public static final String COLUMN_IMAGE_MID = "imageMidUrl";
    	/**小图*/
    	public static final String COLUMN_IMAGE_SMALL = "imageSmallUrl";
    	/**商品名称*/
    	public static final String COLUMN_PRODUCT = "product";
    	/**价格*/
    	public static final String COLUMN_PRICE = "price";
    	/**原价*/
    	public static final String COLUMN_VALUE = "value";
    	/**购买人数*/
    	public static final String COLUMN_BOUGHT = "bought";
    	/***/
    	public static final String COLUMN_TITLE = "title";
    	/***/
    	public static final String COLUMN_SHORT_TITLE = "short_title";
    	public static final String COLUMN_GOODS_SHOW_TYPE = "goods_show_type";
    	/**是否参与抽奖*/
    	public static final String COLUMN_GOODS_IS_LOTTERY = "is_lottery";
    	/**拉手价标签文字*/
    	public static final String COLUMN_GOODS_PRICE_TITLE = "price_title";
    	/**拉手价格。未登录状态时，此节点会显示 “XX元起”*/
    	public static final String COLUMN_GOODS_PRICE = "act_price";
    	/**是否免预约*/
    	public static final String COLUMN_IS_APPOINTMENT = "is_appointment";
    }
    
    @Override
    public ContentProviderResult[] applyBatch(
    		ArrayList<ContentProviderOperation> operations)
    		throws OperationApplicationException {
    	return super.applyBatch(operations);
    }
}
