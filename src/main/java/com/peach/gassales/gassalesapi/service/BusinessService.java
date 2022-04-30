package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Business;
import com.peach.gassales.gassalesapi.model.Lancamento;
import com.peach.gassales.gassalesapi.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

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
        business.setCodigoBusiness(gerarBusinessCode(lancamento.getDescricao()));
        business.setDescricao(lancamento.getDescricao());
        business.setDataBusiness(lancamento.getDataLancamento());
        //business.setLancamento(lancamento);

        Business businessSalvo = businessRepository.save(business);
        publisher.publishEvent(new RecursoCriadoEvent(source, response, businessSalvo.getId()));
        return businessSalvo;
    }

    public void finalizarBusiness(String codigoBusiness, boolean finalizado) {
        List<Business> businessList = getBusinessByCodigoBusiness(codigoBusiness);
        businessList.forEach(business -> {
            business.setFinalizado(finalizado);
            businessRepository.save(business);
        });
    }

    private List<Business> getBusinessByCodigoBusiness(String codigoBusiness) {
        List<Business> businessList = businessRepository.findAllByCodigoBusiness(codigoBusiness);
        if(businessList == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return businessList;
    }

    public String gerarBusinessCode(String businessType) {
        Business business = businessRepository.findTopByOrderByIdDesc();
        String sequencia, businessId;
        LocalDate dataHoje = LocalDate.now();
        if(business.isFinalizado() || !(dataHoje.toString().equals(business.getDataBusiness().toString()))) {

            int savedBusinessCodeSequence;
            if(dataHoje.toString().equals(business.getDataBusiness().toString())) {
                savedBusinessCodeSequence = Integer.parseInt(business.getCodigoBusiness().substring(9, business.getCodigoBusiness().length() - 1)) + 1;
            }else {
                savedBusinessCodeSequence = 1;
            }

            int numberOfDigits = (int) (Math.log10(savedBusinessCodeSequence) + 1);
            switch (numberOfDigits) {
                case 1:
                    sequencia = "00" + savedBusinessCodeSequence;
                    break;
                case 2:
                    sequencia = "0" + savedBusinessCodeSequence;
                    break;
                case 3:
                    sequencia = "" + savedBusinessCodeSequence;
                    break;
                default:
                    sequencia = String.valueOf(savedBusinessCodeSequence);
            }

            if (businessType.equals("Venda"))
                sequencia = sequencia + "V";
            else
                sequencia = sequencia + "A";

            String data = LocalDate.now().toString().replaceAll("-", "");
            businessId = data.concat("-" + sequencia);
        }
        else {
            businessId = business.getCodigoBusiness();
        }
        return businessId;
    }
}
