package propensi.d06.sihedes.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.d06.sihedes.model.DepartemenModel;
import propensi.d06.sihedes.model.SLAModel;
import propensi.d06.sihedes.model.VendorModel;
import propensi.d06.sihedes.repository.VendorDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorDb vendorDb;

    @Override
    public List<VendorModel> getListVendor(){
        return vendorDb.findAll();
    }

    @Override
    public VendorModel getVendorbyId(Long id){
        return vendorDb.findById(id).get();
    }

    @Override
    public void addVendor(VendorModel vendor){ vendorDb.save(vendor); }

    @Override
    public VendorModel updateVendor(VendorModel vendor) {
        VendorModel targetVendor = vendorDb.findById(vendor.getId_vendor()).get();
        targetVendor.setNama(vendor.getNama());
        targetVendor.setNo_telp(vendor.getNo_telp());
        targetVendor.setDescription(vendor.getDescription());
        return targetVendor;
    }

    @Override
    public void deleteVendor(VendorModel vendor) {
        vendorDb.delete(vendorDb.findById(vendor.getId_vendor()).get());
    }

}
