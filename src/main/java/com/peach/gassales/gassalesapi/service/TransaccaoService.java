package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Produto;
import com.peach.gassales.gassalesapi.model.Stock;
import com.peach.gassales.gassalesapi.model.Transaccao;
import com.peach.gassales.gassalesapi.repository.StockRepository;
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

    private StockRepository stockRepository;

    private ApplicationEventPublisher publisher;

    @Autowired
    public void setTransaccaoRepository(TransaccaoRepository transaccaoRepository) {
        this.transaccaoRepository = transaccaoRepository;
    }

    @Autowired
    private void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Autowired
    public void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public Transaccao novaTransaccao(Transaccao transaccao, Object source, HttpServletResponse response) {
        transaccao.setIdTransaccao(gerarIdtransaccao(transaccao.getTipo()));
        salvarStock(transaccao);
        Transaccao transaccaoSalva = transaccaoRepository.save(transaccao);
        publisher.publishEvent(new RecursoCriadoEvent(source, response, transaccaoSalva.getId()));
        return transaccaoSalva;
    }

    private String gerarIdtransaccao(String tipoTransaccao) {
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

    private void salvarStock(Transaccao transaccao) {
        Stock stock = stockRepository.findStockByProduto(transaccao.getProduto());
        if (transaccao.getTipo().equals("Venda") & stock != null) {
            if (stock.getQuantidade() < transaccao.getQuantidade())
                throw new IllegalStateException();
            stock.setQuantidade(decrementarStock(stock.getQuantidade(), transaccao.getQuantidade()));
        } else if (transaccao.getTipo().equals("Venda") & stock == null) {
            throw new IllegalStateException();
        } else if (transaccao.getTipo().equals("Aquisição") & stock == null) {
            stock = new Stock();
            stock.setQuantidade(transaccao.getQuantidade());
            stock.setProduto(transaccao.getProduto());
        } else {
            stock.setQuantidade(incrementarStock(stock.getQuantidade(), transaccao.getQuantidade()));
        }
        stockRepository.save(stock);
    }

    private Long incrementarStock(Long quantidadeAntiga, Long novaQuantidade) {
        return quantidadeAntiga + novaQuantidade;
    }

    private Long decrementarStock(Long quantidadeAntiga, Long novaQuantidade) {
        return quantidadeAntiga - novaQuantidade;
    }
}
