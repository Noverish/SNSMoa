package kr.ac.korea.snsmoa.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Noverish on 2017-01-03.
 */

public class Essentials {
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void replaceFragment(FragmentActivity activity, @IdRes int layoutId, Fragment fr) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId, fr);
        fragmentTransaction.commit();
    }

    public static void changeFragment(FragmentActivity activity, @IdRes int layoutId, Fragment fr) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments == null)
            fragments = new ArrayList<>();

        if(fragments.contains(fr)) {
            for(Fragment fragment : fragments) {
                if(fragment.equals(fr))
                    fragmentTransaction.show(fragment);
                else
                    fragmentTransaction.hide(fragment);
            }
        } else {
            fragmentTransaction.add(layoutId, fr);
        }
        fragmentTransaction.commit();
    }

    public static void removeFragment(FragmentActivity activity, Fragment fr) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fr);
        fragmentTransaction.commit();
    }

    public static int getPxById(Context context, @DimenRes int resid) {
        return (int) context.getResources().getDimension(resid);
    }

    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static String numberWithComma(int number) {
        return new DecimalFormat("#,###").format(number);
    }

    public static String numberWithComma(long number) {
        return new DecimalFormat("#,###").format(number);
    }

    public static String numberWithComma(String number) {
        try {
            return new DecimalFormat("#,###").format(Double.parseDouble(number));
        } catch (Exception ex) {
            return "";
        }
    }

    public static Bitmap getBitmapById(Context context, @DrawableRes int resid) {
        return BitmapFactory.decodeResource(context.getResources(), resid);
    }

    public static Bitmap combineImages(Bitmap base, Bitmap overlay, int overlayAlpha) {
        Bitmap cs = Bitmap.createBitmap(base.getWidth(), base.getHeight(), Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setAlpha(overlayAlpha);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(base, 0f, 0f, null);
        comboImage.drawBitmap(overlay, 0f, 0f, paint);

        return cs;
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static <V extends View> V setMargin(V view, Class<? extends ViewGroup> c, int left, int top, int right, int bottom) {
        if(c == LinearLayout.class) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(left, top, right, bottom);
            view.setLayoutParams(params);
        } else if (c == RelativeLayout.class) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(left, top, right, bottom);
            view.setLayoutParams(params);
        } else if (c == FrameLayout.class) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(left, top, right, bottom);
            view.setLayoutParams(params);
        } else if (c == RecyclerView.class) {
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            params.setMargins(left, top, right, bottom);
            view.setLayoutParams(params);
        }
        return view;
    }

    public static String shrinkText(String origin, int lengthOfKorean) {
        float tmp = 0;
        int index = 0;
        for(char c : origin.toCharArray()) {
            if(String.valueOf(c).matches("[a-z]")) {
                tmp += 0.6;
            } else if(String.valueOf(c).matches("[A-Z]")) {
                tmp += 0.8;
            } else if(String.valueOf(c).matches("[0-9]")) {
                tmp += 0.75;
            } else if(String.valueOf(c).matches("[() _'.]")) {
                tmp += 0.5;
            } else {
                tmp += 1;
            }
            index++;
            if(tmp >= lengthOfKorean) {
                if(tmp == origin.length())
                    return origin;
                else
                    return origin.substring(0,index - 1) + "…";
            }
        }
        return origin;
    }

    public static String getMatches(String regex, String input) {
        return getMatches(regex, input, 0);
    }

    public static String getMatches(String regex, String input, int flag) {
        Pattern pattern = Pattern.compile(regex, flag);
        Matcher matcher = pattern.matcher(input);

        if(matcher.find()) {
            if(matcher.groupCount() > 1)
                Log.w("getMatches", matcher.groupCount() + "matches : " + regex  + " - " + input);

            return matcher.group();
        }
        else
            return "";
    }

    public static String unicodeToString(String str) {
        if(str == null) {
            Log.w("unicodeToString","str is null!");
            return "";
        }

        str = str.replaceAll("\\\\u200E"," ▶ ");

        Pattern pattern = Pattern.compile("(\\\\){1,2}u([0-9]|[A-F]|[a-f]){4}");
        Matcher matcher = pattern.matcher(str);

        while(matcher.find()) {
            String tmp = matcher.group();
            str = str.replace(tmp, (char) (Integer.parseInt(tmp.substring(tmp.length() - 4), 16)) + "");
        }

        return str;
    }
}
