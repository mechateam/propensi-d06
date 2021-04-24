package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.SLABOAModel;

import java.util.List;

public interface SLABOAService {
    List<SLABOAModel> getSLABOABySLAId(Long id);
}
