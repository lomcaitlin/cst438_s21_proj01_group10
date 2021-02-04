package edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db;

import androidx.room.*;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.Favorites;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.User;

@Database(entities = {User.class, Favorites.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "APP_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String FAVORITES_TABLE = "FAVORITES_TABLE";

    public abstract AppDAO getAppDAO();
}
