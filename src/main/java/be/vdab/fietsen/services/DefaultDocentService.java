package be.vdab.fietsen.services;

import be.vdab.fietsen.exceptions.DocentNietGevondenException;
import be.vdab.fietsen.repositories.DocentRepository;

import java.math.BigDecimal;

class DefaultDocentService implements DocentService{
    private final DocentRepository repository;

    DefaultDocentService(DocentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void opslag(long id, BigDecimal percentage) {
        repository.findById(id)
                .orElseThrow(DocentNietGevondenException::new)
                .opslag(percentage);
    }
}
