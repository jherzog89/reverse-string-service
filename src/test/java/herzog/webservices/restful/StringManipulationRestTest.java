package herzog.webservices.restful;


import herzog.webservices.restful.controller.StringManipulationRest;
import herzog.webservices.restful.model.ManipulatedString;
import herzog.webservices.restful.service.StringManipulationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StringManipulationRestTest {

    private StringManipulationService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        service = mock(StringManipulationService.class);
        StringManipulationRest controller = new StringManipulationRest();
        controller.setSrv(service); // inject mock
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void reverseString_post_returnsLastTen() throws Exception {
        ManipulatedString input = new ManipulatedString();
        input.setOriginalString("abc def");
        ManipulatedString reversed = new ManipulatedString();
        reversed.setOriginalString("abc def");
        reversed.setManipulatedString("def abc");
        List<ManipulatedString> lastTen = Collections.singletonList(reversed);

        when(service.reverseString(any(ManipulatedString.class))).thenReturn(reversed);
        when(service.getLastTenReversedStrings()).thenReturn(lastTen);

        mockMvc.perform(post("/reverseString")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"originalString\":\"abc def\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].manipulatedString", is("def abc")));

        ArgumentCaptor<ManipulatedString> captor = ArgumentCaptor.forClass(ManipulatedString.class);
        verify(service).reverseString(captor.capture());
        ManipulatedString passed = captor.getValue();
        assert passed.getApiUsed() != null;
    }

    @Test
    void getLastTenReversedStrings_get_returnsLastTen() throws Exception {
        ManipulatedString ms = new ManipulatedString();
        ms.setManipulatedString("foo bar");
        List<ManipulatedString> lastTen = Collections.singletonList(ms);

        when(service.getLastTenReversedStrings()).thenReturn(lastTen);

        mockMvc.perform(get("/reverseString"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].manipulatedString", is("foo bar")));
    }

    @Test
    void deleteAllManipulatedStrings_delete_callsServiceAndReturnsLastTen() throws Exception {
        ManipulatedString ms = new ManipulatedString();
        ms.setManipulatedString("baz qux");
        List<ManipulatedString> lastTen = Collections.singletonList(ms);

        doNothing().when(service).deleteAllManipulatedStrings();
        when(service.getLastTenReversedStrings()).thenReturn(lastTen);

        mockMvc.perform(delete("/reverseString"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].manipulatedString", is("baz qux")));

        verify(service, times(1)).deleteAllManipulatedStrings();
        verify(service, times(1)).getLastTenReversedStrings();
    }
}