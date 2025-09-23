package br.edu.infnet.thomaspereirasellerapi.model.domain.client;

import br.edu.infnet.thomaspereirasellerapi.model.domain.CreditCardData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value="CieloZeroAuth", url="${url.cielo.zeroauth.api}")
public interface CieloZeroAuthFeignClient {

    @PostMapping
    CieloZeroAuthResponse checkCreditCard(@RequestParam CreditCardData CardNumber, @RequestHeader String MerchantId, @RequestHeader String MerchantKey);

    class CieloZeroAuthResponse {

          private boolean Valid;
          private String ReturnCode;
          private String ReturnMessage;
          private String IssuerTransactionId;
          private String Message;
          private String Code;
          private List<String> Errors;

        public boolean isValid() {
            return Valid;
        }

        public void setValid(boolean valid) {
            Valid = valid;
        }

        public String getReturnCode() {
            return ReturnCode;
        }

        public void setReturnCode(String returnCode) {
            ReturnCode = returnCode;
        }

        public String getReturnMessage() {
            return ReturnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            ReturnMessage = returnMessage;
        }

        public String getIssuerTransactionId() {
            return IssuerTransactionId;
        }

        public void setIssuerTransactionId(String issuerTransactionId) {
            IssuerTransactionId = issuerTransactionId;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public List<String> getErrors() {
            return Errors;
        }

        public void setErrors(List<String> errors) {
            Errors = errors;
        }
    }

}
