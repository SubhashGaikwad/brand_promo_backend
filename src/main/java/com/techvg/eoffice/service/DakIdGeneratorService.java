package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.DakIdGenerator;
import com.techvg.eoffice.repository.DakIdGeneratorRepository;
import com.techvg.eoffice.service.dto.DakIdGeneratorDTO;
import com.techvg.eoffice.service.mapper.DakIdGeneratorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DakIdGenerator}.
 */
@Service
@Transactional
public class DakIdGeneratorService {

    private final Logger log = LoggerFactory.getLogger(DakIdGeneratorService.class);

    private final DakIdGeneratorRepository dakIdGeneratorRepository;

    private final DakIdGeneratorMapper dakIdGeneratorMapper;

    public DakIdGeneratorService(DakIdGeneratorRepository dakIdGeneratorRepository, DakIdGeneratorMapper dakIdGeneratorMapper) {
        this.dakIdGeneratorRepository = dakIdGeneratorRepository;
        this.dakIdGeneratorMapper = dakIdGeneratorMapper;
    }

    /**
     * Save a dakIdGenerator.
     *
     * @param dakIdGeneratorDTO the entity to save.
     * @return the persisted entity.
     */
    public DakIdGeneratorDTO save(DakIdGeneratorDTO dakIdGeneratorDTO) {
        log.debug("Request to save DakIdGenerator : {}", dakIdGeneratorDTO);
        DakIdGenerator dakIdGenerator = dakIdGeneratorMapper.toEntity(dakIdGeneratorDTO);
        dakIdGenerator = dakIdGeneratorRepository.save(dakIdGenerator);
        return dakIdGeneratorMapper.toDto(dakIdGenerator);
    }

    /**
     * Update a dakIdGenerator.
     *
     * @param dakIdGeneratorDTO the entity to save.
     * @return the persisted entity.
     */
    public DakIdGeneratorDTO update(DakIdGeneratorDTO dakIdGeneratorDTO) {
        log.debug("Request to save DakIdGenerator : {}", dakIdGeneratorDTO);
        DakIdGenerator dakIdGenerator = dakIdGeneratorMapper.toEntity(dakIdGeneratorDTO);
        dakIdGenerator = dakIdGeneratorRepository.save(dakIdGenerator);
        return dakIdGeneratorMapper.toDto(dakIdGenerator);
    }

    /**
     * Partially update a dakIdGenerator.
     *
     * @param dakIdGeneratorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DakIdGeneratorDTO> partialUpdate(DakIdGeneratorDTO dakIdGeneratorDTO) {
        log.debug("Request to partially update DakIdGenerator : {}", dakIdGeneratorDTO);

        return dakIdGeneratorRepository
            .findById(dakIdGeneratorDTO.getId())
            .map(existingDakIdGenerator -> {
                dakIdGeneratorMapper.partialUpdate(existingDakIdGenerator, dakIdGeneratorDTO);

                return existingDakIdGenerator;
            })
            .map(dakIdGeneratorRepository::save)
            .map(dakIdGeneratorMapper::toDto);
    }

    /**
     * Get all the dakIdGenerators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DakIdGeneratorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DakIdGenerators");
        return dakIdGeneratorRepository.findAll(pageable).map(dakIdGeneratorMapper::toDto);
    }

    /**
     * Get one dakIdGenerator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DakIdGeneratorDTO> findOne(Long id) {
        log.debug("Request to get DakIdGenerator : {}", id);
        return dakIdGeneratorRepository.findById(id).map(dakIdGeneratorMapper::toDto);
    }

    /**
     * Delete the dakIdGenerator by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DakIdGenerator : {}", id);
        dakIdGeneratorRepository.deleteById(id);
    }
}
