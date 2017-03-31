package com.doing.architectureencapsulation.listadapter.threetype;

import android.text.TextUtils;

import com.doing.architectureencapsulation.listadapter.recycler.BaseViewHolder;
import com.doing.architectureencapsulation.listadapter.recycler.ItemViewTaker;
import com.doing.architectureencapsulation.listadapter.threetype.entity.ThreeType;

import org.w3c.dom.Text;

/**
 * Class description here
 *
 * @author doing
 * @version 1.0.0
 * @since 2017-03-30.
 */

public class TwoTextItemView implements ItemViewTaker<ThreeType>{

    @Override
    public int getLayoutId() {
        return android.R.layout.simple_list_item_2;
    }

    @Override
    public boolean getItemViewType(ThreeType data, int position) {
        return true;
    }

    @Override
    public void convert(BaseViewHolder holder, ThreeType data, int position) {
        holder.setText(android.R.id.text1, data.getUrl())
                .setText(android.R.id.text2, data.getFlag() + "");
    }
}
