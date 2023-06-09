package dev.paul.restdemo.controller;

import dev.paul.restdemo.model.CloudVendor;
import dev.paul.restdemo.response.ResponseHandler;
import dev.paul.restdemo.service.CloudVendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cloudvendor")
public class CloudVendorController {

    CloudVendorService cloudVendorService;

    public CloudVendorController(CloudVendorService cloudVendorService) {
        this.cloudVendorService = cloudVendorService;
    }

    // Read Specific Cloud Vendor Details
    @GetMapping("/id/{vendorId}")
    public ResponseEntity<Object> getCloudVendorDetails(@PathVariable("vendorId") String vendorId) {
        return ResponseHandler.responseBuilder("Requested vendor details are given here",
                HttpStatus.OK, cloudVendorService.getCloudVendor(vendorId));
    }

    // Read All Cloud Vendor Details from DB
    @GetMapping
    public List<CloudVendor>  getAllCloudVendorDetails() {
        return cloudVendorService.getAllCloudVendors();
    }

    // Read cloud vendor by name
    @GetMapping("/name/{vendorName}")
    public ResponseEntity<?> getCloudVendorByName(@PathVariable("vendorName") String vendorName){
        return new ResponseEntity<>(cloudVendorService.getByVendorName(vendorName),HttpStatus.OK);
    }

    // create cloud Vendor
    @PostMapping
    public String createCloudVendorDetails(@RequestBody CloudVendor cloudVendor) {
        cloudVendorService.createCloudVendor(cloudVendor);
        return "Cloud Vendor created successfully";
    }

    @PutMapping
    public String updateCloudVendorDetails(@RequestBody CloudVendor cloudVendor) {
        cloudVendorService.updateCloudVendor(cloudVendor);
        return "Cloud Vendor updated successfully";
    }

    @DeleteMapping("{vendorId}")
    public String deleteCloudVendorDetails(@PathVariable("vendorId") String vendorId) {
        cloudVendorService.deleteCloudVendor(vendorId);
        return "Cloud Vendor deleted successfully";
    }
}
