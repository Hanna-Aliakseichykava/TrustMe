package org.trustme.web.rest;

import org.trustme.TrustMeApp;

import org.trustme.domain.Visited;
import org.trustme.repository.VisitedRepository;
import org.trustme.service.VisitedService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static org.trustme.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VisitedResource REST controller.
 *
 * @see VisitedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrustMeApp.class)
public class VisitedResourceIntTest {

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private VisitedRepository visitedRepository;
    
    @Autowired
    private VisitedService visitedService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVisitedMockMvc;

    private Visited visited;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VisitedResource visitedResource = new VisitedResource(visitedService);
        this.restVisitedMockMvc = MockMvcBuilders.standaloneSetup(visitedResource)
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
    public static Visited createEntity(EntityManager em) {
        Visited visited = new Visited()
            .score(DEFAULT_SCORE)
            .date(DEFAULT_DATE);
        return visited;
    }

    @Before
    public void initTest() {
        visited = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisited() throws Exception {
        int databaseSizeBeforeCreate = visitedRepository.findAll().size();

        // Create the Visited
        restVisitedMockMvc.perform(post("/api/visiteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visited)))
            .andExpect(status().isCreated());

        // Validate the Visited in the database
        List<Visited> visitedList = visitedRepository.findAll();
        assertThat(visitedList).hasSize(databaseSizeBeforeCreate + 1);
        Visited testVisited = visitedList.get(visitedList.size() - 1);
        assertThat(testVisited.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testVisited.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createVisitedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitedRepository.findAll().size();

        // Create the Visited with an existing ID
        visited.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitedMockMvc.perform(post("/api/visiteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visited)))
            .andExpect(status().isBadRequest());

        // Validate the Visited in the database
        List<Visited> visitedList = visitedRepository.findAll();
        assertThat(visitedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVisiteds() throws Exception {
        // Initialize the database
        visitedRepository.saveAndFlush(visited);

        // Get all the visitedList
        restVisitedMockMvc.perform(get("/api/visiteds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visited.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getVisited() throws Exception {
        // Initialize the database
        visitedRepository.saveAndFlush(visited);

        // Get the visited
        restVisitedMockMvc.perform(get("/api/visiteds/{id}", visited.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(visited.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVisited() throws Exception {
        // Get the visited
        restVisitedMockMvc.perform(get("/api/visiteds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisited() throws Exception {
        // Initialize the database
        visitedService.save(visited);

        int databaseSizeBeforeUpdate = visitedRepository.findAll().size();

        // Update the visited
        Visited updatedVisited = visitedRepository.findById(visited.getId()).get();
        // Disconnect from session so that the updates on updatedVisited are not directly saved in db
        em.detach(updatedVisited);
        updatedVisited
            .score(UPDATED_SCORE)
            .date(UPDATED_DATE);

        restVisitedMockMvc.perform(put("/api/visiteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisited)))
            .andExpect(status().isOk());

        // Validate the Visited in the database
        List<Visited> visitedList = visitedRepository.findAll();
        assertThat(visitedList).hasSize(databaseSizeBeforeUpdate);
        Visited testVisited = visitedList.get(visitedList.size() - 1);
        assertThat(testVisited.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testVisited.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVisited() throws Exception {
        int databaseSizeBeforeUpdate = visitedRepository.findAll().size();

        // Create the Visited

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitedMockMvc.perform(put("/api/visiteds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(visited)))
            .andExpect(status().isBadRequest());

        // Validate the Visited in the database
        List<Visited> visitedList = visitedRepository.findAll();
        assertThat(visitedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVisited() throws Exception {
        // Initialize the database
        visitedService.save(visited);

        int databaseSizeBeforeDelete = visitedRepository.findAll().size();

        // Get the visited
        restVisitedMockMvc.perform(delete("/api/visiteds/{id}", visited.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Visited> visitedList = visitedRepository.findAll();
        assertThat(visitedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Visited.class);
        Visited visited1 = new Visited();
        visited1.setId(1L);
        Visited visited2 = new Visited();
        visited2.setId(visited1.getId());
        assertThat(visited1).isEqualTo(visited2);
        visited2.setId(2L);
        assertThat(visited1).isNotEqualTo(visited2);
        visited1.setId(null);
        assertThat(visited1).isNotEqualTo(visited2);
    }
}
