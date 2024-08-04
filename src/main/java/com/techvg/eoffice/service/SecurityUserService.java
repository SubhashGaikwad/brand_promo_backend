package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.SecurityUser;
import com.techvg.eoffice.repository.SecurityUserRepository;
import com.techvg.eoffice.security.SecurityUtils;
import com.techvg.eoffice.service.dto.LoginUserDTO;
import com.techvg.eoffice.service.dto.SecurityUserDTO;
import com.techvg.eoffice.service.mapper.LoginUserMapper;
import com.techvg.eoffice.service.mapper.SecurityUserMapper;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Service Implementation for managing {@link SecurityUser}.
 */
@Service
@Transactional
public class SecurityUserService {

    private final Logger log = LoggerFactory.getLogger(SecurityUserService.class);

    private final SecurityUserRepository securityUserRepository;

    private final SecurityUserMapper securityUserMapper;

    private final LoginUserMapper loginUserMapper;

    private final PasswordEncoder passwordEncoder;

    public SecurityUserService(
        SecurityUserRepository securityUserRepository,
        SecurityUserMapper securityUserMapper,
        LoginUserMapper loginUserMapper,
        PasswordEncoder passwordEncoder
    ) {
        this.securityUserRepository = securityUserRepository;
        this.securityUserMapper = securityUserMapper;
        this.loginUserMapper = loginUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save a securityUser.
     *
     * @param securityUserDTO the entity to save.
     * @return the persisted entity.
     */
    public SecurityUserDTO save(SecurityUserDTO securityUserDTO) {
        log.debug("Request to save SecurityUser : {}", securityUserDTO);
        SecurityUser securityUser = securityUserMapper.toEntity(securityUserDTO);
        if (securityUser.getPasswordHash().length() < 12) {
            String encryptedPassword = passwordEncoder.encode(securityUser.getPasswordHash());
            securityUser.setPasswordHash(encryptedPassword);
        }
        securityUser = securityUserRepository.save(securityUser);
        return securityUserMapper.toDto(securityUser);
    }

    /**
     * Partially update a securityUser.
     *
     * @param securityUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SecurityUserDTO> partialUpdate(SecurityUserDTO securityUserDTO) {
        log.debug("Request to partially update SecurityUser : {}", securityUserDTO);

        return securityUserRepository
            .findById(securityUserDTO.getId())
            .map(existingSecurityUser -> {
                securityUserMapper.partialUpdate(existingSecurityUser, securityUserDTO);

                return existingSecurityUser;
            })
            .map(securityUserRepository::save)
            .map(securityUserMapper::toDto);
    }

    /**
     * Get all the securityUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SecurityUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SecurityUsers");
        return securityUserRepository.findAll(pageable).map(securityUserMapper::toDto);
    }

    /**
     * Get all the securityUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SecurityUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return securityUserRepository.findAllWithEagerRelationships(pageable).map(securityUserMapper::toDto);
    }

    /**
     * Get one securityUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SecurityUserDTO> findOne(Long id) {
        log.debug("Request to get SecurityUser : {}", id);
        return securityUserRepository.findOneWithEagerRelationships(id).map(securityUserMapper::toDto);
    }

    /**
     * Delete the securityUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SecurityUser : {}", id);
        securityUserRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<SecurityUser> getUserWithAuthoritiesByUsername(String login) {
        return securityUserRepository.findOneWithSecurityRolesByUsername(login);
    }

    @Transactional(readOnly = true)
    public LoginUserDTO getUserWithAuthorities() {
        Optional<SecurityUser> securityUserOpt = securityUserRepository.findOneWithSecurityRolesByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );
        if (!securityUserOpt.isPresent()) {
            securityUserOpt =
                securityUserRepository.findOneWithSecurityPermissionsByUsername(
                    SecurityContextHolder.getContext().getAuthentication().getName()
                );
        }
        return securityUserOpt.map(loginUserMapper::toDto).orElse(null);
    }

    public Optional<SecurityUser> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return securityUserRepository
            .findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<SecurityUser> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return securityUserRepository
            .findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                user.setPasswordHash(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                return user;
            });
    }

    public Optional<SecurityUser> requestPasswordReset(String mail) {
        return securityUserRepository
            .findOneByEmailIgnoreCase(mail)
            .filter(SecurityUser::isActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                return user;
            });
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
            .getCurrentUserLogin()
            .flatMap(securityUserRepository::findOneByUsername)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPasswordHash();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPasswordHash(encryptedPassword);
                log.debug("Changed password for User: {}", user);
            });
    }

    /**
     * Transfer one securityUser by  another one.
     *
     * @param id the id of the entity of both users.
     * @return the entity New User.
     */
    public SecurityUserDTO transferEmployeeInwards(Long oldEmployeeId, Long newEmployeeId, String loginUserName) {
        Optional<SecurityUserDTO> newSecurityUser = this.findOne(newEmployeeId);
        SecurityUserDTO newUser = newSecurityUser.get();
        Long newUserId = newUser.getId();
        this.save(newUser);

        Optional<SecurityUserDTO> oldSecurityUser = this.findOne(oldEmployeeId);

        SecurityUserDTO oldUser = oldSecurityUser.get();
        Long oldUserId = oldUser.getId();
        oldUser.setUsername(oldUser.getUsername() + "#");
        oldUser.setEmail(oldUser.getEmail() + "#");

        oldUser.setId(newUserId);
        newUser.setId(oldUserId);

        this.save(oldUser);
        this.save(newUser);

        return newUser;
    }

    public boolean changeUserPassword(SecurityUserDTO newUser) {
        String userName = newUser.getUsername();
        String userEmail = newUser.getEmail();
        newUser.setUsername(userName.replace("#", ""));
        newUser.setEmail(userEmail.replace("#", ""));
        this.save(newUser);
        return true;
    }
}
