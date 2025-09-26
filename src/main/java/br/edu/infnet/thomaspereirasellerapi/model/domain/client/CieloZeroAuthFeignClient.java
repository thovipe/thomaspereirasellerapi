package br.edu.infnet.thomaspereirasellerapi.model.domain.client;

import br.edu.infnet.thomaspereirasellerapi.model.domain.dto.CreditCardRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value="CieloZeroAuth", url="${url.cielo.zeroauth.api}")
public interface CieloZeroAuthFeignClient {

    @PostMapping(consumes = "application/json", produces = "application/json")
    CieloZeroAuthResponse checkCreditCard(@RequestBody CreditCardRequestDTO CardNumber, @RequestHeader String MerchantId, @RequestHeader String MerchantKey);

    @JsonIgnoreProperties(ignoreUnknown = true)
    class CieloZeroAuthResponse {

        @JsonProperty("Valid")
        private String valid;

        @JsonProperty("ReturnCode")
        private String returnCode;

        @JsonProperty("ReturnMessage")
        private String returnMessage;

        @JsonProperty("IssuerTransactionId")
        private String issuerTransactionId;

        @JsonProperty("Message")
        private String message;

        @JsonProperty("Code")
        private String code;

        @JsonProperty("Errors")
        private List<String> errors;

        public CieloZeroAuthResponse() {}

        public String getValid() {
            return valid;
        }

        public void setValid(String valid) {
            this.valid = valid;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public String getIssuerTransactionId() {
            return issuerTransactionId;
        }

        public void setIssuerTransactionId(String issuerTransactionId) {
            this.issuerTransactionId = issuerTransactionId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }


        public boolean isValid() {
            return "true".equalsIgnoreCase(valid);
        }

        public boolean hasErrors() {
            return errors != null && !errors.isEmpty();
        }

        public boolean isSuccess() {
            return "0".equals(returnCode) || "00".equals(returnCode);
        }

        @Override
        public String toString() {
            return "CieloZeroAuthResponse{" +
                    "valid='" + valid + '\'' +
                    ", returnCode='" + returnCode + '\'' +
                    ", returnMessage='" + returnMessage + '\'' +
                    ", issuerTransactionId='" + issuerTransactionId + '\'' +
                    ", message='" + message + '\'' +
                    ", code='" + code + '\'' +
                    ", errors=" + errors +
                    '}';
        }
    }
}
