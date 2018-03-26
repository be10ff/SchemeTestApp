package ru.abelov.schemeTimeComponent.scheme;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import ru.abelov.schemeTimeComponent.OnTableSelectListener;
import ru.abelov.schemeTimeComponent.R;
import ru.abelov.schemeTimeComponent.TableStatusData;
import ru.abelov.schemeTimeComponent.entity.ISection;
import ru.abelov.schemeTimeComponent.entity.ITable;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by artem on 03.07.17.
 */

public class ControlTableList extends RelativeLayout implements OnTableSelectListener {

    private static final int INVALID_POINTER_ID = -1;

    public ImageView ivScheme;

    public HorizontalScrollView hScroll;

    public ScrollView vScroll;

    public RelativeLayout container;

    List<ITable> tableList;
    private List<ControlTable> controls;
    private int width;
    private int height;
    private float mLastTouchX, mLastTouchY;
    private boolean mIsScrolling = false;
    private ScaleGestureDetector mScaleDetector;
    private float scaleFactor = .5f;
    private float minScaleFactor = .2f;
    private int mActivePointerId = INVALID_POINTER_ID;

    private int mTouchSlop;

    private TableStatusData data;

    private Context context;
    private Subscription subscription;

    private OnTableSelectListener listener;

    public ControlTableList(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View v = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.table_list_view, this);

        ivScheme = v.findViewById(R.id.ivScheme);
        hScroll = v.findViewById(R.id.hScroll);
        vScroll = v.findViewById(R.id.vScroll);
        container = v.findViewById(R.id.rlContainer);

        this.context = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
        tableList = new ArrayList<>();
        controls = new ArrayList<>();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        subscription.unsubscribe();
    }

    private void init(Builder builder) {
        this.listener = builder.listener;
        this.data = builder.tableStatusData;
        this.tableList = builder.section.getTables();
        setSection(builder.section);
    }


    public void onTimeChanged() {
        if (controls != null && !controls.isEmpty()) {
            for (ControlTable c : controls) {
                c.onTimeChanged();
            }
        }
    }

    public void onUIChanged() {
        if (controls != null && !controls.isEmpty()) {
            for (ControlTable c : controls) {
                c.onUIChanged(scaleFactor);
            }
        }
        LayoutParams params = (LayoutParams) ivScheme.getLayoutParams();
        float originX = hScroll.getScrollX() + vScroll.getScrollX() + mLastTouchX;
        float originY = hScroll.getScrollY() + vScroll.getScrollY() + mLastTouchY;

        float relativeX = originX / params.width;
        float relativeY = originY / params.height;
        params.height = (int) (height * scaleFactor);
        params.width = (int) (width * scaleFactor);
        float newX = relativeX * width * scaleFactor;
        float newY = relativeY * height * scaleFactor;

        final float newCenterY = newY - getHeight() / 2;
        final float newCenterX = newX - getWidth() / 2;

        ivScheme.setLayoutParams(params);

        hScroll.post(new Runnable() {
            @Override
            public void run() {
                hScroll.scrollTo((int) newCenterX, (int) newCenterY);
            }
        });

        vScroll.post(new Runnable() {
            @Override
            public void run() {
                vScroll.scrollTo((int) newCenterX, (int) newCenterY);
            }
        });
    }


    private void add(final ControlTable control) {
        container.addView(control);
        controls.add(control);
    }

    public void setSection(final ISection section) {
        this.tableList = section.getTables();
        subscription = Observable.combineLatest(
                Observable.create(new Observable.OnSubscribe<Pair<Integer, Integer>>() {
                    @Override
                    public void call(final Subscriber<? super Pair<Integer, Integer>> subscriber) {
                        Target target = new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                width = bitmap.getWidth();
                                height = bitmap.getHeight();
                                ivScheme.setImageBitmap(bitmap);
                                subscriber.onNext(new Pair<Integer, Integer>(width, height));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                subscriber.onError(new NetworkErrorException());
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        };
                        ivScheme.setTag(target);

                        String url = section.getSectionURL();

                        Picasso.with(context).load(url).into(target);

                    }
                }).filter(new Func1<Pair<Integer, Integer>, Boolean>() {
                    @Override
                    public Boolean call(Pair<Integer, Integer> integerIntegerPair) {
                        return integerIntegerPair.first > 0 && integerIntegerPair.second > 0;
                    }
                }),
                Observable.create(new Observable.OnSubscribe<Pair<Integer, Integer>>() {
                    @Override
                    public void call(final Subscriber<? super Pair<Integer, Integer>> subscriber) {

                        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                subscriber.onNext(new Pair<Integer, Integer>(getWidth(), getHeight()));
                            }
                        });

                    }
                }).filter(new Func1<Pair<Integer, Integer>, Boolean>() {
                    @Override
                    public Boolean call(Pair<Integer, Integer> integerIntegerPair) {
                        return integerIntegerPair.first > 0 && integerIntegerPair.second > 0;
                    }
                }),
                new Func2<Pair<Integer, Integer>, Pair<Integer, Integer>, Float>() {
                    @Override
                    public Float call(Pair<Integer, Integer> integerIntegerPair, Pair<Integer, Integer> integerIntegerPair2) {
                        Float res = Math.min(integerIntegerPair2.first / (float) integerIntegerPair.first, integerIntegerPair2.second / (float) integerIntegerPair.second);
                        return res;
                    }
                }).subscribe(new Subscriber<Float>() {
            @Override
            public void onCompleted() {
                int i = 90;
            }

            @Override
            public void onError(Throwable e) {
                int i = 90;
            }

            @Override
            public void onNext(Float res) {
                scaleFactor = res;
                minScaleFactor = res;
                onUIChanged();
            }
        });

        if (tableList != null) {
            for (ITable table : tableList) {
                add(new ControlTable(container, this, table, data));
            }
        }

        if (controls != null && !controls.isEmpty()) {
            for (ControlTable c : controls) {
                c.onUIChanged(scaleFactor);
                c.onTimeChanged();
            }
        }

    }

    @Override
    public void onTableSelect(ITable table) {
        if (listener != null) {
            listener.onTableSelect(table);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = MotionEventCompat.getActionMasked(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mIsScrolling = false;
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchX = ev.getX();
                mLastTouchY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE: {

                if (mIsScrolling) {
                    return true;
                }

                final int xDiff = (int) Math.round(Math.sqrt(Math.pow(mLastTouchX - ev.getX(), 2) + Math.pow(mLastTouchY - ev.getY(), 2)));

                if (xDiff > mTouchSlop) {
                    mIsScrolling = true;
                    return true;
                }
                break;
            }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        mScaleDetector.onTouchEvent(event);

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                mActivePointerId = event.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = event.findPointerIndex(mActivePointerId);
                try {
                    float X = event.getX(pointerIndex);
                    float Y = event.getY(pointerIndex);
                    vScroll.scrollBy((int) (mLastTouchX - X), (int) (mLastTouchY - Y));
                    hScroll.scrollBy((int) (mLastTouchX - X), (int) (mLastTouchY - Y));
                    mLastTouchX = X;
                    mLastTouchY = Y;
                } catch (IllegalArgumentException e) {

                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = event.getX(newPointerIndex);
                    mLastTouchY = event.getY(newPointerIndex);
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
            }
        }

        return true;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float scaleFactorOrigin = scaleFactor;
            scaleFactor *= detector.getScaleFactor();

            scaleFactor = Math.max(minScaleFactor, Math.min(scaleFactor, 2.0f));
            float delta = scaleFactor / scaleFactorOrigin;

            float sX = hScroll.getScrollX() + vScroll.getScrollX();
            final float scrollX = detector.getFocusX() * (delta - 1) + sX * delta;

            float sY = hScroll.getScrollY() + vScroll.getScrollY();
            final float scrollY = detector.getFocusY() * (delta - 1) + sY * delta;

            for (ControlTable c : controls) {
                c.onUIChanged(scaleFactor);
            }

            LayoutParams params = (LayoutParams) ivScheme.getLayoutParams();
            params.height = (int) (height * scaleFactor);
            params.width = (int) (width * scaleFactor);
            ivScheme.setLayoutParams(params);

            hScroll.post(new Runnable() {
                @Override
                public void run() {
                    hScroll.scrollTo((int) scrollX, (int) scrollY);
                }
            });

            vScroll.post(new Runnable() {
                @Override
                public void run() {
                    vScroll.scrollTo((int) scrollX, (int) scrollY);
                }
            });

            return true;
        }
    }

    public static class Builder {
        private ControlTableList cPlace;
        private OnTableSelectListener listener;
        private ISection section;
        private TableStatusData tableStatusData;

        public Builder(ControlTableList cPlace) {
            this.cPlace = cPlace;
        }

        public Builder setListener(OnTableSelectListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setTimeLine(ISection section) {
            this.section = section;
            return this;
        }

        public Builder setTableStatusData(TableStatusData tableStatusData) {
            this.tableStatusData = tableStatusData;
            return this;
        }

        public Builder build() {
            cPlace.init(this);
            return this;
        }
    }

}
