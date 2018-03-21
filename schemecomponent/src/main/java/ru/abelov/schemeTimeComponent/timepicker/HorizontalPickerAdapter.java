package ru.abelov.schemeTimeComponent.timepicker;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.abelov.schemeTimeComponent.OnItemClickedListener;
import ru.abelov.schemeTimeComponent.R;
import ru.abelov.schemeTimeComponent.TableStatusData;
import ru.abelov.schemeTimeComponent.entity.Hour;


public class HorizontalPickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int primaryTextColor;
    private final int disableTextColor;
    private OnItemClickedListener listener;
    private Context context;
    private List<Hour> items;
    private TableStatusData tableStatusData;


    public HorizontalPickerAdapter(Builder builder) {
        this.context = builder.context;
        items = new ArrayList<>();
        items = builder.timeLine;
        this.listener = builder.listener;
        this.tableStatusData = builder.tableStatusData;
        this.primaryTextColor = context.getResources().getColor(R.color.white);
        this.disableTextColor = context.getResources().getColor(R.color.datapickerWhiteSemitransparent);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeTableHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_hour_picker, parent, false), new OnItemClickedListener() {
            @Override
            public void onClickView(TimeTableHolder holder) {
                listener.onClickView(holder);
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, int position) {
        Hour item = getItem(position);
        TimeTableHolder holder = (TimeTableHolder) h;
        holder.tvTime.setText(item.getTime());

        holder.vLeft.setBackgroundResource(R.color.transparent);
        holder.vRight.setBackgroundResource(R.color.transparent);
        holder.vBottomLeft.setBackgroundResource(R.color.transparent);
        holder.vBottomRight.setBackgroundResource(R.color.transparent);

        switch (tableStatusData.getGray(item.getMillis())) {
            case 0b11:
                holder.vLeft.setBackgroundResource(R.color.datapickerGray);
                holder.vRight.setBackgroundResource(R.color.datapickerGray);
                holder.tvTime.setBackgroundResource(R.drawable.background_day_past_transparent);
                holder.tvTime.setTextColor(disableTextColor);
                break;
            case 0b10:
                holder.vLeft.setBackgroundResource(R.color.datapickerGray);
                holder.vRight.setBackgroundResource(R.color.datapickerDark);
                holder.tvTime.setBackgroundResource(R.drawable.background_day_transparent);
                holder.tvTime.setTextColor(primaryTextColor);
                break;
            case 0b01:
                holder.vLeft.setBackgroundResource(R.color.datapickerDark);
                holder.vRight.setBackgroundResource(R.color.datapickerGray);
                holder.tvTime.setBackgroundResource(R.drawable.background_day_transparent);
                holder.tvTime.setTextColor(primaryTextColor);
                break;
            default:
                holder.vLeft.setBackgroundResource(R.color.datapickerDark);
                holder.vRight.setBackgroundResource(R.color.datapickerDark);
                holder.tvTime.setBackgroundResource(R.drawable.background_day_transparent);
                holder.tvTime.setTextColor(primaryTextColor);
                break;
        }

        switch (tableStatusData.getOrange(item.getMillis())) {
            case 0b01:
                holder.vRight.setBackgroundResource(R.color.datapickerGreen);
                break;
            case 0b11:
                holder.vLeft.setBackgroundResource(R.color.datapickerGreen);
                holder.vRight.setBackgroundResource(R.color.datapickerGreen);
                break;
            case 0b10:
                holder.vLeft.setBackgroundResource(R.color.datapickerGreen);
                break;
//            case 0b00:
//                holder.vLeft.setBackgroundResource(R.color.datapickerGreen);
//                break;
            default:
        }

        switch (tableStatusData.getVacant(item.getMillis())) {
            case 0b11:
                holder.vBottomLeft.setBackgroundResource(R.color.datapickerRed);
                holder.vBottomRight.setBackgroundResource(R.color.datapickerRed);
                break;
            case 0b10:
                holder.vBottomLeft.setBackgroundResource(R.color.datapickerRed);
                break;
            case 0b01:
                holder.vBottomRight.setBackgroundResource(R.color.datapickerRed);
                break;

        }

    }

    public Hour getItem(int position) {
        return items.get(position);
    }

    public List<Hour> getItems() {
        return items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Builder {
        public TableStatusData tableStatusData;
        private Context context;
        private OnItemClickedListener listener;
        private List<Hour> timeLine;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setListener(OnItemClickedListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setTimeLine(List<Hour> timeLine) {
            this.timeLine = timeLine;
            return this;
        }

        public Builder setTableStatusData(TableStatusData tableStatusData) {
            this.tableStatusData = tableStatusData;
            return this;
        }

        public HorizontalPickerAdapter build() {
            return new HorizontalPickerAdapter(this);
        }
    }

}