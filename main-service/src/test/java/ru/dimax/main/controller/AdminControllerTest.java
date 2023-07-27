package ru.dimax.main.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

/*
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CategoryService categoryService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    @Test
    void itShouldCreateUser() {
        // Given
        NewUserRequest userRequest = NewUserRequest.builder()
                .name(faker.name().fullName())
                .email(String.format("%s@practicum.ru", faker.funnyName()))
                .build();

        UserDto  userDto = UserDto.builder()
                .id(1L)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();

        when(userService.createUser(userRequest)).thenReturn(userDto);
        // When
        // Then
        mockMvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(userRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class));

    }

    @SneakyThrows
    @Test
    void itShouldNotCreateUserWithInvalidEmail() {
        // Given
        NewUserRequest userRequest = NewUserRequest.builder()
                .name(faker.name().fullName())

                .build();

        UserDto  userDto = UserDto.builder()
                .id(1L)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();


        // When
        // Then
        mockMvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(userRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @SneakyThrows
    @Test
    void itShouldNotCreateUserWithEmptyName() {
        // Given
        NewUserRequest userRequest = NewUserRequest.builder()
                .name(null)
                .email(String.format("%s@practicum.ru", faker.funnyName()))
                .build();

        UserDto  userDto = UserDto.builder()
                .id(1L)
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();

        when(userService.createUser(userRequest)).thenReturn(userDto);
        // When
        // Then
        mockMvc.perform(post("/admin/users")
                        .content(mapper.writeValueAsString(userRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

    @SneakyThrows
    @Test
    void itShouldCreateCategory() {
        // Given
        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("Concerts")
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("New Category")
                .build();

        when(categoryService.createCategory(newCategoryDto)).thenReturn(categoryDto);
        // When

        // Then
        mockMvc.perform(post("/admin/categories")
                        .content(mapper.writeValueAsString(newCategoryDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(categoryDto.getId()), Long.class));
    }

    @SneakyThrows
    @Test
    void itShouldUpdateCategory() {
        // Given
        NewCategoryDto newCategoryDto = NewCategoryDto.builder()
                .name("Concerts")
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("Concerts")
                .build();

        when(categoryService.updateCategory(1L, newCategoryDto)).thenReturn(categoryDto);
        // When
        // Then
        mockMvc.perform(patch("/admin/categories/1")
                        .content(mapper.writeValueAsString(newCategoryDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
*/
}