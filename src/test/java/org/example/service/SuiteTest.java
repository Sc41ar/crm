package org.example.service;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ClientServiceTest.class, CompanyServiceTest.class, DealServiceTest.class, ProductDealServiceTest.class,
        ProductServiceTest.class})
public class SuiteTest {

}
