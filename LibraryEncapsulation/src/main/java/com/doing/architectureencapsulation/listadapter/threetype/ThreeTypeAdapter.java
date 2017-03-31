package com.doing.architectureencapsulation.listadapter.threetype;


import android.content.Context;

import com.doing.architectureencapsulation.listadapter.recycler.MultiItemTypeAdapter;
import com.doing.architectureencapsulation.listadapter.threetype.entity.ThreeType;

import java.util.List;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 */

public class ThreeTypeAdapter extends MultiItemTypeAdapter<ThreeType> {

    public ThreeTypeAdapter(Context context, List<ThreeType> data) {
        super(context, data);
        mManager.addItemView(new TextItemView());
        mManager.addItemView(new TwoTextItemView());
    }
}
