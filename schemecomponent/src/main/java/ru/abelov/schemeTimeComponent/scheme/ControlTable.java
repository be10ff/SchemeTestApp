package ru.abelov.schemeTimeComponent.scheme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.squareup.picasso.Picasso;

import ru.abelov.schemeTimeComponent.OnTableSelectListener;
import ru.abelov.schemeTimeComponent.R;
import ru.abelov.schemeTimeComponent.TableStatusData;
import ru.abelov.schemeTimeComponent.entity.TableEntity;

public class ControlTable extends FrameLayout {

    android.support.v7.widget.AppCompatImageView ivTable;
    View flControl;

    TableEntity tableInfo;
    TableStatusData mData;
//    float textSize;
    private int width = 0;
    private int height = 0;
    private int margin = 0;
    private float zoom = 1;

    public ControlTable(final ViewGroup container, final OnTableSelectListener listener, final TableEntity tableInfo, TableStatusData data) {
        super(container.getContext());
        View v = ((LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.control_table, this);
        ivTable =/* (android.support.v7.widget.AppCompatImageView)*/v.findViewById(R.id.ivTable);
        flControl =/* (android.support.v7.widget.AppCompatImageView)*/v.findViewById(R.id.flControl);
        this.mData = data;

        this.tableInfo = tableInfo;
        flControl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTableSelect(tableInfo);
            }
        });
//        textSize = container.getContext().getResources().getDimension(R.dimen.table_table_capasity_size);
        init();
    }

    public long getIndex() {
        return tableInfo.id;
    }

    public void onUIChanged(float zoom) {
        this.zoom = zoom;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();

        params.height = (int) (height * zoom);
        params.width = (int) (width * zoom);
        setLayoutParams(params);
        setX((tableInfo.mapX - width/2) * zoom);
        setY((tableInfo.mapY - height/2) * zoom);

//        LayoutParams lp = (FrameLayout.LayoutParams)ivStatusBuzy.getLayoutParams();
//        int m = (int) (margin * zoom);
//        lp.setMargins(m, m, m, m);
//        ivStatusBuzy.setLayoutParams(lp);
////        tvCapacity.setTextSize(textSize * zoom);

        invalidate();

    }

    private void init() {
        width = getContext().getResources().getDimensionPixelSize(R.dimen.table_list_table_size);
        height = getContext().getResources().getDimensionPixelSize(R.dimen.table_list_table_size);
        margin = getContext().getResources().getDimensionPixelSize(R.dimen.table_list_icon_margin);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (width * zoom), (int) (height * zoom));
        setLayoutParams(params);
    }

    public void onTimeChanged() {
//        if (mData.isBuzy(tableInfo)) {
//            ivStatusBuzy.setVisibility(VISIBLE);
////            tvCapacity.setVisibility(INVISIBLE);
//        } else {
//            ivStatusBuzy.setVisibility(INVISIBLE);
////            tvCapacity.setVisibility(VISIBLE);
////            tvCapacity.setText(String.valueOf(tableInfo.capacity));
//        }

//        switch(tableInfo.capacity){
//            case 0:
//                ivTable.setImageResource(R.drawable.ic_table_for_0);
//                break;
//            case 2:
//                ivTable.setImageResource(R.drawable.ic_table_for_2);
//                break;
//            case 3:
//                ivTable.setImageResource(R.drawable.ic_table_for_3);
//                break;
//            case 4:
//                ivTable.setImageResource(R.drawable.ic_table_for_4);
//                break;
//            case 5:
//                ivTable.setImageResource(R.drawable.ic_table_for_6);
//                break;
//            case 6:
//                ivTable.setImageResource(R.drawable.ic_table_for_6);
//                break;
//            case 7:
//                ivTable.setImageResource(R.drawable.ic_table_for_8);
//                break;
//            case 8:
//                ivTable.setImageResource(R.drawable.ic_table_for_8);
//                break;
//            default:
//                ivTable.setImageResource(R.drawable.ic_table_for_0);
//        }

        if(mData.isMyBookingOrder(tableInfo)){
//            Drawable wrappedDrawable = DrawableCompat.wrap(ivTable.getDrawable());
//            wrappedDrawable = wrappedDrawable.mutate();
//            wrappedDrawable.setColorFilter(getContext().getResources().getColor(R.color.table_my_order), PorterDuff.Mode.SRC_IN);
//            ivTable.setImageDrawable(wrappedDrawable);

            switch(tableInfo.capacity){
                case 0:
                    ivTable.setImageResource(R.drawable.ic_table_for_gren);
                    break;
                case 2:
                    ivTable.setImageResource(R.drawable.ic_table_for_gren_2);
                    break;
                case 3:
                    ivTable.setImageResource(R.drawable.ic_table_for_gren_3);
                    break;
                case 4:
                    ivTable.setImageResource(R.drawable.ic_table_for_gren_4);
                    break;
                case 5:
                    ivTable.setImageResource(R.drawable.ic_table_for_gren_5);
                    break;
                case 6:
                    ivTable.setImageResource(R.drawable.ic_table_for_gren_6);
                    break;
                case 7:
                    ivTable.setImageResource(R.drawable.ic_table_for_gren_8);
                    break;
                case 8:
                    ivTable.setImageResource(R.drawable.ic_table_for_gren_8);
                    break;
                default:
                    ivTable.setImageResource(R.drawable.ic_table_for_gren);
            }
//            ivTable.setImageResource(R.drawable.ic_table_8_green);
        } else if(mData.isBuzy(tableInfo)){
            switch(tableInfo.capacity){
                case 0:
                    ivTable.setImageResource(R.drawable.ic_table_for_red);
                    break;
                case 2:
                    ivTable.setImageResource(R.drawable.ic_table_for_red_2);
                    break;
                case 3:
                    ivTable.setImageResource(R.drawable.ic_table_for_red_3);
                    break;
                case 4:
                    ivTable.setImageResource(R.drawable.ic_table_for_red_4);
                    break;
                case 5:
                    ivTable.setImageResource(R.drawable.ic_table_for_red_5);
                    break;
                case 6:
                    ivTable.setImageResource(R.drawable.ic_table_for_red_6);
                    break;
                case 7:
                    ivTable.setImageResource(R.drawable.ic_table_for_red_8);
                    break;
                case 8:
                    ivTable.setImageResource(R.drawable.ic_table_for_red_8);
                    break;
                default:
                    ivTable.setImageResource(R.drawable.ic_table_for_red);
            }
//            ivTable.setImageResource(R.drawable.ic_table_8_red);
        } else {
            switch(tableInfo.capacity){
                case 0:
                    ivTable.setImageResource(R.drawable.ic_table_for_white);
                    break;
                case 2:
                    ivTable.setImageResource(R.drawable.ic_table_for_2);
                    break;
                case 3:
                    ivTable.setImageResource(R.drawable.ic_table_for_3);
                    break;
                case 4:
                    ivTable.setImageResource(R.drawable.ic_table_for_4);
                    break;
                case 5:
                    ivTable.setImageResource(R.drawable.ic_table_for_6);
                    break;
                case 6:
                    ivTable.setImageResource(R.drawable.ic_table_for_6);
                    break;
                case 7:
                    ivTable.setImageResource(R.drawable.ic_table_for_8);
                    break;
                case 8:
                    ivTable.setImageResource(R.drawable.ic_table_for_8);
                    break;
                default:
                    ivTable.setImageResource(R.drawable.ic_table_for_white);
            }
//            ivTable.setImageResource(R.drawable.ic_table_8_white);
        }

    }

    public void _onTimeChanged() {
        //todo
        String url = "http://img.bakapp.ru/image/table/file?categoryId=9&type=table&orientation=ordinal&personsCount=4";
//        url += "&access_token=" + Settings.getInstance(getContext()).getAccessToken();
        /*&personsCount=4*/
//        url += "&personsCount=" + Math.max(2, tableInfo.capacity);
        if(mData.isMyBookingOrder(tableInfo)){
            url += "&status=booked";
        } else if(mData.isBuzy(tableInfo)){
            url += "&status=busy";
        } else {
            url += "&status=free";
        }

        Picasso.with(getContext()).load(url).into(ivTable);
    }

}
