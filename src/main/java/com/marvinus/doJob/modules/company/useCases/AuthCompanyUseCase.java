package com.marvinus.doJob.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.marvinus.doJob.modules.company.dto.AuthCompanyDTO;
import com.marvinus.doJob.modules.company.dto.AuthCompanyResponseDTO;
import com.marvinus.doJob.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {
    @Value("${secret.token}")
    private String key;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws Exception{
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/password incorrect");
                });
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if(!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(key);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("doJob")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authCompanyResponseDTO;
    }
}
