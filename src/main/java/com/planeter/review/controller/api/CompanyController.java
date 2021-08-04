package com.planeter.review.controller.api;


import com.planeter.review.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RudeCrab
 */
@RestController
@RequestMapping("/API/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/list")
    public List<Company> list() {
        return companyService.list();
    }

}
