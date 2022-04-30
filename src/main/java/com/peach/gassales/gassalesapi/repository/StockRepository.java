package com.peach.gassales.gassalesapi.repository;

import com.peach.gassales.gassalesapi.model.Produto;
import com.peach.gassales.gassalesapi.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findStockByProduto(Produto produto);
}
