package com.rizomm.m2.rooforall.rooforall.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bucketName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<File> files = new ArrayList<>();
}
