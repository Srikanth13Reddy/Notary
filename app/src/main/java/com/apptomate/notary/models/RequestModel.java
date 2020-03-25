package com.apptomate.notary.models;

import java.util.Comparator;

public class RequestModel
{
    private String userRequestDetailsId;
    private String saasUserId;
    private String requestedDate;
    private String customerId;
    private String serviceId;
    private String status;
    private String comments;
    private String compeletedDate;
    private String assignedTo;
    private String isDocumentTranslated;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String street;
    private String apartment;
    private String city;
    private String state;
    private String postalcode;
    private String country;
    private String countryCode;
    private String serviceName;
    private String price;
    private String description;
    private String dateFrom;
    private String dateTo;
    private String feeDescription;
    private String periodType;
    private String documents;
    private String fullAddress;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getUserRequestDetailsId() {
        return userRequestDetailsId;
    }

    public void setUserRequestDetailsId(String userRequestDetailsId) {
        this.userRequestDetailsId = userRequestDetailsId;
    }

    public String getSaasUserId() {
        return saasUserId;
    }

    public void setSaasUserId(String saasUserId) {
        this.saasUserId = saasUserId;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCompeletedDate() {
        return compeletedDate;
    }

    public void setCompeletedDate(String compeletedDate) {
        this.compeletedDate = compeletedDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getIsDocumentTranslated() {
        return isDocumentTranslated;
    }

    public void setIsDocumentTranslated(String isDocumentTranslated) {
        this.isDocumentTranslated = isDocumentTranslated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getFeeDescription() {
        return feeDescription;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }



    public static Comparator<RequestModel> nameComparator = new Comparator<RequestModel>() {
        @Override
        public int compare(RequestModel jc1, RequestModel jc2) {
            return (int) (jc1.getName().compareTo(jc2.getName()));
        }
    };

    public static Comparator<RequestModel> statusComparator = new Comparator<RequestModel>() {
        @Override
        public int compare(RequestModel jc1, RequestModel jc2) {
            return (int) (jc1.getStatus().compareTo(jc2.getStatus()));
        }
    };

}
