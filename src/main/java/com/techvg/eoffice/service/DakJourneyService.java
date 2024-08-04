package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.DakJourney;
import com.techvg.eoffice.repository.DakJourneyRepository;
import com.techvg.eoffice.service.dto.DakJourneyDTO;
import com.techvg.eoffice.service.mapper.DakJourneyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DakJourney}.
 */
@Service
@Transactional
public class DakJourneyService {

    private final Logger log = LoggerFactory.getLogger(DakJourneyService.class);

    private final DakJourneyRepository dakJourneyRepository;

    private final DakJourneyMapper dakJourneyMapper;

    public DakJourneyService(DakJourneyRepository dakJourneyRepository, DakJourneyMapper dakJourneyMapper) {
        this.dakJourneyRepository = dakJourneyRepository;
        this.dakJourneyMapper = dakJourneyMapper;
    }

    /**
     * Save a dakJourney.
     *
     * @param dakJourneyDTO the entity to save.
     * @return the persisted entity.
     */
    public DakJourneyDTO save(DakJourneyDTO dakJourneyDTO) {
        log.debug("Request to save DakJourney : {}", dakJourneyDTO);
        DakJourney dakJourney = dakJourneyMapper.toEntity(dakJourneyDTO);
        dakJourney = dakJourneyRepository.save(dakJourney);
        return dakJourneyMapper.toDto(dakJourney);
    }

    /**
     * Update a dakJourney.
     *
     * @param dakJourneyDTO the entity to save.
     * @return the persisted entity.
     */
    public DakJourneyDTO update(DakJourneyDTO dakJourneyDTO) {
        log.debug("Request to save DakJourney : {}", dakJourneyDTO);
        DakJourney dakJourney = dakJourneyMapper.toEntity(dakJourneyDTO);
        dakJourney = dakJourneyRepository.save(dakJourney);
        return dakJourneyMapper.toDto(dakJourney);
    }

    /**
     * Partially update a dakJourney.
     *
     * @param dakJourneyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DakJourneyDTO> partialUpdate(DakJourneyDTO dakJourneyDTO) {
        log.debug("Request to partially update DakJourney : {}", dakJourneyDTO);

        return dakJourneyRepository
            .findById(dakJourneyDTO.getId())
            .map(existingDakJourney -> {
                dakJourneyMapper.partialUpdate(existingDakJourney, dakJourneyDTO);

                return existingDakJourney;
            })
            .map(dakJourneyRepository::save)
            .map(dakJourneyMapper::toDto);
    }

    /**
     * Get all the dakJourneys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DakJourneyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DakJourneys");
        return dakJourneyRepository.findAll(pageable).map(dakJourneyMapper::toDto);
    }

    /**
     * Get all the dakJourneys with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DakJourneyDTO> findAllWithEagerRelationships(Pageable pageable) {
        return dakJourneyRepository.findAllWithEagerRelationships(pageable).map(dakJourneyMapper::toDto);
    }

    /**
     * Get one dakJourney by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DakJourneyDTO> findOne(Long id) {
        log.debug("Request to get DakJourney : {}", id);
        return dakJourneyRepository.findOneWithEagerRelationships(id).map(dakJourneyMapper::toDto);
    }

    /**
     * Delete the dakJourney by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DakJourney : {}", id);
        dakJourneyRepository.deleteById(id);
    }
}
