package propensi.d06.sihedes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.BOAModel;
import propensi.d06.sihedes.repository.BOADb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BOAServiceImpl implements BOAService {

    @Autowired
    BOADb boaDb;

    @Override
    public List<BOAModel> findAll(){ return boaDb.findAll();}

    @Override
    public List<BOAModel> findAllByRank(Integer rank){ return boaDb.findAllByRank(rank);}

    @Override
    public Optional<BOAModel> findById(Long id){
        return boaDb.findById(id);
    }
}
