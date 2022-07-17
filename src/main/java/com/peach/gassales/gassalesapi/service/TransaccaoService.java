package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Transaccao;
import com.peach.gassales.gassalesapi.repository.TransaccaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransaccaoService {
    private TransaccaoRepository transaccaoRepository;

    private ApplicationEventPublisher publisher;

    @Autowired
    public void setTransaccaoRepository(TransaccaoRepository transaccaoRepository) {
        this.transaccaoRepository = transaccaoRepository;
    }

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public Transaccao novaTransaccao(Transaccao transaccao, Object source, HttpServletResponse response) {
        transaccao.setIdTransaccao(gerarIdtransaccao(transaccao.getTipo()));
        Transaccao transaccaoSalva = transaccaoRepository.save(transaccao);
        publisher.publishEvent(new RecursoCriadoEvent(source, response, transaccaoSalva.getId()));
        return transaccaoSalva;
    }

    public String gerarIdtransaccao(String tipoTransaccao) {
        Transaccao transaccao = transaccaoRepository.findTopByOrderByIdDesc();
        String sequencia, transaccaoId;
        LocalDate dataHoje = LocalDate.now();
        if (!(dataHoje.toString().equals(transaccao.getIdTransaccao().toString()))) {
            int savedIdTransaccaoSequence;
            if(dataHoje.toString().equals(transaccao.getDataTransaccao().toString())) {
                savedIdTransaccaoSequence = Integer.parseInt(transaccao.getIdTransaccao().substring(9, transaccao.getIdTransaccao().length() - 1)) + 1;
            }else {
                savedIdTransaccaoSequence = 1;
            }

            int numberOfDigits = (int) (Math.log10(savedIdTransaccaoSequence) + 1);
            switch (numberOfDigits) {
                case 1:
                    sequencia = "00" + savedIdTransaccaoSequence;
                    break;
                case 2:
                    sequencia = "0" + savedIdTransaccaoSequence;
                    break;
                case 3:
                    sequencia = "" + savedIdTransaccaoSequence;
                    break;
                default:
                    sequencia = String.valueOf(savedIdTransaccaoSequence);
            }

            if(tipoTransaccao.equals("Venda"))
                sequencia = sequencia + "V";
            else
                sequencia = sequencia + "A";

            String data = LocalDate.now().toString().replaceAll("-", "");
            transaccaoId = data.concat("-" + sequencia);
        }
        else {
            transaccaoId = transaccao.getIdTransaccao();
        }

        return transaccaoId;
    }

    public List<Transaccao> getTransaccaoByIdTransaccao(String idTransaccao) {
        List<Transaccao> transaccaoList = transaccaoRepository.findAllByIdTransaccao(idTransaccao);
        if (transaccaoList == null)
            throw new EmptyResultDataAccessException(1);
        return transaccaoList;
    }

    public Transaccao actualizar(Long id, Transaccao transaccao) {
        Transaccao transaccaoSalva = transaccaoRepository.findById(id).get();
        BeanUtils.copyProperties(transaccao, transaccaoSalva, "id");
        return transaccaoRepository.save(transaccaoSalva);
    }
}
