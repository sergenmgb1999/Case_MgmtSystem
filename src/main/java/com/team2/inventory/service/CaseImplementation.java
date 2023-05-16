package com.team2.inventory.service;

import com.team2.inventory.interfacemethods.CaseInterface;
import com.team2.inventory.model.Case;
import com.team2.inventory.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CaseImplementation implements CaseInterface {
    @Autowired
    CaseRepository caseRepository;

    @Override
    @Transactional
    public boolean saveCase(Case aCase) {
        if (caseRepository.save(aCase) != null)
            return true;
        else
            return false;
    }

    @Transactional
    public Page<Case> findAllCases(int pageNumber, String sortField, String sortDir) {
        Sort sort=Sort.by(sortField);
        sort=sortDir.equals("asc")?sort.ascending():sort.descending();
        Pageable pageable= PageRequest.of(pageNumber - 1, 5,sort);
        return caseRepository.findAll(pageable);
    }


    @Override
    @Transactional
    public List<Case> findAllCases() {
        return caseRepository.findAll();
    }

    @Override
    @Transactional
    public Case findCaseById(Integer id) {
        return caseRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void deleteCase(Case aCase) {
        caseRepository.delete(aCase);
    }
}