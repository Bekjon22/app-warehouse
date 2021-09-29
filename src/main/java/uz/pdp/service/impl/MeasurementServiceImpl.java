package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.common.MapstructMapper;
import uz.pdp.domain.Measurement;
import uz.pdp.model.MeasurementAddDto;
import uz.pdp.model.MeasurementDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.MeasurementRepo;
import uz.pdp.service.MeasurementService;

import java.util.Optional;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepo measurementRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepo measurementRepo, MapstructMapper mapstructMapper) {
        this.measurementRepo = measurementRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<Measurement>> save(MeasurementAddDto measurementDto) {
        boolean existsByName = measurementRepo.existsByName(measurementDto.getName());
        if (existsByName){
            return  response(String.format("This Measurement Name [ %s ] already exist!", measurementDto.getName()), HttpStatus.CONFLICT);
        }
        return response(measurementRepo.save(mapstructMapper.toMeasurement(measurementDto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Measurement>> get(Long id) {

        Optional<Measurement> optionalMeasurement = measurementRepo.findById(id);
        if (!optionalMeasurement.isPresent()) {
            return response(String.format("This Measurement id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        return response(optionalMeasurement.get());
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Measurement>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<Measurement> measurements = measurementRepo.findAll(pageable);
        if (measurements.isEmpty()) {
            return response("Measurements not found", HttpStatus.NOT_FOUND);
        }
        return response(measurements);
    }

    @Override
    public ResponseEntity<ApiResponse<Measurement>> update(Long id, MeasurementDto dto) {
        Optional<Measurement> optionalMeasurement = measurementRepo.findById(id);
        if (!optionalMeasurement.isPresent()) {
            return response(String.format("This Measurement id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        Measurement byNameAndIdNot = measurementRepo.findByNameAndIdNot(dto.getName(), id);
        if (byNameAndIdNot != null) {
            return response(String.format("This Measurement name , %s already exist!", dto.getName()), HttpStatus.CONFLICT);
        }
        return response(measurementRepo.save(mapstructMapper.toMeasurement(optionalMeasurement.get(),dto)));
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {

        Optional<Measurement> optionalMeasurement = measurementRepo.findById(id);
        if (!optionalMeasurement.isPresent()) {
            return response(String.format("This Measurement id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        measurementRepo.delete(optionalMeasurement.get());
        return response(true);
    }

}
