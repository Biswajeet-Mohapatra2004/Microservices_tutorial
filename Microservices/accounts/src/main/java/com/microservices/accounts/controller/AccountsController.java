package com.microservices.accounts.controller;

import com.microservices.accounts.constants.AccountsConstants;
import com.microservices.accounts.dto.AccountsContactInfoDto;
import com.microservices.accounts.dto.CustomerDTO;
import com.microservices.accounts.dto.ErrorResponseDTO;
import com.microservices.accounts.dto.ResponseDTO;
import com.microservices.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


//annotation to improve documentation of openAPI specification
@Tag(
        name = "CRUD REST APIs for Accounts operation in Bank",
        description = "CRUD REST APIs of eazy bank accounts microservices which enables user to perform the create, read, update and delete operations on the resources."
)
@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated // enables the controller to check for the model input validation
public class AccountsController {

    @Value("${build.version}")
    private String buildVersion;

    //using the environment class to fetch the build version from the application properties file
    @Autowired
    private Environment environment;

    @Autowired
    private IAccountsService accountsService;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Operation(
            summary = "A RestAPI endpoint to create an account associated with a new user",
            description = "The endpoint is a POST Type which accepts a Customer details and creates a new account."
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping(path="/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO){
        accountsService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstants.SAVINGS,AccountsConstants.MESSAGE_201));

    }

    // here we are not accepting any DTO body,so we can manually add input validation
    @Operation(
            summary = "RestAPI to fetch Account details",
            description = "The endpoint receives a mobile number as input and checks whether an account exits or not"
    )
    @ApiResponse(
            responseCode = "302",
            description = "HTTP Status FOUND"
    )
    @GetMapping(path = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam("mobileNumber")
                                                               @Pattern(regexp = "($|[0-9]{10})",message = "mobile number cannot be empty or null.")
                                                               String mobileNumber){
        CustomerDTO customerDTO=accountsService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(customerDTO);
    }


    @Operation(
            summary = "A RestAPI endpoint to update the account details",
            description = "The endpoint accepts a put request and updates the account details"
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
                                    implementation = ErrorResponseDTO.class
                            )
                    )
            )

    })
    @PutMapping(path = "/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDTO){
        boolean isUpdated=accountsService.updateAccount(customerDTO);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "A RestAPI endpoint to delete an already existing account.",
            description = "The endpoint accepts a delete request and removes the account details from database"
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
                                    implementation = ErrorResponseDTO.class
                            )
                    )
            )

    })
    @DeleteMapping(path = "/delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam("mobileNumber")
                                                         @Pattern(regexp = "($|[0-9]{10})",message = "mobile number cannot be empty or null.")
                                                         String mobileNumber){
        boolean isDeleted= accountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_DELETE));

        }
    }

    @Operation(
            summary = "A RestAPI endpoint to view the build information of the service.",
            description = "The endpoint returns the build version of the service which is fetched from the application properties file."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    @GetMapping(path = "/build-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @Operation(
            summary = "A RestAPI endpoint to view the java version information of the service.",
            description = "The endpoint returns the java version of the service which is fetched from the application properties file."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    @GetMapping(path = "/java-version", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }


    @Operation(
            summary = "A RestAPI endpoint to get the contact information.",
            description = "The endpoint returns the contact information of the service which is fetched from the application properties file."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status OK"
    )
    @GetMapping(path = "/contact-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
    }
}
