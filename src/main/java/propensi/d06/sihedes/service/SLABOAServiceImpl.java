package propensi.d06.sihedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.SLABOAModel;
import propensi.d06.sihedes.repository.SLABOADb;
import propensi.d06.sihedes.repository.SLADb;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class SLABOAServiceImpl implements SLABOAService {

    @Autowired
    SLABOADb slaboaDb;

    @Autowired
    SLADb slaDb;

    @Override
    public List<SLABOAModel> getSLABOABySLAId(Long id){
        return  slaboaDb.findAllBySla(slaDb.findById(id).get());
    }

    @Override
    public void addSLABOA(SLABOAModel slaboaModel){slaboaDb.save(slaboaModel);}
}
