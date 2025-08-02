package herzog.webservices.restful.service;


import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import herzog.webservices.restful.model.ManipulatedString;
import herzog.webservices.restful.repository.ManipulatedStringRepository;
import jakarta.transaction.Transactional;

@Service
public class StringManipulationService {

    @Autowired
    ManipulatedStringRepository repo;


    public ManipulatedStringRepository getRepo() {
        return repo;
    }

    public void setRepo(ManipulatedStringRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public ManipulatedString reverseString(ManipulatedString maniStr){
        //populate microserviceId
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String processId = runtimeMXBean.getName(); // Extracts PID from format "pid@hostname"
        maniStr.setMicroserviceId(processId);

        //populate manipulatedString
        maniStr.setManipulatedString(this.reverseStringUtil(maniStr.getOriginalString()));

        //populate timeCompleted
        maniStr.setTimeCompleted(new Date());
        return repo.save(maniStr);
    }

    public String reverseStringUtil(String str){
        return new StringBuilder(str).reverse().toString();
    }

    public List<ManipulatedString> getLastTenReversedStrings() {
        Pageable pageable = PageRequest.of(0, 10); // Page 0, size 10
        return repo.findLast10ByTimeCompleted(pageable);
    }

    public void deleteAllManipulatedStrings() {
        repo.deleteAll();
    }

}
