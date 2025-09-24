package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Address;
import br.edu.infnet.thomaspereirasellerapi.model.domain.client.OpenCepFeignClient;
import br.edu.infnet.thomaspereirasellerapi.model.domain.repository.AddressRepository;
import br.edu.infnet.thomaspereirasellerapi.model.exception.AddressNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private final OpenCepFeignClient openCepFeignClient;
    private final AddressRepository addressRepository;

    public AddressService(OpenCepFeignClient openCepFeignClient,  AddressRepository addressRepository) {
        this.openCepFeignClient = openCepFeignClient;
        this.addressRepository = addressRepository;

    }

    public Address getSellerAddress(String zipCode, String complement, Integer number) {

        zipCode = zipCode.replace("-","").trim();

        Address address = new Address();
        OpenCepFeignClient.OpenCepAddressResponse response = openCepFeignClient.getAddress(zipCode);
        address.setCity(response.getLocalidade());
        address.setStreetName(response.getLogradouro());
        address.setDistrict(response.getBairro());
        address.setState(response.getEstado());
        address.setComplement(complement);
        address.setNumber(number);
        address.setZipCode(zipCode);

        return address;
    }

    public Address getAddress(Long id) {
        return addressRepository.findAddressById(id).orElseThrow(() -> new AddressNotFoundException("Address with id: "+ id +" not found."));
    }

    public Address addAddress(Address address) {
        Optional<Address> addressFromRepository = addressRepository.findAddressById(address.getId());
        if (addressFromRepository.isPresent()) {
            return addressFromRepository.get();
        }
        return addressRepository.save(address);
    }

}


