package com.example.demo.domain.car

import org.mapstruct.*


@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN)
interface CarMapper {

    @Mappings(
        Mapping(target = "id", source = "carId"),
        Mapping(target = "name", source = "carName"),
        Mapping(target = "tag", source = "carTag")
    )
    fun toCar(dto: CarDto): Car

    @Mappings(
        Mapping(target = "carId", source = "id"),
        Mapping(target = "carName", source = "name"),
        Mapping(target = "carTag", source = "tag")
    )
    @InheritConfiguration
//    @InheritInverseConfiguration
    fun toCarDto(car: Car): CarDto

    @InheritConfiguration(name = "toCar")
    @Mappings(
        Mapping(target = "id", source = "carId"),
        Mapping(target = "name", source = "carName"),
        Mapping(target = "tag", source = "carTag")
    )
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun updateWithNullAsNoChange(carDto: CarDto, @MappingTarget car: Car): Car
}