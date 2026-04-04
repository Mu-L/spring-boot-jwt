package murraco.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void signin_withValidCredentials_returnsToken() throws Exception {
    mockMvc.perform(post("/users/signin")
            .param("username", "admin")
            .param("password", "admin"))
        .andExpect(status().isOk())
        .andExpect(result -> {
          String body = result.getResponse().getContentAsString();
          assert body != null && !body.isBlank() : "Expected non-empty JWT token";
        });
  }

  @Test
  void me_withoutToken_returns403() throws Exception {
    mockMvc.perform(get("/users/me"))
        .andExpect(status().isForbidden());
  }

  @Test
  void me_withValidToken_returnsUserData() throws Exception {
    String token = mockMvc.perform(post("/users/signin")
            .param("username", "admin")
            .param("password", "admin"))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    mockMvc.perform(get("/users/me")
            .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("admin"))
        .andExpect(jsonPath("$.email").value("admin@email.com"))
        .andExpect(jsonPath("$.appUserRoles").isArray());
  }
}
