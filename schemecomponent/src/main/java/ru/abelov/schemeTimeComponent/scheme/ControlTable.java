package ru.abelov.schemeTimeComponent.scheme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ru.abelov.schemeTimeComponent.OnTableSelectListener;
import ru.abelov.schemeTimeComponent.R;
import ru.abelov.schemeTimeComponent.TableStatusData;
import ru.abelov.schemeTimeComponent.entity.ITable;

public class ControlTable extends FrameLayout {

    android.support.v7.widget.AppCompatImageView ivTable;
    View flControl;

    ITable tableInfo;
    TableStatusData mData;
//    float textSize;
    private int width = 0;
    private int height = 0;
//    private int margin = 0;
    private float zoom = 1;

    public ControlTable(final ViewGroup container, final OnTableSelectListener listener, final ITable tableInfo, TableStatusData data) {
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
        return tableInfo.getId();
    }

    public void onUIChanged(float zoom) {
        this.zoom = zoom;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();

        params.height = (int) (height * zoom);
        params.width = (int) (width * zoom);
        setLayoutParams(params);
        setX((tableInfo.getX() - width/2) * zoom);
        setY((tableInfo.getY() - height/2) * zoom);
        invalidate();

    }

    private void init() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (width * zoom), (int) (height * zoom));
        setLayoutParams(params);
    }

    public void _onTimeChanged() {

        if(mData.isMyBookingOrder(tableInfo)){

            switch(tableInfo.getCapacity()){
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
            switch(tableInfo.getCapacity()){
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
            switch(tableInfo.getCapacity()){
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
        }

    }
//    String url = "http://img.bakapp.ru/image/table/file?categoryId=9&type=table&orientation=ordinal&personsCount=4";
public void onTimeChanged() {

    String url = tableInfo.getImageUrl();

    if(mData.isMyBookingOrder(tableInfo)){
        url += "&status=booked";
    } else if(mData.isBuzy(tableInfo)){
        url += "&status=busy";
    } else {
        url += "&status=free";
    }

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            ivTable.setImageBitmap(bitmap);

            onUIChanged(zoom);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    ivTable.setTag(target);

    Picasso.with(getContext()).load(url).into(target);


}

}
