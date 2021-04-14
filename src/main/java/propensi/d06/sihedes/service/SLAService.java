package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.SLAModel;

import java.util.List;

public interface SLAService {
    List<SLAModel> getListSLA();
    SLAModel getSLAById(Long id);
    void addSLA(SLAModel sla);
    SLAModel updateSLA(SLAModel sla);
    void deleteSLA(SLAModel sla);
}
