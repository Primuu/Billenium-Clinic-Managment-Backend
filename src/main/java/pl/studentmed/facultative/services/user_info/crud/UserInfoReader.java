package pl.studentmed.facultative.services.user_info.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.studentmed.facultative.exceptions.EntityNotFoundException;
import pl.studentmed.facultative.models.user_info.UserInfo;
import pl.studentmed.facultative.models.user_info.UserInfoLoginRequestDTO;
import pl.studentmed.facultative.models.user_info.UserInfoLoginResponseDTO;
import pl.studentmed.facultative.services.patient.crud.IPatientRepository;
import pl.studentmed.facultative.services.user_security.SessionRegistry;

@Component
@RequiredArgsConstructor
class UserInfoReader {

    private final UserInfoRepository repository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserInfo getUserInfoById(Long userInfoId) {
        return repository.findById(userInfoId)
                .orElseThrow(
                        () -> new EntityNotFoundException("userInfo", "User with id " + userInfoId + " doesn't exist.")
                );
    }

    public UserInfo getUserInfoByEmail(String userEmail) {
        return repository.findByEmail(userEmail)
                .orElseThrow(
                        () -> new EntityNotFoundException("userInfo", "User with email " + userEmail + " doesn't exist.")
                );
    }

    public boolean existsByEmailOrPesel(String email, String pesel) {
        return repository.existsByEmailOrPesel(email, pesel);
    }

    public void existsByEmail(String email) {
        if (!repository.existsByEmail(email)) {
            throw new BadCredentialsException("There is no such user with given email.");
        }
    }

    public void checkCredentials(String email, String password) {
        if (!passwordEncoder.matches(password, repository.findRegisteredUserInfoByEmail(email).getPassword())) {
            throw new BadCredentialsException("Wrong password or email entered.");
        }
    }

    public UserInfo getRegisteredUserInfoByEmail(String userEmail) {
        return repository.findRegisteredUserInfoByEmail(userEmail);
    }

}
