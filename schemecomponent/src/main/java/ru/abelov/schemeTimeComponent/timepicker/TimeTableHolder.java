package ru.abelov.schemeTimeComponent.timepicker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.abelov.schemeTimeComponent.OnItemClickedListener;
import ru.abelov.schemeTimeComponent.R;

/**
 * Created by artem on 07.07.17.
 */

public class TimeTableHolder extends RecyclerView.ViewHolder {

    public TextView tvTime;

    public View vLeft;

    public View vRight;

    public View vBottomLeft;

    public View vBottomRight;

    OnItemClickedListener listener;

    View itemView;

    public TimeTableHolder(View itemView, final OnItemClickedListener listener) {
        super(itemView);
        this.listener = listener;
        this.itemView = itemView;

        tvTime = itemView.findViewById(R.id.tvTime);
        vLeft = itemView.findViewById(R.id.vLeft);
        vRight = itemView.findViewById(R.id.vRight);
        vBottomLeft = itemView.findViewById(R.id.vBottomLeft);
        vBottomRight = itemView.findViewById(R.id.vBottomRight);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClickView(TimeTableHolder.this);
                }
            }
        });

    }
}
