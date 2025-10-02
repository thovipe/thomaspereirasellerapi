package br.edu.infnet.thomaspereirasellerapi.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class CreditCardData {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      @NotBlank(message="Credit Card Number is a mandatory field.")
      private String creditCardNumber;
      @NotBlank(message="Holder name is a mandatory field.")
      private String holderName;
      private String expirationDate;
      @NotBlank(message = "Security code is mandatory field.")
      private String securityCode;

      private String brand;

    public String getCreditCardNumber() {
        return this.creditCardNumber;
    }

    public void setCreditCardNumber(String cardCreditNumber) {
        this.creditCardNumber = cardCreditNumber;
    }

    public String getHolderName() {
        return this.holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return this.securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
