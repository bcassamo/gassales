package com.peach.gassales.gassalesapi.resource;

import com.peach.gassales.gassalesapi.model.Business;
import com.peach.gassales.gassalesapi.repository.BusinessRepository;
import com.peach.gassales.gassalesapi.repository.filter.BusinessFilter;
import com.peach.gassales.gassalesapi.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class BusinessResource {
    private BusinessRepository businessRepository;
    private BusinessService businessService;

    @Autowired
    private void setBusinessRepository(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Autowired
    private void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping
    public Page<Business> pesquisar(BusinessFilter businessFilter, Pageable pageable) {
        return businessRepository.filtrar(businessFilter, pageable);
    }
}
