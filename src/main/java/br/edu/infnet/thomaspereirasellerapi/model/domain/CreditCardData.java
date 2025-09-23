package br.edu.infnet.thomaspereirasellerapi.model.domain;

public class CreditCardData {

      private String cardCreditNumber;
      private String holderName;
      private String expirationDate;
      private String securityCode;
      private String brand;

    public String getCardCreditNumber() {
        return this.cardCreditNumber;
    }

    public void setCardCreditNumber(String cardCreditNumber) {
        this.cardCreditNumber = cardCreditNumber;
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
