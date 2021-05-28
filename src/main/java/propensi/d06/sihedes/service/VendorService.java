package propensi.d06.sihedes.service;

import propensi.d06.sihedes.model.VendorModel;

import java.util.List;

public interface VendorService {
    List<VendorModel> getListVendor();
    VendorModel getVendorbyId (Long id);
    void addVendor(VendorModel vendor);
    VendorModel updateVendor(VendorModel vendor);
    void deleteVendor(VendorModel vendor);

}
