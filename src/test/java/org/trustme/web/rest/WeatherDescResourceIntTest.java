package org.trustme.web.rest;

import org.trustme.TrustMeApp;

import org.trustme.domain.WeatherDesc;
import org.trustme.repository.WeatherDescRepository;
import org.trustme.service.WeatherDescService;
import org.trustme.web.rest.errors.ExceptionTranslator;

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
import java.util.List;


import static org.trustme.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WeatherDescResource REST controller.
 *
 * @see WeatherDescResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrustMeApp.class)
public class WeatherDescResourceIntTest {

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final String DEFAULT_SHORT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESC = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESC = "BBBBBBBBBB";

    @Autowired
    private WeatherDescRepository weatherDescRepository;
    
    @Autowired
    private WeatherDescService weatherDescService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWeatherDescMockMvc;

    private WeatherDesc weatherDesc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeatherDescResource weatherDescResource = new WeatherDescResource(weatherDescService);
        this.restWeatherDescMockMvc = MockMvcBuilders.standaloneSetup(weatherDescResource)
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
    public static WeatherDesc createEntity(EntityManager em) {
        WeatherDesc weatherDesc = new WeatherDesc()
            .weight(DEFAULT_WEIGHT)
            .shortDesc(DEFAULT_SHORT_DESC)
            .longDesc(DEFAULT_LONG_DESC);
        return weatherDesc;
    }

    @Before
    public void initTest() {
        weatherDesc = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeatherDesc() throws Exception {
        int databaseSizeBeforeCreate = weatherDescRepository.findAll().size();

        // Create the WeatherDesc
        restWeatherDescMockMvc.perform(post("/api/weather-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weatherDesc)))
            .andExpect(status().isCreated());

        // Validate the WeatherDesc in the database
        List<WeatherDesc> weatherDescList = weatherDescRepository.findAll();
        assertThat(weatherDescList).hasSize(databaseSizeBeforeCreate + 1);
        WeatherDesc testWeatherDesc = weatherDescList.get(weatherDescList.size() - 1);
        assertThat(testWeatherDesc.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testWeatherDesc.getShortDesc()).isEqualTo(DEFAULT_SHORT_DESC);
        assertThat(testWeatherDesc.getLongDesc()).isEqualTo(DEFAULT_LONG_DESC);
    }

    @Test
    @Transactional
    public void createWeatherDescWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weatherDescRepository.findAll().size();

        // Create the WeatherDesc with an existing ID
        weatherDesc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeatherDescMockMvc.perform(post("/api/weather-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weatherDesc)))
            .andExpect(status().isBadRequest());

        // Validate the WeatherDesc in the database
        List<WeatherDesc> weatherDescList = weatherDescRepository.findAll();
        assertThat(weatherDescList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWeatherDescs() throws Exception {
        // Initialize the database
        weatherDescRepository.saveAndFlush(weatherDesc);

        // Get all the weatherDescList
        restWeatherDescMockMvc.perform(get("/api/weather-descs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weatherDesc.getId().intValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].shortDesc").value(hasItem(DEFAULT_SHORT_DESC.toString())))
            .andExpect(jsonPath("$.[*].longDesc").value(hasItem(DEFAULT_LONG_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getWeatherDesc() throws Exception {
        // Initialize the database
        weatherDescRepository.saveAndFlush(weatherDesc);

        // Get the weatherDesc
        restWeatherDescMockMvc.perform(get("/api/weather-descs/{id}", weatherDesc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weatherDesc.getId().intValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.shortDesc").value(DEFAULT_SHORT_DESC.toString()))
            .andExpect(jsonPath("$.longDesc").value(DEFAULT_LONG_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWeatherDesc() throws Exception {
        // Get the weatherDesc
        restWeatherDescMockMvc.perform(get("/api/weather-descs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeatherDesc() throws Exception {
        // Initialize the database
        weatherDescService.save(weatherDesc);

        int databaseSizeBeforeUpdate = weatherDescRepository.findAll().size();

        // Update the weatherDesc
        WeatherDesc updatedWeatherDesc = weatherDescRepository.findById(weatherDesc.getId()).get();
        // Disconnect from session so that the updates on updatedWeatherDesc are not directly saved in db
        em.detach(updatedWeatherDesc);
        updatedWeatherDesc
            .weight(UPDATED_WEIGHT)
            .shortDesc(UPDATED_SHORT_DESC)
            .longDesc(UPDATED_LONG_DESC);

        restWeatherDescMockMvc.perform(put("/api/weather-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeatherDesc)))
            .andExpect(status().isOk());

        // Validate the WeatherDesc in the database
        List<WeatherDesc> weatherDescList = weatherDescRepository.findAll();
        assertThat(weatherDescList).hasSize(databaseSizeBeforeUpdate);
        WeatherDesc testWeatherDesc = weatherDescList.get(weatherDescList.size() - 1);
        assertThat(testWeatherDesc.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testWeatherDesc.getShortDesc()).isEqualTo(UPDATED_SHORT_DESC);
        assertThat(testWeatherDesc.getLongDesc()).isEqualTo(UPDATED_LONG_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingWeatherDesc() throws Exception {
        int databaseSizeBeforeUpdate = weatherDescRepository.findAll().size();

        // Create the WeatherDesc

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeatherDescMockMvc.perform(put("/api/weather-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weatherDesc)))
            .andExpect(status().isBadRequest());

        // Validate the WeatherDesc in the database
        List<WeatherDesc> weatherDescList = weatherDescRepository.findAll();
        assertThat(weatherDescList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWeatherDesc() throws Exception {
        // Initialize the database
        weatherDescService.save(weatherDesc);

        int databaseSizeBeforeDelete = weatherDescRepository.findAll().size();

        // Get the weatherDesc
        restWeatherDescMockMvc.perform(delete("/api/weather-descs/{id}", weatherDesc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WeatherDesc> weatherDescList = weatherDescRepository.findAll();
        assertThat(weatherDescList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeatherDesc.class);
        WeatherDesc weatherDesc1 = new WeatherDesc();
        weatherDesc1.setId(1L);
        WeatherDesc weatherDesc2 = new WeatherDesc();
        weatherDesc2.setId(weatherDesc1.getId());
        assertThat(weatherDesc1).isEqualTo(weatherDesc2);
        weatherDesc2.setId(2L);
        assertThat(weatherDesc1).isNotEqualTo(weatherDesc2);
        weatherDesc1.setId(null);
        assertThat(weatherDesc1).isNotEqualTo(weatherDesc2);
    }
}
