package com.emcloud.mi.web.rest;

import com.emcloud.mi.EmCloudMiApp;

import com.emcloud.mi.config.SecurityBeanOverrideConfiguration;

import com.emcloud.mi.domain.MeterInfo;
import com.emcloud.mi.repository.MeterInfoRepository;
import com.emcloud.mi.service.MeterInfoService;
import com.emcloud.mi.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.emcloud.mi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MeterInfoResource REST controller.
 *
 * @see MeterInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudMiApp.class, SecurityBeanOverrideConfiguration.class})
public class MeterInfoResourceIntTest {

    private static final String DEFAULT_METER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_METER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_METER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_REGISTER_CODE = 1;
    private static final Integer UPDATED_REGISTER_CODE = 2;

    private static final String DEFAULT_ADDRESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COM_POINT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COM_POINT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CPI_REGISTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CPI_REGISTER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_METER_TYPE_CODE = 1;
    private static final Integer UPDATED_METER_TYPE_CODE = 2;

    private static final String DEFAULT_METER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_METER_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Long DEFAULT_LONG_CODE = 1L;
    private static final Long UPDATED_LONG_CODE = 2L;

    private static final Integer DEFAULT_START_OFFSET = 1;
    private static final Integer UPDATED_START_OFFSET = 2;

    private static final Integer DEFAULT_NUMBER_OF_REGISTERS = 1;
    private static final Integer UPDATED_NUMBER_OF_REGISTERS = 2;

    private static final Integer DEFAULT_CONTROL_ADDRESS = 1;
    private static final Integer UPDATED_CONTROL_ADDRESS = 2;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONTROL_COMMANDS = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_COMMANDS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BIG_ENDIAN = false;
    private static final Boolean UPDATED_BIG_ENDIAN = true;

    private static final Boolean DEFAULT_ALLOW_DUPLICATE = false;
    private static final Boolean UPDATED_ALLOW_DUPLICATE = true;

    private static final Integer DEFAULT_CALCULATES = 1;
    private static final Integer UPDATED_CALCULATES = 2;

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    @Autowired
    private MeterInfoRepository meterInfoRepository;

    @Autowired
    private MeterInfoService meterInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeterInfoMockMvc;

    private MeterInfo meterInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeterInfoResource meterInfoResource = new MeterInfoResource(meterInfoService);
        this.restMeterInfoMockMvc = MockMvcBuilders.standaloneSetup(meterInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeterInfo createEntity(EntityManager em) {
        MeterInfo meterInfo = new MeterInfo()
            .meterCode(DEFAULT_METER_CODE)
            .meterName(DEFAULT_METER_NAME)
            .registerCode(DEFAULT_REGISTER_CODE)
            .addressCode(DEFAULT_ADDRESS_CODE)
            .addressName(DEFAULT_ADDRESS_NAME)
            .organizationCode(DEFAULT_ORGANIZATION_CODE)
            .organizationName(DEFAULT_ORGANIZATION_NAME)
            .companyCode(DEFAULT_COMPANY_CODE)
            .companyName(DEFAULT_COMPANY_NAME)
            .comPointCode(DEFAULT_COM_POINT_CODE)
            .cpiRegisterName(DEFAULT_CPI_REGISTER_NAME)
            .meterTypeCode(DEFAULT_METER_TYPE_CODE)
            .meterType(DEFAULT_METER_TYPE)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .longCode(DEFAULT_LONG_CODE)
            .startOffset(DEFAULT_START_OFFSET)
            .numberOfRegisters(DEFAULT_NUMBER_OF_REGISTERS)
            .controlAddress(DEFAULT_CONTROL_ADDRESS)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME)
            .controlCommands(DEFAULT_CONTROL_COMMANDS)
            .bigEndian(DEFAULT_BIG_ENDIAN)
            .allowDuplicate(DEFAULT_ALLOW_DUPLICATE)
            .calculates(DEFAULT_CALCULATES)
            .enable(DEFAULT_ENABLE);
        return meterInfo;
    }

    @Before
    public void initTest() {
        meterInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeterInfo() throws Exception {
        int databaseSizeBeforeCreate = meterInfoRepository.findAll().size();

        // Create the MeterInfo
        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isCreated());

        // Validate the MeterInfo in the database
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeCreate + 1);
        MeterInfo testMeterInfo = meterInfoList.get(meterInfoList.size() - 1);
        assertThat(testMeterInfo.getMeterCode()).isEqualTo(DEFAULT_METER_CODE);
        assertThat(testMeterInfo.getMeterName()).isEqualTo(DEFAULT_METER_NAME);
        assertThat(testMeterInfo.getRegisterCode()).isEqualTo(DEFAULT_REGISTER_CODE);
        assertThat(testMeterInfo.getAddressCode()).isEqualTo(DEFAULT_ADDRESS_CODE);
        assertThat(testMeterInfo.getAddressName()).isEqualTo(DEFAULT_ADDRESS_NAME);
        assertThat(testMeterInfo.getOrganizationCode()).isEqualTo(DEFAULT_ORGANIZATION_CODE);
        assertThat(testMeterInfo.getOrganizationName()).isEqualTo(DEFAULT_ORGANIZATION_NAME);
        assertThat(testMeterInfo.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testMeterInfo.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testMeterInfo.getComPointCode()).isEqualTo(DEFAULT_COM_POINT_CODE);
        assertThat(testMeterInfo.getCpiRegisterName()).isEqualTo(DEFAULT_CPI_REGISTER_NAME);
        assertThat(testMeterInfo.getMeterTypeCode()).isEqualTo(DEFAULT_METER_TYPE_CODE);
        assertThat(testMeterInfo.getMeterType()).isEqualTo(DEFAULT_METER_TYPE);
        assertThat(testMeterInfo.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testMeterInfo.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testMeterInfo.getLongCode()).isEqualTo(DEFAULT_LONG_CODE);
        assertThat(testMeterInfo.getStartOffset()).isEqualTo(DEFAULT_START_OFFSET);
        assertThat(testMeterInfo.getNumberOfRegisters()).isEqualTo(DEFAULT_NUMBER_OF_REGISTERS);
        assertThat(testMeterInfo.getControlAddress()).isEqualTo(DEFAULT_CONTROL_ADDRESS);
        assertThat(testMeterInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMeterInfo.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMeterInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testMeterInfo.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testMeterInfo.getControlCommands()).isEqualTo(DEFAULT_CONTROL_COMMANDS);
        assertThat(testMeterInfo.isBigEndian()).isEqualTo(DEFAULT_BIG_ENDIAN);
        assertThat(testMeterInfo.isAllowDuplicate()).isEqualTo(DEFAULT_ALLOW_DUPLICATE);
        assertThat(testMeterInfo.getCalculates()).isEqualTo(DEFAULT_CALCULATES);
        assertThat(testMeterInfo.isEnable()).isEqualTo(DEFAULT_ENABLE);
    }

    @Test
    @Transactional
    public void createMeterInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meterInfoRepository.findAll().size();

        // Create the MeterInfo with an existing ID
        meterInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MeterInfo in the database
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMeterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setMeterCode(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setMeterName(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegisterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setRegisterCode(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrganizationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setOrganizationName(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setCompanyName(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpiRegisterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setCpiRegisterName(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeterTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setMeterTypeCode(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeterTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setMeterType(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartOffsetIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setStartOffset(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfRegistersIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setNumberOfRegisters(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkControlAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setControlAddress(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setCreatedBy(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setCreateTime(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setUpdatedBy(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setUpdateTime(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setEnable(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeterInfos() throws Exception {
        // Initialize the database
        meterInfoRepository.saveAndFlush(meterInfo);

        // Get all the meterInfoList
        restMeterInfoMockMvc.perform(get("/api/meter-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meterInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterCode").value(hasItem(DEFAULT_METER_CODE.toString())))
            .andExpect(jsonPath("$.[*].meterName").value(hasItem(DEFAULT_METER_NAME.toString())))
            .andExpect(jsonPath("$.[*].registerCode").value(hasItem(DEFAULT_REGISTER_CODE)))
            .andExpect(jsonPath("$.[*].addressCode").value(hasItem(DEFAULT_ADDRESS_CODE.toString())))
            .andExpect(jsonPath("$.[*].addressName").value(hasItem(DEFAULT_ADDRESS_NAME.toString())))
            .andExpect(jsonPath("$.[*].organizationCode").value(hasItem(DEFAULT_ORGANIZATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].organizationName").value(hasItem(DEFAULT_ORGANIZATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].comPointCode").value(hasItem(DEFAULT_COM_POINT_CODE.toString())))
            .andExpect(jsonPath("$.[*].cpiRegisterName").value(hasItem(DEFAULT_CPI_REGISTER_NAME.toString())))
            .andExpect(jsonPath("$.[*].meterTypeCode").value(hasItem(DEFAULT_METER_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].meterType").value(hasItem(DEFAULT_METER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longCode").value(hasItem(DEFAULT_LONG_CODE.intValue())))
            .andExpect(jsonPath("$.[*].startOffset").value(hasItem(DEFAULT_START_OFFSET)))
            .andExpect(jsonPath("$.[*].numberOfRegisters").value(hasItem(DEFAULT_NUMBER_OF_REGISTERS)))
            .andExpect(jsonPath("$.[*].controlAddress").value(hasItem(DEFAULT_CONTROL_ADDRESS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].controlCommands").value(hasItem(DEFAULT_CONTROL_COMMANDS.toString())))
            .andExpect(jsonPath("$.[*].bigEndian").value(hasItem(DEFAULT_BIG_ENDIAN.booleanValue())))
            .andExpect(jsonPath("$.[*].allowDuplicate").value(hasItem(DEFAULT_ALLOW_DUPLICATE.booleanValue())))
            .andExpect(jsonPath("$.[*].calculates").value(hasItem(DEFAULT_CALCULATES)))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getMeterInfo() throws Exception {
        // Initialize the database
        meterInfoRepository.saveAndFlush(meterInfo);

        // Get the meterInfo
        restMeterInfoMockMvc.perform(get("/api/meter-infos/{id}", meterInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meterInfo.getId().intValue()))
            .andExpect(jsonPath("$.meterCode").value(DEFAULT_METER_CODE.toString()))
            .andExpect(jsonPath("$.meterName").value(DEFAULT_METER_NAME.toString()))
            .andExpect(jsonPath("$.registerCode").value(DEFAULT_REGISTER_CODE))
            .andExpect(jsonPath("$.addressCode").value(DEFAULT_ADDRESS_CODE.toString()))
            .andExpect(jsonPath("$.addressName").value(DEFAULT_ADDRESS_NAME.toString()))
            .andExpect(jsonPath("$.organizationCode").value(DEFAULT_ORGANIZATION_CODE.toString()))
            .andExpect(jsonPath("$.organizationName").value(DEFAULT_ORGANIZATION_NAME.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.comPointCode").value(DEFAULT_COM_POINT_CODE.toString()))
            .andExpect(jsonPath("$.cpiRegisterName").value(DEFAULT_CPI_REGISTER_NAME.toString()))
            .andExpect(jsonPath("$.meterTypeCode").value(DEFAULT_METER_TYPE_CODE))
            .andExpect(jsonPath("$.meterType").value(DEFAULT_METER_TYPE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longCode").value(DEFAULT_LONG_CODE.intValue()))
            .andExpect(jsonPath("$.startOffset").value(DEFAULT_START_OFFSET))
            .andExpect(jsonPath("$.numberOfRegisters").value(DEFAULT_NUMBER_OF_REGISTERS))
            .andExpect(jsonPath("$.controlAddress").value(DEFAULT_CONTROL_ADDRESS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.controlCommands").value(DEFAULT_CONTROL_COMMANDS.toString()))
            .andExpect(jsonPath("$.bigEndian").value(DEFAULT_BIG_ENDIAN.booleanValue()))
            .andExpect(jsonPath("$.allowDuplicate").value(DEFAULT_ALLOW_DUPLICATE.booleanValue()))
            .andExpect(jsonPath("$.calculates").value(DEFAULT_CALCULATES))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeterInfo() throws Exception {
        // Get the meterInfo
        restMeterInfoMockMvc.perform(get("/api/meter-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeterInfo() throws Exception {
        // Initialize the database
        meterInfoService.save(meterInfo);

        int databaseSizeBeforeUpdate = meterInfoRepository.findAll().size();

        // Update the meterInfo
        MeterInfo updatedMeterInfo = meterInfoRepository.findOne(meterInfo.getId());
        updatedMeterInfo
            .meterCode(UPDATED_METER_CODE)
            .meterName(UPDATED_METER_NAME)
            .registerCode(UPDATED_REGISTER_CODE)
            .addressCode(UPDATED_ADDRESS_CODE)
            .addressName(UPDATED_ADDRESS_NAME)
            .organizationCode(UPDATED_ORGANIZATION_CODE)
            .organizationName(UPDATED_ORGANIZATION_NAME)
            .companyCode(UPDATED_COMPANY_CODE)
            .companyName(UPDATED_COMPANY_NAME)
            .comPointCode(UPDATED_COM_POINT_CODE)
            .cpiRegisterName(UPDATED_CPI_REGISTER_NAME)
            .meterTypeCode(UPDATED_METER_TYPE_CODE)
            .meterType(UPDATED_METER_TYPE)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .longCode(UPDATED_LONG_CODE)
            .startOffset(UPDATED_START_OFFSET)
            .numberOfRegisters(UPDATED_NUMBER_OF_REGISTERS)
            .controlAddress(UPDATED_CONTROL_ADDRESS)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME)
            .controlCommands(UPDATED_CONTROL_COMMANDS)
            .bigEndian(UPDATED_BIG_ENDIAN)
            .allowDuplicate(UPDATED_ALLOW_DUPLICATE)
            .calculates(UPDATED_CALCULATES)
            .enable(UPDATED_ENABLE);

        restMeterInfoMockMvc.perform(put("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeterInfo)))
            .andExpect(status().isOk());

        // Validate the MeterInfo in the database
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeUpdate);
        MeterInfo testMeterInfo = meterInfoList.get(meterInfoList.size() - 1);
        assertThat(testMeterInfo.getMeterCode()).isEqualTo(UPDATED_METER_CODE);
        assertThat(testMeterInfo.getMeterName()).isEqualTo(UPDATED_METER_NAME);
        assertThat(testMeterInfo.getRegisterCode()).isEqualTo(UPDATED_REGISTER_CODE);
        assertThat(testMeterInfo.getAddressCode()).isEqualTo(UPDATED_ADDRESS_CODE);
        assertThat(testMeterInfo.getAddressName()).isEqualTo(UPDATED_ADDRESS_NAME);
        assertThat(testMeterInfo.getOrganizationCode()).isEqualTo(UPDATED_ORGANIZATION_CODE);
        assertThat(testMeterInfo.getOrganizationName()).isEqualTo(UPDATED_ORGANIZATION_NAME);
        assertThat(testMeterInfo.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testMeterInfo.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testMeterInfo.getComPointCode()).isEqualTo(UPDATED_COM_POINT_CODE);
        assertThat(testMeterInfo.getCpiRegisterName()).isEqualTo(UPDATED_CPI_REGISTER_NAME);
        assertThat(testMeterInfo.getMeterTypeCode()).isEqualTo(UPDATED_METER_TYPE_CODE);
        assertThat(testMeterInfo.getMeterType()).isEqualTo(UPDATED_METER_TYPE);
        assertThat(testMeterInfo.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testMeterInfo.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testMeterInfo.getLongCode()).isEqualTo(UPDATED_LONG_CODE);
        assertThat(testMeterInfo.getStartOffset()).isEqualTo(UPDATED_START_OFFSET);
        assertThat(testMeterInfo.getNumberOfRegisters()).isEqualTo(UPDATED_NUMBER_OF_REGISTERS);
        assertThat(testMeterInfo.getControlAddress()).isEqualTo(UPDATED_CONTROL_ADDRESS);
        assertThat(testMeterInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMeterInfo.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testMeterInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testMeterInfo.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testMeterInfo.getControlCommands()).isEqualTo(UPDATED_CONTROL_COMMANDS);
        assertThat(testMeterInfo.isBigEndian()).isEqualTo(UPDATED_BIG_ENDIAN);
        assertThat(testMeterInfo.isAllowDuplicate()).isEqualTo(UPDATED_ALLOW_DUPLICATE);
        assertThat(testMeterInfo.getCalculates()).isEqualTo(UPDATED_CALCULATES);
        assertThat(testMeterInfo.isEnable()).isEqualTo(UPDATED_ENABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeterInfo() throws Exception {
        int databaseSizeBeforeUpdate = meterInfoRepository.findAll().size();

        // Create the MeterInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeterInfoMockMvc.perform(put("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isCreated());

        // Validate the MeterInfo in the database
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeterInfo() throws Exception {
        // Initialize the database
        meterInfoService.save(meterInfo);

        int databaseSizeBeforeDelete = meterInfoRepository.findAll().size();

        // Get the meterInfo
        restMeterInfoMockMvc.perform(delete("/api/meter-infos/{id}", meterInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeterInfo.class);
        MeterInfo meterInfo1 = new MeterInfo();
        meterInfo1.setId(1L);
        MeterInfo meterInfo2 = new MeterInfo();
        meterInfo2.setId(meterInfo1.getId());
        assertThat(meterInfo1).isEqualTo(meterInfo2);
        meterInfo2.setId(2L);
        assertThat(meterInfo1).isNotEqualTo(meterInfo2);
        meterInfo1.setId(null);
        assertThat(meterInfo1).isNotEqualTo(meterInfo2);
    }
}
