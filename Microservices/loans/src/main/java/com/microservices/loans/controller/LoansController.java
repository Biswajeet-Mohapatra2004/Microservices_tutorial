package com.microservices.loans.controller;

import com.microservices.loans.constants.LoansConstants;
import com.microservices.loans.dto.ErrorResponseDto;
import com.microservices.loans.dto.LoansDTO;
import com.microservices.loans.dto.ResponseDTO;
import com.microservices.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Loans operation in Bank",
        description = "CRUD REST APIs of the eazy bank loans microservice which enables user to perform create,read, update and delete operations on resources"
)
@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoansController {

    @Autowired
    private ILoansService iLoansService;

    @Operation(
            summary = "A restAPI endpoint to create a loan associated with the mobile number of the user",
            description = "The endpoint is a POST type which accepts a Loans details and creates a Loan entity."
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDTO> createLoan(@RequestParam @Pattern(regexp = "^[0-9]{10}$",message = "mobileNumber must be of 10 digits") String mobileNumber){
        iLoansService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO("201", LoansConstants.STATUS_201));
    }


    @Operation(
            summary = "RestAPI to fetch Loan details",
            description = "The endpoint receives a mobile number as input and checks whether an loan exits or not"
    )
    @ApiResponse(
            responseCode = "302",
            description = "HTTP Status FOUND"
    )
    @GetMapping(path = "/fetch")
    public ResponseEntity<LoansDTO> fetchLoanDetails(@RequestParam @Pattern(regexp = "^[0-9]{10}$",message = "mobileNumber must be of 10 digits") String mobileNumber){
        LoansDTO loansDTO=iLoansService.fetchLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansDTO);
    }

    @Operation(
            summary = "A RestAPI endpoint to update the loans details",
            description = "The endpoint accepts a put request and updates the loans details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )

    })
    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDTO> updateLoanDetails(@Valid @RequestBody LoansDTO loansDTO){
        boolean isUpdated = iLoansService.updateLoan(loansDTO);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO("200",LoansConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO("417",LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "A RestAPI endpoint to update the loans details",
            description = "The endpoint accepts a put request and updates the loans details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )

    })
    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDTO> deleteLoanDetails(@RequestParam @Pattern(regexp = "^[0-9]{10}$",message = "Mobile number must of 10 digit") String mobileNumber){
        boolean isDeleted=iLoansService.deleteLoan(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO("200",LoansConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO("417",LoansConstants.MESSAGE_417_DELETE));
        }
    }
}
