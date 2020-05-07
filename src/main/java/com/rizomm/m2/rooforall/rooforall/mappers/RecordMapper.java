package com.rizomm.m2.rooforall.rooforall.mappers;


import com.rizomm.m2.rooforall.rooforall.dto.RecordDto;
import com.rizomm.m2.rooforall.rooforall.entites.Record;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RecordMapper {

    RecordDto toDto(Record record);

    List<RecordDto> toDtos(List<Record> records);

    Record toBo(RecordDto recordDto);

    List<Record> toBos(List<RecordDto> recordDtos);


}
