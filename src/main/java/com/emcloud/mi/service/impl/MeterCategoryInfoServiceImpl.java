package com.emcloud.mi.service.impl;

import com.emcloud.mi.security.SecurityUtils;
import com.emcloud.mi.service.MeterCategoryInfoService;
import com.emcloud.mi.domain.MeterCategoryInfo;
import com.emcloud.mi.repository.MeterCategoryInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


/**
 * Service Implementation for managing MeterCategoryInfo.
 */
@Service
@Transactional
public class MeterCategoryInfoServiceImpl implements MeterCategoryInfoService{

    private final Logger log = LoggerFactory.getLogger(MeterCategoryInfoServiceImpl.class);

    private final MeterCategoryInfoRepository meterCategoryInfoRepository;

    public MeterCategoryInfoServiceImpl(MeterCategoryInfoRepository meterCategoryInfoRepository) {
        this.meterCategoryInfoRepository = meterCategoryInfoRepository;
    }

    /**
     * Save a meterCategoryInfo.
     *
     * @param meterCategoryInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public MeterCategoryInfo save(MeterCategoryInfo meterCategoryInfo) {
        log.debug("Request to save MeterCategoryInfo : {}", meterCategoryInfo);
        meterCategoryInfo.setCreatedBy(SecurityUtils.getCurrentUserLogin());
        meterCategoryInfo.setCreateTime(Instant.now());
        meterCategoryInfo.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        meterCategoryInfo.setUpdateTime(Instant.now());
        return meterCategoryInfoRepository.save(meterCategoryInfo);
    }

    /**
     * update a meterCategoryInfo.
     *
     * @param meterCategoryInfo the entity to update
     * @return the persisted entity
     */
    @Override
    public MeterCategoryInfo update(MeterCategoryInfo meterCategoryInfo) {
        log.debug("Request to save MeterCategoryInfo : {}", meterCategoryInfo);
        meterCategoryInfo.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        meterCategoryInfo.setUpdateTime(Instant.now());
        return meterCategoryInfoRepository.save(meterCategoryInfo);
    }


    /**
     *  Get all the meterCategoryInfos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeterCategoryInfo> findAll(Pageable pageable) {
        log.debug("Request to get all MeterCategoryInfos");
        return meterCategoryInfoRepository.findAll(pageable);
    }

    /**
     *  Get all the MeterCategoryInfo by meterType .
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeterCategoryInfo> findAllByMeterType(Pageable pageable,String meterType) {
        log.debug("Request to get all MeterCategoryInfo by meterType");
        return meterCategoryInfoRepository.findAllByMeterTypeContaining(pageable,meterType);
    }


    /**
     *  Get all the MeterCategoryInfo by meterType .
     *
     *  @param meterType the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MeterCategoryInfo> findByMeterType(String meterType){
        log.debug("Request to get all MeterCategoryInfo by meterType");
        return meterCategoryInfoRepository.findByMeterType(meterType);
    }

    /**
     *  Get one meterCategoryInfo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeterCategoryInfo findOne(Long id) {
        log.debug("Request to get MeterCategoryInfo : {}", id);
        return meterCategoryInfoRepository.findOne(id);
    }

    /**
     *  Delete the  meterCategoryInfo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeterCategoryInfo : {}", id);
        meterCategoryInfoRepository.delete(id);
    }
}
