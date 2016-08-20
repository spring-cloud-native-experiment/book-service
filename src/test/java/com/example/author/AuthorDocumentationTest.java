package com.example.author;

import com.example.book.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
@AutoConfigureRestDocs("build/generated-snippets")
public class AuthorDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private BookService bookService;

    @Test
    public void getAuthor() throws Exception {
        Author author = Author.builder().id(1L).name("Some Author").build();
        given(this.authorService.findAuthorByAuthorId(1L)).willReturn(Optional.of(author));

        this.mockMvc
                .perform(
                        get("/authors/{author-id}", 1L)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(document(
                        "author-by-id",
                        pathParameters(
                                parameterWithName("author-id").description("Unique identifier for author")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Unique identifier for author"),
                                fieldWithPath("name").description("Name of the author")
                        )
                ));

    }
}
