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

    private OnItemClickedListener listener;
    private Context context;
    private List<Hour> items;
    private TableStatusData tableStatusData;
    private int transparentColor;
    private int primaryTextColor;
    private int disableTextColor;
    private int disableColor;
    private int enableColor;
    private int selectionColor;
    private int buzyColor;

    private int backgroundDay;
    private int backgroundPastDay;

    public HorizontalPickerAdapter(Builder builder) {
        this.context = builder.context;
        items = new ArrayList<>();
        items = builder.timeLine;
        this.listener = builder.listener;
        this.tableStatusData = builder.tableStatusData;

        this.transparentColor = builder.transparentColor;
        this.primaryTextColor = builder.primaryTextColor;
        this.disableTextColor = builder.disableTextColor;
        this.disableColor = builder.disableColor;
        this.enableColor = builder.enableColor;
        this.selectionColor = builder.selectionColor;
        this.buzyColor = builder.buzyColor;

        this.backgroundDay = builder.backgroundDay;
        this.backgroundPastDay = builder.backgroundPastDay;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeTableHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_picker_layout, parent, false), new OnItemClickedListener() {
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

        holder.vLeft.setBackgroundResource(transparentColor);
        holder.vRight.setBackgroundResource(transparentColor);
        holder.vBottomLeft.setBackgroundResource(transparentColor);
        holder.vBottomRight.setBackgroundResource(transparentColor);

        switch (tableStatusData.getGray(item.getMillis())) {
            case 0b11:
                holder.vLeft.setBackgroundResource(disableColor);
                holder.vRight.setBackgroundResource(disableColor);
                holder.tvTime.setBackgroundResource(backgroundPastDay);
                holder.tvTime.setTextColor( context.getResources().getColor(disableTextColor));
                break;
            case 0b10:
                holder.vLeft.setBackgroundResource(disableColor);
                holder.vRight.setBackgroundResource(enableColor);
                holder.tvTime.setBackgroundResource(backgroundDay);
                holder.tvTime.setTextColor( context.getResources().getColor(primaryTextColor));
                break;
            case 0b01:
                holder.vLeft.setBackgroundResource(enableColor);
                holder.vRight.setBackgroundResource(disableColor);
                holder.tvTime.setBackgroundResource(backgroundDay);
                holder.tvTime.setTextColor( context.getResources().getColor(primaryTextColor));
                break;
            default:
                holder.vLeft.setBackgroundResource(enableColor);
                holder.vRight.setBackgroundResource(enableColor);
                holder.tvTime.setBackgroundResource(backgroundDay);
                holder.tvTime.setTextColor( context.getResources().getColor(primaryTextColor));
                break;
        }

        switch (tableStatusData.getOrange(item.getMillis())) {
            case 0b01:
                holder.vRight.setBackgroundResource(selectionColor);
                break;
            case 0b11:
                holder.vLeft.setBackgroundResource(selectionColor);
                holder.vRight.setBackgroundResource(selectionColor);
                break;
            case 0b10:
                holder.vLeft.setBackgroundResource(selectionColor);
                break;
//            case 0b00:
//                holder.vLeft.setBackgroundResource(R.color.datapickerGreen);
//                break;
            default:
        }

        switch (tableStatusData.getVacant(item.getMillis())) {
            case 0b11:
                holder.vBottomLeft.setBackgroundResource(buzyColor);
                holder.vBottomRight.setBackgroundResource(buzyColor);
                break;
            case 0b10:
                holder.vBottomLeft.setBackgroundResource(buzyColor);
                break;
            case 0b01:
                holder.vBottomRight.setBackgroundResource(buzyColor);
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
        private int transparentColor;
        private int primaryTextColor;
        private int disableTextColor;
        private int disableColor;
        private int enableColor;
        private int selectionColor;
        private int buzyColor;

        private int backgroundDay;
        private int backgroundPastDay;


        public Builder(Context context) {
            this.context = context;
            transparentColor = R.color.transparent;
            primaryTextColor = R.color.white;
            disableTextColor = R.color.datapickerWhiteSemitransparent;
            disableColor = R.color.datapickerGray;
            enableColor = R.color.datapickerDark;
            selectionColor = R.color.datapickerGreen;
            buzyColor = R.color.datapickerRed;

            backgroundDay = R.drawable.bg_transparent;
            backgroundPastDay = R.drawable.bg_past_transparent;
        }

        public Builder setTransparentColor(int colorResId){
            this.transparentColor = colorResId;
            return this;
        }

        public Builder setPrimaryTextColor(int colorResId){
            this.primaryTextColor = colorResId;
            return this;
        }

        public Builder setDisableTextColor(int colorResId){
            this.disableTextColor = colorResId;
            return this;
        }

        public Builder setEnableColor(int colorResId){
            this.enableColor = colorResId;
            return this;
        }

        public Builder setDisableColor(int colorResId){
            this.disableColor = colorResId;
            return this;
        }

        public Builder setSelectionColor(int colorResId){
            this.selectionColor = colorResId;
            return this;
        }

        public Builder setBuzyColor(int colorResId){
            this.buzyColor = colorResId;
            return this;
        }

        public Builder setDayBackgroundId(int drawableResId){
            this.backgroundDay = drawableResId;
            return this;
        }

        public Builder setPastDayBackgroundId(int drawableResId){
            this.backgroundPastDay = drawableResId;
            return this;
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