package edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db;

import androidx.room.*;

import java.util.List;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.*;

@Dao
public interface AppDAO {

    /* User Queries */
    @Insert
    void insert(User... user);

    @Update
    void update(User... user);

    @Delete
    void delete(User user);

    /* Get user by username */
    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username LIKE :username")
    User getUserByUsername(String username);

    /* Get user by ID */
    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userId LIKE :userId")
    User getUserById(int userId);

    @Insert
    void insert(Favorites... favorites);

    @Update
    void update(Favorites... favorites);

    @Delete
    void delete(Favorites... favorites);

    @Query("SELECT * FROM " + AppDatabase.FAVORITES_TABLE + " WHERE userId LIKE :userId")
    List<Favorites> getFavoritesByUserId(int userId);

    @Query("SELECT * FROM " + AppDatabase.FAVORITES_TABLE + " WHERE userId LIKE :userId AND type LIKE :type AND endpoint LIKE :endpoint")
    Favorites getFavoriteByPrimaryKey(int userId, String type, String endpoint);
}
