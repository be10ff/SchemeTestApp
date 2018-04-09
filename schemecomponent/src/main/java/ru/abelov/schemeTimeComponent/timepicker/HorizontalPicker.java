package ru.abelov.schemeTimeComponent.timepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.List;

import ru.abelov.schemeTimeComponent.OnItemClickedListener;
import ru.abelov.schemeTimeComponent.R;
import ru.abelov.schemeTimeComponent.TableStatusData;
import ru.abelov.schemeTimeComponent.entity.Hour;


public class HorizontalPicker extends LinearLayout implements HorizontalPickerListener, OnItemClickedListener {

    //ui
    private int transparentColor;
    private int primaryTextColor;
    private int disableTextColor;
    private int disableColor;
    private int enableColor;
    private int selectionColor;
    private int buzyColor;

    private int backgroundDay;
    private int backgroundPastDay;

    private int leftArrow;
    private int rightArrow;
    private int plusButton;
    private int minusButton;

    RecyclerView rvDays;
    View vHover;

    ImageView ivLeft;
    ImageView ivPlus;
    ImageView ivRight;
    ImageView ivMinus;

    private DatePickerListener listener;
    private HorizontalPickerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private TableStatusData tableStatusData;
    private List<Hour> timeLine;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE: {
                    onStopDraggingPicker();
                    break;
                }
                case RecyclerView.SCROLL_STATE_DRAGGING: {
                    onDraggingPicker();
                    break;
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };


    public HorizontalPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        internInit();

    }

    public HorizontalPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
        internInit();
    }

    private void init(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalPicker);

        transparentColor = a.getResourceId(R.styleable.HorizontalPicker_transparent_color, R.color.transparent);
        primaryTextColor = a.getResourceId(R.styleable.HorizontalPicker_primary_text_color, R.color.white);
        disableTextColor = a.getResourceId(R.styleable.HorizontalPicker_disable_text_color, R.color.datapickerWhiteSemitransparent);
        disableColor = a.getResourceId(R.styleable.HorizontalPicker_disable_color, R.color.datapickerGray);
        enableColor = a.getResourceId(R.styleable.HorizontalPicker_enable_color, R.color.datapickerDark);
        selectionColor = a.getResourceId(R.styleable.HorizontalPicker_selection_color, R.color.datapickerGreen);
        buzyColor = a.getResourceId(R.styleable.HorizontalPicker_buzy_color, R.color.datapickerRed);

        backgroundDay = a.getResourceId(R.styleable.HorizontalPicker_background_day_drawable, R.drawable.bg_transparent);
        backgroundPastDay = a.getResourceId(R.styleable.HorizontalPicker_background_past_day_drawable, R.drawable.bg_past_transparent);

        leftArrow = a.getResourceId(R.styleable.HorizontalPicker_left_arrow, 0);
        rightArrow = a.getResourceId(R.styleable.HorizontalPicker_right_arrow, 0);
        plusButton = a.getResourceId(R.styleable.HorizontalPicker_plus_button, 0);
        minusButton = a.getResourceId(R.styleable.HorizontalPicker_minus_button, 0);

        a.recycle();
    }

    private void internInit() {
        View v = inflate(getContext(), R.layout.picker_layout, this);
        rvDays = (RecyclerView) v.findViewById(R.id.rvDays);
        vHover = v.findViewById(R.id.vHover);

//        ImageView ivLeft;
//        ImageView ivPlus;
//        ImageView ivRight;
//        ImageView ivMinus;
        ivLeft = (ImageView) v.findViewById(R.id.ivScrollLeft);
        if(leftArrow != 0) {
            ivLeft.setVisibility(VISIBLE);
            ivLeft.setImageResource(leftArrow);
            ivLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (layoutManager.findFirstVisibleItemPosition() < timeLine.size() - 2) {
                        smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 2);
                    }
                }
            });
        } else {
            ivLeft.setVisibility(GONE);
        }



        ivPlus = (ImageView) v.findViewById(R.id.ivIncreaseDuration);
        if(plusButton != 0) {
            ivPlus.setVisibility(VISIBLE);
            ivPlus.setImageResource(plusButton);
            ivPlus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tableStatusData.changeDuration(1)) {
                        adapter.notifyDataSetChanged();
                        listener.onTimeChanged();
                    }
                }
            });
        } else {
            ivPlus.setVisibility(GONE);
        }


        ivRight = (ImageView) v.findViewById(R.id.ivScrollRight);
        if(rightArrow != 0) {
            ivRight.setVisibility(VISIBLE);
            ivRight.setImageResource(rightArrow);
            ivRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (layoutManager.findFirstVisibleItemPosition() > 1) {
                        rvDays.getLayoutManager().scrollToPosition(layoutManager.findFirstVisibleItemPosition() - 2);
                    }
                }
            });
        } else {
            ivRight.setVisibility(GONE);
        }


        ivMinus = (ImageView)v.findViewById(R.id.ivDecreaseDuration);
        if(minusButton != 0) {
            ivMinus.setVisibility(VISIBLE);
            ivMinus.setImageResource(minusButton);
            ivMinus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tableStatusData.changeDuration(-1)) {
                        adapter.notifyDataSetChanged();
                        listener.onTimeChanged();
                    }
                }
            });
        } else {
            ivMinus.setVisibility(GONE);
        }

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvDays.setLayoutManager(layoutManager);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvDays);
        rvDays.removeOnScrollListener(onScrollListener);
        rvDays.addOnScrollListener(onScrollListener);
    }

    private void init(Builder builder) {
        this.listener = builder.listener;
        timeLine = builder.timeLine;
        tableStatusData = builder.tableStatusData;
        adapter = new HorizontalPickerAdapter.Builder(getContext())
                //data
                .setTimeLine(timeLine)
                .setListener(this)
                .setTableStatusData(tableStatusData)
                //ui
                .setTransparentColor(transparentColor)
                .setPrimaryTextColor(primaryTextColor)
                .setDisableTextColor(disableTextColor)
                .setEnableColor(enableColor)
                .setDisableColor(disableColor)
                .setSelectionColor(selectionColor)
                .setBuzyColor(buzyColor)

                .setDayBackgroundId(backgroundDay)
                .setPastDayBackgroundId(backgroundPastDay)

                .build();
        adapter.notifyDataSetChanged();
        rvDays.setAdapter(adapter);

    }

    @Override
    public void onClickView(TimeTableHolder holder) {

        onSelectPosition(holder.getAdapterPosition());
    }

    public void update() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void onSetInitialPosition(int adapterPosition) {
        if (adapter.getItemCount() > 0) {
            if (adapterPosition < 0) {
                adapterPosition = 0;
            }
            if (adapterPosition >= adapter.getItemCount()) {
                adapterPosition = adapter.getItemCount() - 1;
            }

            smoothScrollToPosition(adapterPosition);
        }
    }

    public void onSelectPosition(int adapterPosition) {
        if (adapterPosition < 0) {
            adapterPosition = 0;
        }
        if (adapterPosition >= adapter.getItemCount()) {
            adapterPosition = adapter.getItemCount() - 1;
        }

        if (tableStatusData.onPeriodClick(adapter.getItem(adapterPosition).getMillis())) {
            adapter.notifyDataSetChanged();
            listener.onTimeChanged();
        }

    }

    public void smoothScrollToPosition(final int position) {
        final RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(getContext());
        smoothScroller.setTargetPosition(position);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutManager.startSmoothScroll(smoothScroller);
            }
        }, 100);


    }

    @Override
    public void onStopDraggingPicker() {
        if (vHover.getVisibility() == VISIBLE)
            vHover.setVisibility(INVISIBLE);
    }

    @Override
    public void onDraggingPicker() {
        if (vHover.getVisibility() == INVISIBLE)
            vHover.setVisibility(VISIBLE);
    }

    private static class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return - viewStart;
        }

    }

    public static class Builder {
        private HorizontalPicker picker;
        private DatePickerListener listener;
        private List<Hour> timeLine;
        private TableStatusData tableStatusData;

        public Builder(HorizontalPicker picker) {
            this.picker = picker;
        }

        public Builder setListener(DatePickerListener listener) {
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

        public Builder build() {
            picker.init(this);
            return this;
        }

        public Builder setInitialPosition(){
            int difference = 0;

            if(tableStatusData != null ) {
                difference = (int) ((tableStatusData.getOrderStart() - tableStatusData.getOpenTime()) / 1800000L);
            }
            picker.onSetInitialPosition(difference);
            return this;
        }
    }
}
