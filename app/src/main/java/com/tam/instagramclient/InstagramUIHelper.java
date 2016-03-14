package com.tam.instagramclient;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by toan on 3/14/2016.
 */
public class InstagramUIHelper {
    public final static String INSTAGRAM_COLOR_CODE = "#3f729b";

    private static ArrayList<int[]> getSpans(String body, char prefix) {
        ArrayList<int[]> spans = new ArrayList<int[]>();

        Pattern pattern = Pattern.compile(prefix + "\\w+");
        Matcher matcher = pattern.matcher(body);

        // Check all occurrences
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }

        return  spans;
    }

    public static Spanned formatUserName(String userName) {
        return Html.fromHtml("<b><font color='" + INSTAGRAM_COLOR_CODE + "'>" + userName + "</font></b>");
    }

    public static Spanned formatCaption(String userName, String caption) {
        return Html.fromHtml("<b><font color='" + INSTAGRAM_COLOR_CODE + "'>" + userName + "</font></b> " + caption);
    }

    public static String formatLikesCount(int likesCount) {
        if (likesCount == 0) {
            return "";
        } else if (likesCount == 1) {
            return String.valueOf(likesCount) + " like";
        }
        return String.valueOf(likesCount) + " likes";
    }


    @NonNull
    public static SpannableStringBuilder formatComment(String commentText) {
        ArrayList<int[]> hashtagSpans = getSpans(commentText, '#');
        ArrayList<int[]> usertagSpans = getSpans(commentText, '@');
        SpannableStringBuilder commentSpan = new SpannableStringBuilder(commentText);
        ForegroundColorSpan colorStyle = new ForegroundColorSpan(Color.parseColor(INSTAGRAM_COLOR_CODE));
        StyleSpan boldStyle = new StyleSpan(Typeface.BOLD);
        for (int j = 0; j < hashtagSpans.size(); j++) {
            int[] span = hashtagSpans.get(j);
            int hashtagStart = span[0];
            int hashtagEnd = span[1];
            commentSpan.setSpan(colorStyle, hashtagStart, hashtagEnd, 0);
            commentSpan.setSpan(boldStyle, hashtagStart, hashtagEnd, 0);
        }
        for (int m = 0; m < usertagSpans.size(); m++) {
            int[] span = usertagSpans.get(m);
            int usertagStart = span[0];
            int usertagEnd = span[1];
            commentSpan.setSpan(colorStyle, usertagStart, usertagEnd, 0);
            commentSpan.setSpan(boldStyle, usertagStart, usertagEnd, 0);
        }
        return commentSpan;
    }
}
