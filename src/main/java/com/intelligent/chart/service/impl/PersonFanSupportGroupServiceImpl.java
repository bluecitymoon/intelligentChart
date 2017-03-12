package com.intelligent.chart.service.impl;

import com.intelligent.chart.service.PersonFanSupportGroupService;
import com.intelligent.chart.domain.PersonFanSupportGroup;
import com.intelligent.chart.repository.PersonFanSupportGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PersonFanSupportGroup.
 */
@Service
@Transactional
public class PersonFanSupportGroupServiceImpl implements PersonFanSupportGroupService{

    private final Logger log = LoggerFactory.getLogger(PersonFanSupportGroupServiceImpl.class);
    
    @Inject
    private PersonFanSupportGroupRepository personFanSupportGroupRepository;

    /**
     * Save a personFanSupportGroup.
     *
     * @param personFanSupportGroup the entity to save
     * @return the persisted entity
     */
    public PersonFanSupportGroup save(PersonFanSupportGroup personFanSupportGroup) {
        log.debug("Request to save PersonFanSupportGroup : {}", personFanSupportGroup);
        PersonFanSupportGroup result = personFanSupportGroupRepository.save(personFanSupportGroup);
        return result;
    }

    /**
     *  Get all the personFanSupportGroups.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PersonFanSupportGroup> findAll(Pageable pageable) {
        log.debug("Request to get all PersonFanSupportGroups");
        Page<PersonFanSupportGroup> result = personFanSupportGroupRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one personFanSupportGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonFanSupportGroup findOne(Long id) {
        log.debug("Request to get PersonFanSupportGroup : {}", id);
        PersonFanSupportGroup personFanSupportGroup = personFanSupportGroupRepository.findOne(id);
        return personFanSupportGroup;
    }

    /**
     *  Delete the  personFanSupportGroup by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonFanSupportGroup : {}", id);
        personFanSupportGroupRepository.delete(id);
    }
}
