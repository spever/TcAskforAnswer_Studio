/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.tuanche.api.widget.coverflower;

import java.util.HashMap;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class FancyCoverFlowAdapter extends BaseAdapter {

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public final View getView(int i, View convertView, ViewGroup viewGroup) {
    	
        FancyCoverFlow coverFlow = (FancyCoverFlow) viewGroup;
        
        View wrappedView = null;
        FancyCoverFlowItemWrapper coverFlowItem;

        if (convertView != null) {//由于Gallery的convertView不会被复用，故此处条件永不成立
            coverFlowItem = (FancyCoverFlowItemWrapper) convertView;
            wrappedView = coverFlowItem.getChildAt(0);
            coverFlowItem.removeAllViews();
        } else {
        	wrappedView = this.getCoverFlowItem(i, wrappedView, viewGroup);
        	if(wrappedView.getParent()!=null){
        		coverFlowItem = (FancyCoverFlowItemWrapper) wrappedView.getParent();
        	}else{
        		coverFlowItem = new FancyCoverFlowItemWrapper(viewGroup.getContext());
        		coverFlowItem.addView(wrappedView);
        		coverFlowItem.setLayoutParams(wrappedView.getLayoutParams());
        	}
        }


        if (wrappedView == null) {
            throw new NullPointerException("getCoverFlowItem() was expected to return a view, but null was returned.");
        }
        
        /*
         * 判断CoverFlow是否显示倒影，并对子项设置相应属性
         */
        final boolean isReflectionEnabled = coverFlow.isReflectionEnabled();
        coverFlowItem.setReflectionEnabled(isReflectionEnabled);
        if(isReflectionEnabled) {
            coverFlowItem.setReflectionGap(coverFlow.getReflectionGap());
            coverFlowItem.setReflectionRatio(coverFlow.getReflectionRatio());
        }

        return coverFlowItem;
    }
    

    // =============================================================================
    // Abstract methods
    // =============================================================================

    public abstract View getCoverFlowItem(int position, View reusableView, ViewGroup parent);
}
