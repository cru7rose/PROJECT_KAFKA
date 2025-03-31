package com.example.APS.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class Order {
    private String barcode;
    private String barcode2;
    private String customerCode;
    private String alias;
    private String addressCode;
    private boolean deliveryOrder;
    private String orderRef;
    private String routeCode;
    private int wave;
    private String routeDescription;
    private String externalRouteCode;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime routeDate;
    private String routeStartTime;
    private String routeEndTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime orderDate;

    private int coli;
    private double weight;
    private double volume;
    private double height;
    private double length;
    private double width;
    private double amount;
    private String productCode;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime eftDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime lttDate;

    private String eftTime;
    private String lttTime;
    private String note;
    private String itemType;
    private String returnUnitCode;
    private int handlingTime;
    private String zoneSetCode;
    private String hubExternalId;
    private String dock;
    private int lineNumber;
    private String fileName;
    private String driverCode;
    private String vehicleCode;
    private String metadata;
    private String parentOrder;
    private int addressId;
    private String type;
    private int externalRouteId;
    private int externalRoutePartNo;
    private int sendingId;
    private int productId;
    private String message;
    private boolean feederRouteOrder;
    private int stopTime;
    private String orderType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime importDate;

    private String hubClientExternalId;
    private String finalCustomerCode;
    private String palletBarcode;
    private String defaultScanReportCode;

    // Gettery i Settery

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getBarcode2() { return barcode2; }
    public void setBarcode2(String barcode2) { this.barcode2 = barcode2; }

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getAddressCode() { return addressCode; }
    public void setAddressCode(String addressCode) { this.addressCode = addressCode; }

    public boolean isDeliveryOrder() { return deliveryOrder; }
    public void setDeliveryOrder(boolean deliveryOrder) { this.deliveryOrder = deliveryOrder; }

    public String getOrderRef() { return orderRef; }
    public void setOrderRef(String orderRef) { this.orderRef = orderRef; }

    public String getRouteCode() { return routeCode; }
    public void setRouteCode(String routeCode) { this.routeCode = routeCode; }

    public int getWave() { return wave; }
    public void setWave(int wave) { this.wave = wave; }

    public String getRouteDescription() { return routeDescription; }
    public void setRouteDescription(String routeDescription) { this.routeDescription = routeDescription; }

    public String getExternalRouteCode() { return externalRouteCode; }
    public void setExternalRouteCode(String externalRouteCode) { this.externalRouteCode = externalRouteCode; }

    public LocalDateTime getRouteDate() { return routeDate; }
    public void setRouteDate(LocalDateTime routeDate) { this.routeDate = routeDate; }

    public String getRouteStartTime() { return routeStartTime; }
    public void setRouteStartTime(String routeStartTime) { this.routeStartTime = routeStartTime; }

    public String getRouteEndTime() { return routeEndTime; }
    public void setRouteEndTime(String routeEndTime) { this.routeEndTime = routeEndTime; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public int getColi() { return coli; }
    public void setColi(int coli) { this.coli = coli; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public LocalDateTime getEftDate() { return eftDate; }
    public void setEftDate(LocalDateTime eftDate) { this.eftDate = eftDate; }

    public LocalDateTime getLttDate() { return lttDate; }
    public void setLttDate(LocalDateTime lttDate) { this.lttDate = lttDate; }

    public String getEftTime() { return eftTime; }
    public void setEftTime(String eftTime) { this.eftTime = eftTime; }

    public String getLttTime() { return lttTime; }
    public void setLttTime(String lttTime) { this.lttTime = lttTime; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }

    public String getReturnUnitCode() { return returnUnitCode; }
    public void setReturnUnitCode(String returnUnitCode) { this.returnUnitCode = returnUnitCode; }

    public int getHandlingTime() { return handlingTime; }
    public void setHandlingTime(int handlingTime) { this.handlingTime = handlingTime; }

    public String getZoneSetCode() { return zoneSetCode; }
    public void setZoneSetCode(String zoneSetCode) { this.zoneSetCode = zoneSetCode; }

    public String getHubExternalId() { return hubExternalId; }
    public void setHubExternalId(String hubExternalId) { this.hubExternalId = hubExternalId; }

    public String getDock() { return dock; }
    public void setDock(String dock) { this.dock = dock; }

    public int getLineNumber() { return lineNumber; }
    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getDriverCode() { return driverCode; }
    public void setDriverCode(String driverCode) { this.driverCode = driverCode; }

    public String getVehicleCode() { return vehicleCode; }
    public void setVehicleCode(String vehicleCode) { this.vehicleCode = vehicleCode; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }

    public LocalDateTime getImportDate() { return importDate; }
    public void setImportDate(LocalDateTime importDate) { this.importDate = importDate; }

    public String getHubClientExternalId() { return hubClientExternalId; }
    public void setHubClientExternalId(String hubClientExternalId) { this.hubClientExternalId = hubClientExternalId; }

    public String getFinalCustomerCode() { return finalCustomerCode; }
    public void setFinalCustomerCode(String finalCustomerCode) { this.finalCustomerCode = finalCustomerCode; }

    public String getPalletBarcode() { return palletBarcode; }
    public void setPalletBarcode(String palletBarcode) { this.palletBarcode = palletBarcode; }

    public String getDefaultScanReportCode() { return defaultScanReportCode; }
    public void setDefaultScanReportCode(String defaultScanReportCode) { this.defaultScanReportCode = defaultScanReportCode; }
}