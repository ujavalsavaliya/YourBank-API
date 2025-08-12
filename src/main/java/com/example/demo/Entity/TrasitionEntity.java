package com.example.demo.Entity;

import java.time.LocalDateTime;

public class TrasitionEntity {
    private String type;
    private Long amount;

    public TrasitionEntity(String type, Long amount, LocalDateTime date , String method) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.method = method;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private LocalDateTime date;
    private String method;
}
