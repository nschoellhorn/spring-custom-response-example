package com.example.springcustomresponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/doesntFail")
    public ResponseEntity<CustomResponseType<String>> canFailButDoesnt() {
        return CustomResponseType.success("Success!").toResponseEntity();
    }

    @GetMapping("/fails")
    public ResponseEntity<CustomResponseType<String>> canFailAndDoesAsWell() {
        return CustomResponseType.<String>error(ErrorCode.SOMETHING).toResponseEntity();
    }

}
