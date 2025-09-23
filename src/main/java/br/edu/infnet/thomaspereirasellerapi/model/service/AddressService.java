package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.Address;
import br.edu.infnet.thomaspereirasellerapi.model.domain.client.OpenCepFeignClient;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final OpenCepFeignClient openCepFeignClient;

    public AddressService(OpenCepFeignClient openCepFeignClient) {
        this.openCepFeignClient = openCepFeignClient;

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


}


