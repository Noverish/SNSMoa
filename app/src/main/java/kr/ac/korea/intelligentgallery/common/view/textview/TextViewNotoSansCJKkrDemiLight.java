package kr.ac.korea.intelligentgallery.common.view.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import kr.ac.korea.intelligentgallery.util.TextUtil;

public class TextViewNotoSansCJKkrDemiLight extends TextView {

    public TextViewNotoSansCJKkrDemiLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (TextUtil.NotoSansCJKkrDemiLight != null)
            setTypeface(TextUtil.NotoSansCJKkrDemiLight);
    }

    public TextViewNotoSansCJKkrDemiLight(Context context) {
        super(context);
        if (TextUtil.NotoSansCJKkrDemiLight != null)
            setTypeface(TextUtil.NotoSansCJKkrDemiLight);
    }

    public TextViewNotoSansCJKkrDemiLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (TextUtil.NotoSansCJKkrDemiLight != null)
            setTypeface(TextUtil.NotoSansCJKkrDemiLight);
    }

}
