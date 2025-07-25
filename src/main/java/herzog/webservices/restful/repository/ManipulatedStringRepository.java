package herzog.webservices.restful.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import herzog.webservices.restful.model.ManipulatedString;

@Repository
public interface ManipulatedStringRepository extends JpaRepository<ManipulatedString, Long> {

    @Query("SELECT m from ManipulatedString m ORDER BY m.timeCompleted DESC")
    List<ManipulatedString> findLast10ByTimeCompleted(Pageable pageable);
}
