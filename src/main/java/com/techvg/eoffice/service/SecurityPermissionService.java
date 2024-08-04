package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.SecurityPermission;
import com.techvg.eoffice.repository.SecurityPermissionRepository;
import com.techvg.eoffice.service.dto.SecurityPermissionDTO;
import com.techvg.eoffice.service.mapper.SecurityPermissionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SecurityPermission}.
 */
@Service
@Transactional
public class SecurityPermissionService {

    private final Logger log = LoggerFactory.getLogger(SecurityPermissionService.class);

    private final SecurityPermissionRepository securityPermissionRepository;

    private final SecurityPermissionMapper securityPermissionMapper;

    public SecurityPermissionService(
        SecurityPermissionRepository securityPermissionRepository,
        SecurityPermissionMapper securityPermissionMapper
    ) {
        this.securityPermissionRepository = securityPermissionRepository;
        this.securityPermissionMapper = securityPermissionMapper;
    }

    /**
     * Save a securityPermission.
     *
     * @param securityPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public SecurityPermissionDTO save(SecurityPermissionDTO securityPermissionDTO) {
        log.debug("Request to save SecurityPermission : {}", securityPermissionDTO);
        SecurityPermission securityPermission = securityPermissionMapper.toEntity(securityPermissionDTO);
        securityPermission = securityPermissionRepository.save(securityPermission);
        return securityPermissionMapper.toDto(securityPermission);
    }

    /**
     * Update a securityPermission.
     *
     * @param securityPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public SecurityPermissionDTO update(SecurityPermissionDTO securityPermissionDTO) {
        log.debug("Request to save SecurityPermission : {}", securityPermissionDTO);
        SecurityPermission securityPermission = securityPermissionMapper.toEntity(securityPermissionDTO);
        securityPermission = securityPermissionRepository.save(securityPermission);
        return securityPermissionMapper.toDto(securityPermission);
    }

    /**
     * Partially update a securityPermission.
     *
     * @param securityPermissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SecurityPermissionDTO> partialUpdate(SecurityPermissionDTO securityPermissionDTO) {
        log.debug("Request to partially update SecurityPermission : {}", securityPermissionDTO);

        return securityPermissionRepository
            .findById(securityPermissionDTO.getId())
            .map(existingSecurityPermission -> {
                securityPermissionMapper.partialUpdate(existingSecurityPermission, securityPermissionDTO);

                return existingSecurityPermission;
            })
            .map(securityPermissionRepository::save)
            .map(securityPermissionMapper::toDto);
    }

    /**
     * Get all the securityPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SecurityPermissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SecurityPermissions");
        return securityPermissionRepository.findAll(pageable).map(securityPermissionMapper::toDto);
    }

    /**
     * Get one securityPermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SecurityPermissionDTO> findOne(Long id) {
        log.debug("Request to get SecurityPermission : {}", id);
        return securityPermissionRepository.findById(id).map(securityPermissionMapper::toDto);
    }

    /**
     * Delete the securityPermission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SecurityPermission : {}", id);
        securityPermissionRepository.deleteById(id);
    }
}
