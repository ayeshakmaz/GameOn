package com.example.gameon.view.viewgroup;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class FlyOutContainer extends LinearLayout {

	// References to groups contained in this view.
	private View menu;
	private View content;
    private GradientDrawable mShadowDrawable;
    private Paint mMenuOverlayPaint = new Paint();
    
	// Layout Constants
	protected static final int menuMargin = 150;

	public enum MenuState {
		CLOSED, OPEN, CLOSING, OPENING
	};

	// Position information attributes
	protected int currentContentOffset = 0;
	protected MenuState menuCurrentState = MenuState.CLOSED;

	// Animation objects
	protected Scroller menuAnimationScroller = new Scroller(this.getContext(),
			new LinearInterpolator());
	// protected Scroller menuAnimationScroller = new
	// Scroller(this.getContext(),
	// new SmoothInterpolator());
	protected Runnable menuAnimationRunnable = new AnimationRunnable();
	protected Handler menuAnimationHandler = new Handler();

	// Animation constants
	private static final int menuAnimationDuration = 1000;
	private static final int menuAnimationPollingInterval = 16;

	public FlyOutContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	    int[] colors = {Color.TRANSPARENT, Color.BLACK};
	    mShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
	}

	public FlyOutContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	    int[] colors = {Color.TRANSPARENT, Color.BLACK};
	    mShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
	}

	public FlyOutContainer(Context context) {
		super(context);
	    int[] colors = {Color.TRANSPARENT, Color.BLACK};
	    mShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		this.menu = this.getChildAt(0);
		this.content = this.getChildAt(1);

		this.menu.setVisibility(View.GONE);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (changed)
			this.calculateChildDimensions();

		this.menu.layout(left, top, right - menuMargin, bottom);

		this.content.layout(left + this.currentContentOffset, top, right
				+ this.currentContentOffset, bottom);

	}

	public void toggleMenu() {
		switch (this.menuCurrentState) {
		case CLOSED:
			this.menuCurrentState = MenuState.OPENING;
			this.menu.setVisibility(View.VISIBLE);
			this.menuAnimationScroller.startScroll(0, 0, this.getMenuWidth(),
					0, menuAnimationDuration);
			break;
		case OPEN:
			this.menuCurrentState = MenuState.CLOSING;
			this.menuAnimationScroller.startScroll(this.currentContentOffset,
					0, -this.currentContentOffset, 0, menuAnimationDuration);
			break;
		default:
			return;
		}

		this.menuAnimationHandler.postDelayed(this.menuAnimationRunnable,
				menuAnimationPollingInterval);

		this.invalidate();
	}

	private int getMenuWidth() {
		return this.menu.getLayoutParams().width;
	}

	private void calculateChildDimensions() {
		this.content.getLayoutParams().height = this.getHeight();
		this.content.getLayoutParams().width = this.getWidth();

		this.menu.getLayoutParams().width = this.getWidth() - menuMargin;
		this.menu.getLayoutParams().height = this.getHeight();
	}

	private void adjustContentPosition(boolean isAnimationOngoing) {
		int scrollerOffset = this.menuAnimationScroller.getCurrX();

		this.content.offsetLeftAndRight(scrollerOffset
				- this.currentContentOffset);

		this.currentContentOffset = scrollerOffset;

		this.invalidate();

		if (isAnimationOngoing)
			this.menuAnimationHandler.postDelayed(this.menuAnimationRunnable,
					menuAnimationPollingInterval);
		else
			this.onMenuTransitionComplete();
	}

	private void onMenuTransitionComplete() {
		switch (this.menuCurrentState) {
		case OPENING:
			this.menuCurrentState = MenuState.OPEN;
			break;
		case CLOSING:
			this.menuCurrentState = MenuState.CLOSED;
			this.menu.setVisibility(View.GONE);
			break;
		default:
			return;
		}
	}
	protected void dispatchDraw(Canvas canvas) {
	    // Draw the children
	    super.dispatchDraw(canvas);

	    if (menuCurrentState != MenuState.CLOSED) {
	        // The menu is not closed. That means we can potentially see the host
	        // overlapping it. Let's add a tiny gradient to indicate the host is
	        // sliding over the menu.
	    	mShadowDrawable.setBounds(-20, content.getTop(), 0, content.getBottom());
	        canvas.save();
	        canvas.translate(content.getLeft(), 0);
	        mShadowDrawable.draw(canvas);
	        canvas.restore();

	        final int menuWidth = menu.getWidth();
	        if (menuWidth != 0) {
	            final float opennessRatio = (menuWidth - content.getLeft()) / (float) menuWidth;

	            // We also draw an overlay over the menu indicating the menu is
	            // in the process of being visible or invisible.
	            onDrawMenuOverlay(canvas, opennessRatio);

	            // Finally we draw an arrow indicating the feature we are
	            // currently in
	           // onDrawMenuArrow(canvas, opennessRatio);
	        }
	    }
	    
	}
	private static final int MAXIMUM_MENU_ALPHA_OVERLAY = 200;

	private void onDrawMenuOverlay(Canvas canvas, float opennessRatio) {
	    final Paint menuOverlayPaint = mMenuOverlayPaint;
	    final int alpha = (int) (MAXIMUM_MENU_ALPHA_OVERLAY * opennessRatio);
	    if (alpha > 0) {
	        menuOverlayPaint.setColor(Color.argb(alpha, 0, 0, 0));
	        canvas.drawRect(0, 0, content.getLeft(), getHeight(), mMenuOverlayPaint);
	    }
	}

	protected class SmoothInterpolator implements Interpolator {

		@Override
		public float getInterpolation(float t) {
			return (float) Math.pow(t - 1, 5) + 1;
		}

	}

	protected class AnimationRunnable implements Runnable {

		@Override
		public void run() {
			FlyOutContainer.this
					.adjustContentPosition(FlyOutContainer.this.menuAnimationScroller
							.computeScrollOffset());
		}

	}
}