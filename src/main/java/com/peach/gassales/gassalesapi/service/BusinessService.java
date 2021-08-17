package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Business;
import com.peach.gassales.gassalesapi.model.Lancamento;
import com.peach.gassales.gassalesapi.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

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

    public Business novoBusiness(Business business, Lancamento lancamento, Object source, HttpServletResponse response) {
        business.setCodigoBusiness(gerarBusinessId(lancamento.getDescricao()));
        business.setDescricao(lancamento.getDescricao());
        business.setDataBusiness(lancamento.getDataLancamento());
        business.setLancamento(lancamento);

        //business = gerarNovoBusiness(business);
        // TODO Criar Lancamento apartir daqui atraves dos valores do business atribuir lancamento
        // pensar em duas possibilidades, salvar como business, o que esta a acortecer ou salvar como
        // lancamento (metodo criar do resource) depois gerar o business

        Business businessSalvo = businessRepository.save(business);
        publisher.publishEvent(new RecursoCriadoEvent(source, response, businessSalvo.getId()));
        return businessSalvo;
    }

//    public Business gerarNovoBusiness(Business business) {
//        business.setCodigoBusiness(gerarBusinessId(business.getDescricao()));
//        business.setDataBusiness(LocalDate.now());
//        return business;
//    }

    private String gerarBusinessId(String businessType) {
        Business business = businessRepository.findTopByOrderByIdDesc();
        int savedBusinessCodeSequence = Integer.parseInt(business.getCodigoBusiness().substring(9, business.getCodigoBusiness().length() - 1)) + 1;
        String sequencia, businessId;
        int numberOfDigits = (int)(Math.log10(savedBusinessCodeSequence) + 1);
        switch (numberOfDigits) {
            case 1: sequencia = "00" + savedBusinessCodeSequence; break;
            case 2: sequencia = "0" + savedBusinessCodeSequence; break;
            case 3: sequencia = "" + savedBusinessCodeSequence; break;
            default: sequencia = String.valueOf(savedBusinessCodeSequence);
        }

        if (businessType.equals("Venda"))
            sequencia = sequencia + "V";
        else
            sequencia = sequencia + "A";

        String data = LocalDate.now().toString().replaceAll("-", "");
        businessId = data.concat("-" + sequencia);
        return businessId;
    }
}
