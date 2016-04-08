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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.tuanche.api.utils.LogUtils;
import com.tuanche.api.vo.CacheData;
import com.tuanche.askforanswer.app.utils.LashouProvider.TableCache;

/**
 * 关于Content Provider的工具类
 *	操作数据库
 */
public class DBUtils {
    
    /**
     * 数据库操作回调函数 
     *
     * @param <T> 数据库操作结果
     */
    public static class DbOperationResultListener<T> {
        
        protected void onQueryResult(T result) {
        }
        
        protected void onInsertResult(T result) {
        }
        
    }
    
    
    public static Uri insertCache(Context context, CacheData entity) {
    	ContentValues values = new ContentValues();
    	Uri rUri=null;
    	try {
    		 	values.put(TableCache.COLUMN_CACHE_KEY, entity.getKey());
    	        values.put(TableCache.COLUMN_CACHE_VALUE, entity.getValue());
    	        values.put(TableCache.COLUMN_CACHE_MD5, entity.getMd5());
    	        values.put(TableCache.COLUMN_CACHE_TIME, entity.getTime());
    	        rUri=context.getContentResolver().insert(LashouProvider.CACHE_CONTENT_URI, values);
		} catch (Exception e) {
			LogUtils.e(e.getMessage());
		}
        return rUri;
    }
    
	public static CacheData queryCache(Context context, String key) {
		final ContentResolver cr = context.getContentResolver();
		String selection = TableCache.COLUMN_CACHE_KEY;
		String[] selectionArgs = { key };
		Cursor c = null;
		CacheData data = null;
		try {
			c = cr.query(LashouProvider.CACHE_CONTENT_URI, null, selection,
					selectionArgs, null);
			if (c != null) {
				if (c.getCount() > 0) {
					c.moveToFirst();
				}
				data = new CacheData();
				data.setKey(c.getString(c.getColumnIndex(TableCache.COLUMN_CACHE_KEY)));
				data.setValue(c.getString(c.getColumnIndex(TableCache.COLUMN_CACHE_VALUE)));
				data.setMd5(c.getString(c.getColumnIndex(TableCache.COLUMN_CACHE_MD5)));
				data.setTime(c.getString(c.getColumnIndex(TableCache.COLUMN_CACHE_TIME)));
			}
		} catch (Exception e) {
			LogUtils.e(e.toString());
		} finally {
			if (c != null) {
				c.close();
				c = null;
			}
		}

		return data;
	}
	
}
