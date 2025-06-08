package com.spring.recommend_contents.user;

import com.spring.recommend_contents.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(this.passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUserByEmail(String email) {
        Optional<SiteUser> siteUser = this.userRepository.findByEmail(email);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser by email not found");
        }
    }
}
