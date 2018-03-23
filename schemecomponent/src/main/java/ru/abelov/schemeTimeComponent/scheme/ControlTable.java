package ru.abelov.schemeTimeComponent.scheme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ru.abelov.schemeTimeComponent.OnTableSelectListener;
import ru.abelov.schemeTimeComponent.R;
import ru.abelov.schemeTimeComponent.TableStatusData;
import ru.abelov.schemeTimeComponent.entity.ITable;

public class ControlTable extends FrameLayout {

    ImageView ivTable;
    View flControl;

    ITable tableInfo;
    TableStatusData mData;
    private int width = 0;
    private int height = 0;
    private float zoom = 1;

    public ControlTable(final ViewGroup container, final OnTableSelectListener listener, final ITable tableInfo, TableStatusData data) {
        super(container.getContext());
        View v = ((LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.table_view, this);
        ivTable = (ImageView)v.findViewById(R.id.ivTable);
        flControl = v.findViewById(R.id.flControl);
        this.mData = data;

        this.tableInfo = tableInfo;
        flControl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTableSelect(tableInfo);
            }
        });

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
