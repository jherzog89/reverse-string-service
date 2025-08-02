package herzog.webservices.restful;

import herzog.webservices.restful.model.ManipulatedString;
import herzog.webservices.restful.repository.ManipulatedStringRepository;
import herzog.webservices.restful.service.StringManipulationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StringManipulationServiceTest {

    private ManipulatedStringRepository repo;
    private StringManipulationService service;

    @BeforeEach
    void setUp() {
        repo = mock(ManipulatedStringRepository.class);
        service = new StringManipulationService();
        service.setRepo(repo); // inject mock
    }

    @Test
    void reverseString_setsFieldsAndSaves() {
        ManipulatedString input = new ManipulatedString();
        input.setOriginalString("abc123");

        ManipulatedString saved = new ManipulatedString();
        when(repo.save(any(ManipulatedString.class))).thenReturn(saved);

        ManipulatedString result = service.reverseString(input);

        ArgumentCaptor<ManipulatedString> captor = ArgumentCaptor.forClass(ManipulatedString.class);
        verify(repo).save(captor.capture());
        ManipulatedString passed = captor.getValue();

        assertThat(passed.getMicroserviceId()).isNotNull();
        assertThat(passed.getManipulatedString()).isEqualTo("321cba");
        assertThat(passed.getTimeCompleted()).isInstanceOf(Date.class);
        assertThat(result).isSameAs(saved);
    }

    @Test
    void reverseStringUtil_reversesString() {
        assertThat(service.reverseStringUtil("abc")).isEqualTo("cba");
        assertThat(service.reverseStringUtil("123 456")).isEqualTo("654 321");
        assertThat(service.reverseStringUtil("")).isEqualTo("");
    }

    @Test
    void getLastTenReversedStrings_returnsListFromRepo() {
        List<ManipulatedString> expected = Arrays.asList(new ManipulatedString(), new ManipulatedString());
        when(repo.findLast10ByTimeCompleted(any(Pageable.class))).thenReturn(expected);

        List<ManipulatedString> result = service.getLastTenReversedStrings();

        assertThat(result).isEqualTo(expected);
        verify(repo).findLast10ByTimeCompleted(any(Pageable.class));
    }

    @Test
    void deleteAllManipulatedStrings_callsRepoDeleteAll() {
        service.deleteAllManipulatedStrings();
        verify(repo).deleteAll();
    }
}