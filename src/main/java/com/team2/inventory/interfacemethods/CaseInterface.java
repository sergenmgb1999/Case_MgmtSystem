package com.team2.inventory.interfacemethods;

import com.team2.inventory.model.Case;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.List;

public interface CaseInterface {
    public boolean saveCase(Case aCase);
    public List<Case> findAllCases();
    public Case findCaseById(Integer id);
    public void deleteCase(Case aCase);
    public Page<Case> findAllCases(int pageNumber, String sortField, String sortDir);
}
