package edu.csumb.caitlin.lo.cst438_s21_proj01_group10;

import androidx.room.Room;

import org.junit.Before;
import org.junit.Test;

import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.AppDatabase;
import edu.csumb.caitlin.lo.cst438_s21_proj01_group10.db.tables.User;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void getPassword(){
        User user = new User("test","test");
        String password = user.getPassword();
        assertEquals("test",password);
        assertNotEquals("wrong",password);
    }

    @Test
    public void getUsername(){
        User user = new User("test","test");
        String username = user.getUsername();
        assertEquals("test",username);
        assertNotEquals("wrong",username);
    }
    
    @Test
    public void validatePassword(){
        User user = new User("test","test");
        String password = "test";
        assertEquals(user.getPassword(),password);
    }

    @Test
    public void setUsername(){
        User user = new User("test","test");
        user.setUsername("test1");
        assertEquals("test1",user.getUsername());
        assertNotEquals("test",user.getUsername());
    }

    @Test
    public void setPassword(){
        User user = new User("test","test");
        user.setPassword("test1");
        assertEquals("test1",user.getPassword());
        assertNotEquals("test",user.getPassword());
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }



}