package com.bytech.demo.controller;

import com.bytech.demo.dao.AssetRepository;
import com.bytech.demo.dao.PersonRepository;
import com.bytech.demo.entity.ApiResponse;
import com.bytech.demo.entity.Asset;
import com.bytech.demo.entity.Person;
import com.bytech.demo.service.AssetService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping()
    public ResponseEntity<?> getPersons(){
        List<Asset> assets = assetService.findAll();
        if (!assets.isEmpty()){
            return ResponseEntity.ok(assets);
        }
        return new ResponseEntity<>(new ApiResponse<>(null, "No Asset exist."), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{assetId}/")
    public ResponseEntity<ApiResponse<Asset>> getPerson(
            @PathVariable Integer assetId) {

        if (assetId != null){
            Optional<Asset> asset = assetService.findById(assetId);
            return asset.map(value -> ResponseEntity.ok(new ApiResponse<>(value, "Find Asset success"))).orElseGet(() -> new ResponseEntity<>(new ApiResponse<>(null, "Asset with this id doesn't exist."), HttpStatus.UNAUTHORIZED));

        }

        return new ResponseEntity<>(new ApiResponse<>(null, "Find Asset failed."), HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("")
    public ResponseEntity<?> post(@RequestParam String symbol,
                                                    @RequestParam Integer amount,
                                                    @RequestParam Integer personId) {
        Asset existingAsset = assetRepository.findBySymbolAndPerson_id(symbol,personId).orElse(null);
        if (existingAsset != null) {
            Asset asset = assetService.updateAmount(existingAsset,amount);
            return ResponseEntity.ok(new ApiResponse<>(asset, "Add amount to existing asset."));
        }
        Optional<Person> person = personRepository.findById(personId);
        if (person.isPresent()){
            Asset newAsset = assetService.create(symbol,amount, person.get());
            return ResponseEntity.ok(newAsset);
        }

        return ResponseEntity.ok(new ApiResponse<>(null,"No Person with this person_id exist."));
    }

    @PutMapping("/update/{assetId}/")
    public ResponseEntity<?>updateAsset(HttpServletRequest request, @PathVariable Integer assetId){
        try{
            Optional<Asset> asset = assetRepository.findById(assetId);
            if(asset.isPresent()){
                Asset updateAsset = assetService.updateAsset(asset.get());
                return ResponseEntity.ok(updateAsset);
            }
            return new ResponseEntity<>(new ApiResponse<>(null, "This Asset ID is not exist."), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Person failed");
        }
    }

    @PutMapping("/custom_update/{assetId}/")
    public ResponseEntity<?> customUpdateAsset(@RequestParam String symbol,
                                             @RequestParam Integer amount,
                                             @RequestParam String currency,
                                               @RequestParam Integer last_price,
                                             HttpServletRequest request,
                                             @PathVariable Integer assetId
    ){
        try{
            Optional<Asset> asset = assetRepository.findById(assetId);
            if (asset.isPresent()){
                Asset updateAsset = assetService.customUpdateAsset(asset.get(),symbol,currency,amount,last_price);
                return ResponseEntity.ok(updateAsset);
            }
            return new ResponseEntity<>(new ApiResponse<>(null, "This Person ID is not exist."), HttpStatus.UNAUTHORIZED);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Person failed");
        }
    }

    @DeleteMapping("/delete/{assetId}/")
    public ResponseEntity<?> deleteAsset(HttpServletRequest request,@PathVariable Integer assetId
    ){
        try{
            Optional<Asset> asset = assetRepository.findById(assetId);
            if (asset.isPresent()){
                assetService.delete(asset.get());
                return ResponseEntity.ok("Delete Asset success");
            }
            return new ResponseEntity<>(new ApiResponse<>(null, "This Asset ID is not exist."), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete Asset failed");
        }
    }
}
