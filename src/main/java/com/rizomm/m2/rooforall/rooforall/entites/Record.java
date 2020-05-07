package com.rizomm.m2.rooforall.rooforall.entites;

import com.rizomm.m2.rooforall.rooforall.enums.RecordStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

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

    @CreatedDate
    private LocalDate creationDate;

    @LastModifiedDate
    private LocalDate editDate;

}
