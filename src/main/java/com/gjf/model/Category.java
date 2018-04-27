package com.gjf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.acl.LastOwnerException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;

    private String src;

    private String name;
}