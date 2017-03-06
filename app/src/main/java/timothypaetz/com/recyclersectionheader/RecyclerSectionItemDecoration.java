package timothypaetz.com.recyclersectionheader;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by paetztm on 2/6/2017.
 */

public class RecyclerSectionItemDecoration extends RecyclerView.ItemDecoration {

    private final int             headerOffset;
    private final boolean         sticky;
    private       View            headerView;
    private       TextView        header;
    private       SectionCallback sectionCallback;

    public RecyclerSectionItemDecoration(int headerHeight, boolean sticky) {
        headerOffset = headerHeight;
        this.sticky = sticky;
    }

    public void setCallback(SectionCallback sectionCallback) {
        this.sectionCallback = sectionCallback;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect,
                             view,
                             parent,
                             state);

        int pos = parent.getChildAdapterPosition(view);
        if (sectionCallback.isSection(pos)) {
            outRect.top = headerOffset;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c,
                         parent,
                         state);

        if (headerView == null) {
            headerView = inflateHeaderView(parent);
            header = (TextView) headerView.findViewById(R.id.list_item_section_text);
            fixLayoutSize(header,
                          parent);
        }

        CharSequence previousHeader = "";
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            final int position = parent.getChildAdapterPosition(child);

            CharSequence title = sectionCallback.getSectionHeader(position);
            header.setText(title);
            if (!previousHeader.equals(title) || sectionCallback.isSection(position)) {
                drawStickyHeader(c,
                                 child,
                                 headerView);
                previousHeader = title;
            }
        }
    }

    private void drawStickyHeader(Canvas c, View child, View headerView) {
        c.save();
        if (sticky) {
            c.translate(0,
                        Math.max(0,
                                 child.getTop() - headerView.getHeight()));
        } else {
            c.translate(0,
                        child.getTop() - headerView.getHeight());
        }
        headerView.draw(c);
        c.restore();
    }

    private View inflateHeaderView(RecyclerView parent) {
        return LayoutInflater.from(parent.getContext())
                             .inflate(R.layout.recycler_section_header,
                                      parent,
                                      false);
    }

    /**
     * https://yoda.entelect.co.za/view/9627/how-to-android-recyclerview-item-decorations
     */
    private void fixLayoutSize(View view, ViewGroup parent) {

        // Create a width and height spec using the parent as an example:
        // For width we make sure that the item matches exactly what it measures from the parent.
        //  IE if layout says to match_parent it will be exactly parent.getWidth()
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(),
                                                         View.MeasureSpec.EXACTLY);
        // For the height we are going to create a spec that says it doesn't really care what is
        // calculated,
        //  even if its larger than the screen
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(),
                                                          View.MeasureSpec.EXACTLY);

        // Get the child specs using the parent spec and the padding the parent has
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                                                       parent.getPaddingLeft() + parent.getPaddingRight(),
                                                       view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                                                        parent.getPaddingTop() + parent.getPaddingBottom(),
                                                        view.getLayoutParams().height);

        // Finally we measure the sizes with the actual view which does margin and padding
        // changes to the sizes calculated
        view.measure(childWidth,
                     childHeight);

        // And now we setup the layout for the view to ensure it has the correct sizes.
        view.layout(0,
                    0,
                    view.getMeasuredWidth(),
                    view.getMeasuredHeight());
    }

    public interface SectionCallback {

        boolean isSection(int position);

        CharSequence getSectionHeader(int position);
    }
}


