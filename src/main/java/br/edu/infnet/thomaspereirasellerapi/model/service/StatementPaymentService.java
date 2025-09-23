package br.edu.infnet.thomaspereirasellerapi.model.service;

import br.edu.infnet.thomaspereirasellerapi.model.domain.CreditCardData;
import br.edu.infnet.thomaspereirasellerapi.model.domain.client.CieloZeroAuthFeignClient;
import org.springframework.stereotype.Service;

@Service
public class StatementPaymentService {

    private CieloZeroAuthFeignClient cieloZeroAuthFeignClient;

    public StatementPaymentService(CieloZeroAuthFeignClient cieloZeroAuthFeignClient) {

        this.cieloZeroAuthFeignClient = cieloZeroAuthFeignClient;
    }

    public Boolean isValidCreditCard(CreditCardData cardNumber, String merchantId, String merchantKey) {

        CieloZeroAuthFeignClient.CieloZeroAuthResponse isValid = cieloZeroAuthFeignClient.checkCreditCard(cardNumber, merchantId, merchantKey);

        return isValid.isValid();

    }


}
