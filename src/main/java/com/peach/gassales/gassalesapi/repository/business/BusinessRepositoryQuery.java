package com.peach.gassales.gassalesapi.repository.business;

import com.peach.gassales.gassalesapi.model.Business;
import com.peach.gassales.gassalesapi.repository.filter.BusinessFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BusinessRepositoryQuery {
    Page<Business> filtrar(BusinessFilter businessFilter, Pageable pageable);
}
