package com.gjf.model;

import java.math.BigDecimal;
import java.util.Date;

public class Goods {
    private Long id;

    private Long userId;

    private String name;

    private String src;

    private BigDecimal price;

    private String location;

    private String campus;

    private Date timeCreate;

    private Date timeModified;

    public Goods(Long id, Long userId, String name, String src, BigDecimal price, String location, String campus, Date timeCreate, Date timeModified) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.src = src;
        this.price = price;
        this.location = location;
        this.campus = campus;
        this.timeCreate = timeCreate;
        this.timeModified = timeModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src == null ? null : src.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus == null ? null : campus.trim();
    }

    public Date getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Date timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Date getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(Date timeModified) {
        this.timeModified = timeModified;
    }
}