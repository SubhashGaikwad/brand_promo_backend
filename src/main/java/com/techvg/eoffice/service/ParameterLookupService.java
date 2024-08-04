package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.ParameterLookup;
import com.techvg.eoffice.repository.ParameterLookupRepository;
import com.techvg.eoffice.service.dto.ParameterLookupDTO;
import com.techvg.eoffice.service.mapper.ParameterLookupMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ParameterLookup}.
 */
@Service
@Transactional
public class ParameterLookupService {

    private final Logger log = LoggerFactory.getLogger(ParameterLookupService.class);

    private final ParameterLookupRepository parameterLookupRepository;

    private final ParameterLookupMapper parameterLookupMapper;

    public ParameterLookupService(ParameterLookupRepository parameterLookupRepository, ParameterLookupMapper parameterLookupMapper) {
        this.parameterLookupRepository = parameterLookupRepository;
        this.parameterLookupMapper = parameterLookupMapper;
    }

    /**
     * Save a parameterLookup.
     *
     * @param parameterLookupDTO the entity to save.
     * @return the persisted entity.
     */
    public ParameterLookupDTO save(ParameterLookupDTO parameterLookupDTO) {
        log.debug("Request to save ParameterLookup : {}", parameterLookupDTO);
        ParameterLookup parameterLookup = parameterLookupMapper.toEntity(parameterLookupDTO);
        parameterLookup = parameterLookupRepository.save(parameterLookup);
        return parameterLookupMapper.toDto(parameterLookup);
    }

    /**
     * Update a parameterLookup.
     *
     * @param parameterLookupDTO the entity to save.
     * @return the persisted entity.
     */
    public ParameterLookupDTO update(ParameterLookupDTO parameterLookupDTO) {
        log.debug("Request to save ParameterLookup : {}", parameterLookupDTO);
        ParameterLookup parameterLookup = parameterLookupMapper.toEntity(parameterLookupDTO);
        parameterLookup = parameterLookupRepository.save(parameterLookup);
        return parameterLookupMapper.toDto(parameterLookup);
    }

    /**
     * Partially update a parameterLookup.
     *
     * @param parameterLookupDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ParameterLookupDTO> partialUpdate(ParameterLookupDTO parameterLookupDTO) {
        log.debug("Request to partially update ParameterLookup : {}", parameterLookupDTO);

        return parameterLookupRepository
            .findById(parameterLookupDTO.getId())
            .map(existingParameterLookup -> {
                parameterLookupMapper.partialUpdate(existingParameterLookup, parameterLookupDTO);

                return existingParameterLookup;
            })
            .map(parameterLookupRepository::save)
            .map(parameterLookupMapper::toDto);
    }

    /**
     * Get all the parameterLookups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ParameterLookupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParameterLookups");
        return parameterLookupRepository.findAll(pageable).map(parameterLookupMapper::toDto);
    }

    /**
     * Get one parameterLookup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParameterLookupDTO> findOne(Long id) {
        log.debug("Request to get ParameterLookup : {}", id);
        return parameterLookupRepository.findById(id).map(parameterLookupMapper::toDto);
    }

    /**
     * Delete the parameterLookup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParameterLookup : {}", id);
        parameterLookupRepository.deleteById(id);
    }
}
