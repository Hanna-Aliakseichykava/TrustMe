package org.trustme.web.rest;

import org.trustme.TrustMeApp;

import org.trustme.domain.Weather;
import org.trustme.repository.WeatherRepository;
import org.trustme.service.WeatherService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static org.trustme.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WeatherResource REST controller.
 *
 * @see WeatherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrustMeApp.class)
public class WeatherResourceIntTest {

    private static final Integer DEFAULT_HUMID = 1;
    private static final Integer UPDATED_HUMID = 2;

    private static final Double DEFAULT_TEMP = 1D;
    private static final Double UPDATED_TEMP = 2D;

    private static final Double DEFAULT_TEMP_MIN = 1D;
    private static final Double UPDATED_TEMP_MIN = 2D;

    private static final Double DEFAULT_TEMP_MAX = 1D;
    private static final Double UPDATED_TEMP_MAX = 2D;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final String DEFAULT_JSON = "AAAAAAAAAA";
    private static final String UPDATED_JSON = "BBBBBBBBBB";

    @Autowired
    private WeatherRepository weatherRepository;
    
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWeatherMockMvc;

    private Weather weather;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeatherResource weatherResource = new WeatherResource(weatherService);
        this.restWeatherMockMvc = MockMvcBuilders.standaloneSetup(weatherResource)
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
    public static Weather createEntity(EntityManager em) {
        Weather weather = new Weather()
            .humid(DEFAULT_HUMID)
            .temp(DEFAULT_TEMP)
            .tempMin(DEFAULT_TEMP_MIN)
            .tempMax(DEFAULT_TEMP_MAX)
            .date(DEFAULT_DATE)
            .weight(DEFAULT_WEIGHT)
            .json(DEFAULT_JSON);
        return weather;
    }

    @Before
    public void initTest() {
        weather = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeather() throws Exception {
        int databaseSizeBeforeCreate = weatherRepository.findAll().size();

        // Create the Weather
        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isCreated());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeCreate + 1);
        Weather testWeather = weatherList.get(weatherList.size() - 1);
        assertThat(testWeather.getHumid()).isEqualTo(DEFAULT_HUMID);
        assertThat(testWeather.getTemp()).isEqualTo(DEFAULT_TEMP);
        assertThat(testWeather.getTempMin()).isEqualTo(DEFAULT_TEMP_MIN);
        assertThat(testWeather.getTempMax()).isEqualTo(DEFAULT_TEMP_MAX);
        assertThat(testWeather.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testWeather.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testWeather.getJson()).isEqualTo(DEFAULT_JSON);
    }

    @Test
    @Transactional
    public void createWeatherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weatherRepository.findAll().size();

        // Create the Weather with an existing ID
        weather.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeatherMockMvc.perform(post("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWeathers() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        // Get all the weatherList
        restWeatherMockMvc.perform(get("/api/weathers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weather.getId().intValue())))
            .andExpect(jsonPath("$.[*].humid").value(hasItem(DEFAULT_HUMID)))
            .andExpect(jsonPath("$.[*].temp").value(hasItem(DEFAULT_TEMP.doubleValue())))
            .andExpect(jsonPath("$.[*].tempMin").value(hasItem(DEFAULT_TEMP_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].tempMax").value(hasItem(DEFAULT_TEMP_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].json").value(hasItem(DEFAULT_JSON.toString())));
    }
    
    @Test
    @Transactional
    public void getWeather() throws Exception {
        // Initialize the database
        weatherRepository.saveAndFlush(weather);

        // Get the weather
        restWeatherMockMvc.perform(get("/api/weathers/{id}", weather.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(weather.getId().intValue()))
            .andExpect(jsonPath("$.humid").value(DEFAULT_HUMID))
            .andExpect(jsonPath("$.temp").value(DEFAULT_TEMP.doubleValue()))
            .andExpect(jsonPath("$.tempMin").value(DEFAULT_TEMP_MIN.doubleValue()))
            .andExpect(jsonPath("$.tempMax").value(DEFAULT_TEMP_MAX.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.json").value(DEFAULT_JSON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWeather() throws Exception {
        // Get the weather
        restWeatherMockMvc.perform(get("/api/weathers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeather() throws Exception {
        // Initialize the database
        weatherService.save(weather);

        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();

        // Update the weather
        Weather updatedWeather = weatherRepository.findById(weather.getId()).get();
        // Disconnect from session so that the updates on updatedWeather are not directly saved in db
        em.detach(updatedWeather);
        updatedWeather
            .humid(UPDATED_HUMID)
            .temp(UPDATED_TEMP)
            .tempMin(UPDATED_TEMP_MIN)
            .tempMax(UPDATED_TEMP_MAX)
            .date(UPDATED_DATE)
            .weight(UPDATED_WEIGHT)
            .json(UPDATED_JSON);

        restWeatherMockMvc.perform(put("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeather)))
            .andExpect(status().isOk());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
        Weather testWeather = weatherList.get(weatherList.size() - 1);
        assertThat(testWeather.getHumid()).isEqualTo(UPDATED_HUMID);
        assertThat(testWeather.getTemp()).isEqualTo(UPDATED_TEMP);
        assertThat(testWeather.getTempMin()).isEqualTo(UPDATED_TEMP_MIN);
        assertThat(testWeather.getTempMax()).isEqualTo(UPDATED_TEMP_MAX);
        assertThat(testWeather.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWeather.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testWeather.getJson()).isEqualTo(UPDATED_JSON);
    }

    @Test
    @Transactional
    public void updateNonExistingWeather() throws Exception {
        int databaseSizeBeforeUpdate = weatherRepository.findAll().size();

        // Create the Weather

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeatherMockMvc.perform(put("/api/weathers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(weather)))
            .andExpect(status().isBadRequest());

        // Validate the Weather in the database
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWeather() throws Exception {
        // Initialize the database
        weatherService.save(weather);

        int databaseSizeBeforeDelete = weatherRepository.findAll().size();

        // Get the weather
        restWeatherMockMvc.perform(delete("/api/weathers/{id}", weather.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Weather> weatherList = weatherRepository.findAll();
        assertThat(weatherList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Weather.class);
        Weather weather1 = new Weather();
        weather1.setId(1L);
        Weather weather2 = new Weather();
        weather2.setId(weather1.getId());
        assertThat(weather1).isEqualTo(weather2);
        weather2.setId(2L);
        assertThat(weather1).isNotEqualTo(weather2);
        weather1.setId(null);
        assertThat(weather1).isNotEqualTo(weather2);
    }
}
