package com.bookstore.usersservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bookstore.usersservice.email.EmailSender;
import com.bookstore.usersservice.model.ApiResponse;
import com.bookstore.usersservice.model.UserDTO;
import com.bookstore.usersservice.model.UserRequestModel;
import com.bookstore.usersservice.repository.User;
import com.bookstore.usersservice.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired private JavaMailSender javaMailSender;

    @Autowired
    private EmailSender emailSender;


    public User updateUser(Long userId, UserRequestModel user) {
        Optional<User> userRepositoryById = userRepository.findById(userId);
        if (userRepositoryById.isPresent()) {
            userRepositoryById.get().setId(userId);
            userRepositoryById.get().setUsername(user.getUsername());
            userRepositoryById.get().setLastName(user.getLastName());
            userRepositoryById.get().setFirstName(user.getFirstName());
            userRepositoryById.get().setPhoneNumber(user.getPhoneNumber());
            userRepositoryById.get().setEnabled(user.getEnabled());
            return userRepository.save(userRepositoryById.get());
        } else {
            return null;
        }
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("user");
        return userRepository.save(user);
    }

    public void forgotPassword(String email) {
        User user = userRepository.findByUsername(email);
        if (user != null) {
            String password = "123456";
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/user/me")
                    .buildAndExpand(user.getId()).toUri();
            ResponseEntity.created(location)
                    .body(new ApiResponse(true, "User registered successfully@"));
            emailSender.send(
                    email,
                    buildEmail(user.getLastName()));
        } else {
            throw new InvalidCredentialsException("Email không tồn tại.");
        }
    }

    private String buildEmail(String name) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica," +
                "Arial,sans-serif;font-weight:700;color:#ffffff;" +
                "text-decoration:none;vertical-align:top;" +
                "display:inline-block\">YOUR NEW PASSWORD</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;" +
                "line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p " +
                "style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;" +
                "color:#0b0c0c\"> Password new: " +
                "</p><blockquote " +
                "style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;" +
                "padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""  + "\">123456</a> </p></blockquote>\n. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    public UserDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        UserDTO dto = new UserDTO();
        if(user != null) {
            BeanUtils.copyProperties(user, dto);
            if(passwordEncoder.matches(password, dto.getPassword())) {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withClaim("role", user.getRole())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (1 * 60 * 10000)))
                        .sign(algorithm);
                String refreshtoken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ (10080 * 60 * 10000)))
                        .sign(algorithm);
                dto.setToken(access_token);
                dto.setRefreshtoken(refreshtoken);
            } else {
                throw new InvalidCredentialsException("Tên đăng nhập hoặc mật khẩu không đúng");
            }
        } else {
            throw new InvalidCredentialsException("Tên đăng nhập hoặc mật khẩu không đúng");
        }
        return dto;
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
