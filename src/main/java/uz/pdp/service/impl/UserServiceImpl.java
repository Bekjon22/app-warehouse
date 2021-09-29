package uz.pdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.common.MapstructMapper;
import uz.pdp.domain.User;
import uz.pdp.domain.Warehouse;
import uz.pdp.model.UserAddDto;
import uz.pdp.model.response.ApiResponse;
import uz.pdp.repository.UserRepo;
import uz.pdp.repository.WarehouseRepo;
import uz.pdp.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static uz.pdp.model.response.ApiResponse.response;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final WarehouseRepo warehouseRepo;
    private final MapstructMapper mapstructMapper;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, WarehouseRepo warehouseRepo, MapstructMapper mapstructMapper) {
        this.userRepo = userRepo;
        this.warehouseRepo = warehouseRepo;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public ResponseEntity<ApiResponse<User>> save(UserAddDto dto) {
        boolean byPhoneNumber = userRepo.existsByPhoneNumber(dto.getPhoneNumber());
        if (byPhoneNumber){
            return response(String.format("This Phone Number  [ %s ] already exist!", dto.getPhoneNumber()), HttpStatus.CONFLICT);

        }
        Set<Warehouse> warehouses = new HashSet<>();
        for (Long aLong : dto.getWarehousesIds()) {

            if (!warehouseRepo.findById(aLong).isPresent()) {
                return response(String.format("This Warehouse id, %s not found!", aLong), HttpStatus.NOT_FOUND);
            }
            if (!warehouseRepo.findById(aLong).get().isActive()){
                return response(String.format("This Warehouse is [ %s ] inactive!", aLong), HttpStatus.NOT_FOUND);
            }
            warehouses.add(warehouseRepo.findById(aLong).get());
        }


        User user = mapstructMapper.toUser(dto);
        user.setWarehouses(warehouses);
        user.setCode(UUID.randomUUID().toString());
        return response(userRepo.save(user));


    }

    @Override
    public ResponseEntity<ApiResponse<User>> get(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        return optionalUser.map(ApiResponse::response).orElseGet(() ->
               response(String.format("This User id, %s not found!", id), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<ApiResponse<Page<User>>> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
        Page<User> users = userRepo.findAll(pageable);
        if (users.isEmpty()) {
            return response("Users not found", HttpStatus.NOT_FOUND);
        }
        return response(users);
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> delete(Long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (!optionalUser.isPresent()) {
            return response(String.format("This User id, %s not found!", id), HttpStatus.NOT_FOUND);
        }
        userRepo.delete(optionalUser.get());
        return response(true);
    }
}
