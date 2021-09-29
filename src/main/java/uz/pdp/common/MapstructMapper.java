package uz.pdp.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import uz.pdp.domain.*;
import uz.pdp.model.*;


import static org.mapstruct.ReportingPolicy.IGNORE;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface MapstructMapper {

    Supplier toSupplier(SupplierAddDto dto);
    Supplier getSupplier(@MappingTarget Supplier supplier, SupplierDto dto);

    Client toClient(ClientAddDto dto);
    Client getClient(@MappingTarget Client client,ClientDto dto);

    Warehouse toWarehouse(WarehouseAddDto dto);
    Warehouse toWarehouse(@MappingTarget Warehouse warehouse, WarehouseDto dto);

    Measurement toMeasurement(MeasurementAddDto dto);
    Measurement toMeasurement(@MappingTarget Measurement measurement, MeasurementDto dto);

    Currency toCurrency(CurrencyAddDto dto);
    Currency toCurrency(@MappingTarget Currency currency, CurrencyDto dto);


    User toUser(UserAddDto dto);

//    @Mapping(target = "category.id", source = "categoryId")
//    Room toRoom(RoomDto dto);




}
