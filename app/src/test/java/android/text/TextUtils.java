package android.text;

import androidx.annotation.Nullable;

//Clone of Android class, to allow tests to run on code using this method
public class TextUtils {
    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
