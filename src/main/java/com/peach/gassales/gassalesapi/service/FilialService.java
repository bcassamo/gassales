package com.peach.gassales.gassalesapi.service;

import com.peach.gassales.gassalesapi.model.Filial;
import com.peach.gassales.gassalesapi.repository.FilialRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilialService {
    private FilialRepository filialRepository;

    @Autowired
    private void setFilialRepository(FilialRepository filialRepository) {
        this.filialRepository = filialRepository;
    }

    public Filial actualizar(Long id, Filial filial) {
        // Se não localizar lança NoSuchElementException
        Filial filialSalva = filialRepository.findById(id).get();
        BeanUtils.copyProperties(filial, filialSalva, "id");
        return filialRepository.save(filialSalva);
    }

    /**
     * Procura a Filial pelo id
     * @param id parametro
     * @return Filial Optional
     * @throws EmptyResultDataAccessException Se o resultado for vazio lanca a excepcao
     */
    public Optional<Filial> buscarFilialPeloCodigo(Long id) {
        Optional<Filial> filial = filialRepository.findById(id);
        if(filial.isEmpty())
            throw new EmptyResultDataAccessException(1);
        return filial;
    }
}
