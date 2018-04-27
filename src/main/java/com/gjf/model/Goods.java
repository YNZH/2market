package com.gjf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private Long id;

    private Long userId;

    private String name;

    private String src;

    private BigDecimal price;

    private String category;

    private String location;

    private String campus;

    private Date timeCreate;

    private Date timeModified;
}