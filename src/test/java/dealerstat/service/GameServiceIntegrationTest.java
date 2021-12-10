package dealerstat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dealerstat.config.WebConfig;
import dealerstat.dto.GameDto;
import dealerstat.entity.Game;
import dealerstat.repository.GameRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@WithMockUser(username = "admin", authorities = "ADMIN")
public class GameServiceIntegrationTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private Game game;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        game = (Game) gameService.createGame(new GameDto("Some name")).getBody();
    }

    @After
    public void destroy() {
        gameRepository.deleteById(game.getId());
    }

    @Test
    public void createGameWhenThisGameAlreadyExist() throws Exception {
        mockMvc.perform(post("/games")
                .content(objectMapper.writeValueAsString(new GameDto("Some name")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void showAllGameTestRequiredStatus200() throws Exception {
        mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateGameIfNotExistTestRequiredStatus200() throws Exception {
        mockMvc.perform(put("/games/{gameId}", game.getId())
                .content(objectMapper.writeValueAsString(new GameDto("Some name 1")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateGameIfExistTestRequiredStatus404() throws Exception {
        mockMvc.perform(put("/games/{gameId}", -1)
                .content(objectMapper.writeValueAsString(new GameDto("Some name")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}