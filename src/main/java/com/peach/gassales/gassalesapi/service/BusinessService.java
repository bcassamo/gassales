package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.model.Business;
import com.peach.gassales.gassalesapi.model.Lancamento;
import com.peach.gassales.gassalesapi.repository.BusinessRepository;
import com.peach.gassales.gassalesapi.resource.BusinessResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Date;

@Service
public class BusinessService {
    private BusinessRepository businessRepository;
    private ApplicationEventPublisher publisher;
    private LancamentoService lancamentoService;

    @Autowired
    private void setBusinessRepository(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Autowired
    private void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Autowired
    private void setLancamentoService(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }

    public Business generateNewBusiness(String businessType) {
        Business business = new Business();
        business.setId(gerarBusinessId(businessType));
        business.setDescricao(businessType);
        business.setDataBusiness(LocalDate.now());
        return business;
    }
    //public Business novaAquisicao(Business business, BusinessResource businessResource, HttpServletResponse response) {


    //}

    private Lancamento salvarLancamento(Lancamento lancamento, HttpServletResponse response) {

        return lancamentoService.novoLancamento(lancamento, this, response);
    }

    private String gerarBusinessId(String businessType) {
        String businessId;
        String data = LocalDate.now().toString();
        String sequencia = "000";

        for(int i = 0; i < 1000; i++) {
            sequencia = i + businessType;
        }
        businessId = data + "-" + sequencia;
        return businessId;
    }
}
