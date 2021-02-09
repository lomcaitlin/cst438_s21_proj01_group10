package edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables;

import androidx.annotation.NonNull;
import androidx.room.*;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.*;

@Entity(tableName = AppDatabase.FAVORITES_TABLE,
        primaryKeys = {"userId", "type", "endpoint"},
        foreignKeys = @ForeignKey( entity = User.class,
                                    parentColumns = "userId",
                                    childColumns = "userId",
                                    onDelete = ForeignKey.CASCADE)
        )
public class Favorites {
    private int userId;
    @NonNull
    private String type;
    @NonNull
    private String endpoint;

    public Favorites(int userId, String type, String endpoint) {
        this.userId = userId;
        this.type = type;
        this.endpoint = endpoint;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
