package com.bookstore.usersservice.email;

public interface EmailSender {
    void send(String to, String email);
}
