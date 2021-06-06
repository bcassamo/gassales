package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.event.RecursoCriadoEvent;
import com.peach.gassales.gassalesapi.model.Lancamento;
import com.peach.gassales.gassalesapi.model.Produto;
import com.peach.gassales.gassalesapi.model.Stock;
import com.peach.gassales.gassalesapi.repository.LancamentoRepository;
import com.peach.gassales.gassalesapi.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {
    private LancamentoRepository lancamentoRepository;
    private StockRepository stockRepository;
    private ApplicationEventPublisher publisher;

    @Autowired
    private void setLancamentoRepository(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Autowired
    private void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Autowired
    private void setPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public Optional<Lancamento> buscarLancamentoPeloCodigo(Long id) {
        Optional<Lancamento> lancamento = lancamentoRepository.findById(id);
        if (lancamento.isEmpty())
            throw new EmptyResultDataAccessException(1);
        return lancamento;
    }

    public Lancamento novoLancamento(Lancamento lancamento, Object source, HttpServletResponse response) {
        salvarStock(lancamento.getProduto(), lancamento.getQuantidade());
        Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(source, response, lancamentoSalvo.getId()));
        return lancamentoSalvo;
    }

    private void salvarStock(Produto produto, Long novaQuantidade) {
        Stock stock = localizarStock(produto);
        if(stock == null) {
            stock = new Stock();
            stock.setProduto(produto);
            stock.setQuantidade(novaQuantidade);
        } else {
            stock.setQuantidade(incrementarStock(stock.getQuantidade(), novaQuantidade));
        }
        stockRepository.save(stock);
    }

    private Stock localizarStock(Produto produto) {
        List<Stock> stocks = stockRepository.findAll();

        for (Stock stockSalvo: stocks) {
            if(stockSalvo.getProduto().getId().equals(produto.getId())) {
                return stockSalvo;
            }
        }
        return null;
    }

    private Long incrementarStock(Long quantidadeAntiga, Long novaQuantidade) {
        return quantidadeAntiga + novaQuantidade;
    }
}
