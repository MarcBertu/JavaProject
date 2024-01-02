package com.example.javaproject.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.javaproject.room.model.User;

import java.util.List;

@Dao
public interface UserDAO {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("SELECT * FROM credentials ORDER BY username ASC")
    List<User> getAll();

    @Query("SELECT DISTINCT * FROM credentials WHERE username = :username")
    User getUserByUsername(String username);

}
