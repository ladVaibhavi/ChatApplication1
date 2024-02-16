package com.example.jdbidemo.persistant;

import com.example.jdbidemo.domains.User;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl {
    private final Jdbi jdbi;

    public UserRepositoryImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
    }


    public List<User> getAllUsers() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM public.\"user\"")
                        .mapToBean(User.class)
                        .list());
    }




    public User findByEmail(String email) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM public.\"user\" WHERE email = :email")
                        .bind("email", email)
                        .mapToBean(User.class)
                        .findOnly()
        );
    }

    public User addNewUser(User user) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO public.\"user\" (id,firstname,lastname,email,password) VALUES (:id,:firstname,:lastname, :email, :password)")
                        .bind("id", user.getId())
                        .bind("firstname", user.getFirstname())
                        .bind("lastname", user.getLastname())
                        .bind("email", user.getEmail())
                        .bind("password", user.getPassword())
                        .execute()
        );

        return user;
    }

    public User updateUser(Long id, User user) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE \"user\" SET username = :username, email = :email WHERE id = :id")
                        .bind("id", id)
                        .bind("username", user.getFirstname())
                        .bind("email", user.getEmail())
                        .execute()
        );
        return user;
    }

    public void deleteUser(Long id) {
        jdbi.useHandle(handle ->
                handle.createUpdate("DELETE FROM \"user\" WHERE id = :id").bind("id",id).execute());
    }
    // Implement other methods defined in UserRepository interface
}
