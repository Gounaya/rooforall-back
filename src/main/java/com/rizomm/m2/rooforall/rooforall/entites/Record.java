package com.rizomm.m2.rooforall.rooforall.entites;

import com.rizomm.m2.rooforall.rooforall.enums.RecordStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Record implements Serializable {

    private static final long serialVersionUID = -5365906240360589667L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RecordStatus status;

    private boolean draft;

    private String habitatType;

    private Long budgetMin;

    private Long budgetMax;

    private Long habitationSurface;

    private String town;

    @OneToMany(fetch = LAZY, cascade = {MERGE, PERSIST}) // TODO: remove and replace with ManyToMany (make changes in House entity)
    @JoinColumn(name = "record_id")
    List<House> houseList = new ArrayList<>();

/*    @ManyToMany
    @JoinTable(
            name = "records_houses",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id"))
    Collection<House> linkedHouses;*/

    @CreatedDate
    private LocalDate creationDate;

    @LastModifiedDate
    private LocalDate editDate;

}
