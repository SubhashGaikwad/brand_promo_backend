package com.techvg.eoffice.service;

import com.techvg.eoffice.domain.DakHistory;
import com.techvg.eoffice.repository.DakHistoryRepository;
import com.techvg.eoffice.service.dto.DakHistoryDTO;
import com.techvg.eoffice.service.mapper.DakHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DakHistory}.
 */
@Service
@Transactional
public class DakHistoryService {

    private final Logger log = LoggerFactory.getLogger(DakHistoryService.class);

    private final DakHistoryRepository dakHistoryRepository;

    private final DakHistoryMapper dakHistoryMapper;

    public DakHistoryService(DakHistoryRepository dakHistoryRepository, DakHistoryMapper dakHistoryMapper) {
        this.dakHistoryRepository = dakHistoryRepository;
        this.dakHistoryMapper = dakHistoryMapper;
    }

    /**
     * Save a dakHistory.
     *
     * @param dakHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public DakHistoryDTO save(DakHistoryDTO dakHistoryDTO) {
        log.debug("Request to save DakHistory : {}", dakHistoryDTO);
        DakHistory dakHistory = dakHistoryMapper.toEntity(dakHistoryDTO);
        dakHistory = dakHistoryRepository.save(dakHistory);
        return dakHistoryMapper.toDto(dakHistory);
    }

    /**
     * Update a dakHistory.
     *
     * @param dakHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public DakHistoryDTO update(DakHistoryDTO dakHistoryDTO) {
        log.debug("Request to save DakHistory : {}", dakHistoryDTO);
        DakHistory dakHistory = dakHistoryMapper.toEntity(dakHistoryDTO);
        dakHistory = dakHistoryRepository.save(dakHistory);
        return dakHistoryMapper.toDto(dakHistory);
    }

    /**
     * Partially update a dakHistory.
     *
     * @param dakHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DakHistoryDTO> partialUpdate(DakHistoryDTO dakHistoryDTO) {
        log.debug("Request to partially update DakHistory : {}", dakHistoryDTO);

        return dakHistoryRepository
            .findById(dakHistoryDTO.getId())
            .map(existingDakHistory -> {
                dakHistoryMapper.partialUpdate(existingDakHistory, dakHistoryDTO);

                return existingDakHistory;
            })
            .map(dakHistoryRepository::save)
            .map(dakHistoryMapper::toDto);
    }

    /**
     * Get all the dakHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DakHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DakHistories");
        return dakHistoryRepository.findAll(pageable).map(dakHistoryMapper::toDto);
    }

    /**
     * Get all the dakHistories with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DakHistoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return dakHistoryRepository.findAllWithEagerRelationships(pageable).map(dakHistoryMapper::toDto);
    }

    /**
     * Get one dakHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DakHistoryDTO> findOne(Long id) {
        log.debug("Request to get DakHistory : {}", id);
        return dakHistoryRepository.findOneWithEagerRelationships(id).map(dakHistoryMapper::toDto);
    }

    /**
     * Delete the dakHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DakHistory : {}", id);
        dakHistoryRepository.deleteById(id);
    }
}
