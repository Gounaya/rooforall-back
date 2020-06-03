package com.rizomm.m2.rooforall.rooforall.entites;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class House implements Serializable {

    private static final long serialVersionUID = -2976554130727238922L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Long price;

    private Long surface;

    private int rooms;

    private String town;

    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    List<File> images = new ArrayList<>();

}
