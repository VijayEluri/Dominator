/**
 * Copyright 2010 Daniel Ferrin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.shemnon.dominator;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ElementPopupListAdapter extends ArrayAdapter<Elements> {

//    private View[] views;
//
//    public ElementPopupListAdapter(Activity activity, Elements[] elements) {
//        super(activity, 0, elements);
// 
//        views = new View[elements.length];
//        for (int i = 0; i < elements.length; i++) {
//            views[i] = activity.getLayoutInflater().inflate(R.layout.element_popup, null);
//            ImageView imageView = (ImageView) views[i].findViewById(R.id.element_image);
//            imageView.setImageResource(AnimalElements.imageForElement(elements[i]));
//            TextView textView = (TextView) views[i].findViewById(R.id.element_text);
//            textView.setText(elements[i].name());
//        }
// 
//    }
// 
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return views[position];
//    }
    public ElementPopupListAdapter(Activity activity, Elements[] elements) {
        super(activity, 0, elements);
    }
    
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.element_popup, null);
        }
        
        ImageView imageView = (ImageView) view.findViewById(R.id.element_image);
        imageView.setImageResource(AnimalElements.imageForElement(getItem(position)));
        
        TextView textView = (TextView) view.findViewById(R.id.element_text);
        textView.setText(getItem(position).name());
        return view;
    }
}
