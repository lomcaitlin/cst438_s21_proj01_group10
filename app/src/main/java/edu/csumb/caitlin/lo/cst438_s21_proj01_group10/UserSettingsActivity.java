package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import android.content.Context;
import android.content.Intent;

public class UserSettingsActivity {
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, UserSettingsActivity.class);
        return intent;
    }
}