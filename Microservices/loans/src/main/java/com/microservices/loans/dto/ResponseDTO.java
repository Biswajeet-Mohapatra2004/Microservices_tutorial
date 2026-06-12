package com.microservices.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "The Response schema",
        description = "It specifies a custom response involving the status code and custom status message"
)
public class ResponseDTO {

    @Schema(
            description = "The status code of the response", example = "200"
    )
    private String statusCode;
    @Schema(
            description = "The status message of the response", example = "Account Created"
    )
    private String statusMsg;
}
