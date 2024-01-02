package com.example.javaproject.room.repository;

import android.app.Application;

import com.example.javaproject.room.dao.UserDAO;
import com.example.javaproject.room.database.UserDatabase;
import com.example.javaproject.room.model.User;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserRepository {

    private UserDAO userDAO;
    private List<User> users;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        userDAO = db.userDAO();
        users = userDAO.getAll();
    }

    public User insert(User user) {
        UserDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });

        return user;
    }

    public User getUser(String username) {

        AtomicReference<User> user = new AtomicReference<>();

        UserDatabase.databaseWriteExecutor.execute(() -> {
            user.set(userDAO.getUserByUsername(username));
        });

        return user.get();
    }

    public List<User> getAll() {
        return users;
    }
}
