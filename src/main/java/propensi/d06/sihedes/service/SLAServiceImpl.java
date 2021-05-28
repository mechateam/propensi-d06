package propensi.d06.sihedes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.SLAModel;
import propensi.d06.sihedes.repository.SLADb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SLAServiceImpl implements SLAService {
    @Autowired
    SLADb slaDb;

    @Override
    public List<SLAModel> getListSLA(){
        return slaDb.findAll();
    }

    @Override
    public SLAModel getSLAById(Long id){
        return slaDb.findById(id).get();
    }

    @Override
    public void addSLA(SLAModel sla){ slaDb.save(sla); }

    @Override
    public SLAModel updateSLA(SLAModel sla){
        SLAModel targetSLA = slaDb.findById(sla.getId_sla()).get();
        targetSLA.setNama_sla(sla.getNama_sla());
        targetSLA.setDepartemen(sla.getDepartemen());
        targetSLA.setDescription(sla.getDescription());
        targetSLA.setCompletion_time(sla.getCompletion_time());

        return targetSLA;
    }

    @Override
    public void deleteSLA(SLAModel sla){
        slaDb.delete(slaDb.findById(sla.getId_sla()).get());

    }

    @Override
    public List<SLAModel> getAllSLAByDepartemen(DepartemenModel departemen) {
        return slaDb.findAllByDepartemen(departemen);
    }
}
