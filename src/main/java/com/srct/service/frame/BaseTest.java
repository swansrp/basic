/**
 * Title: BaseTest.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-5 21:22
 * @description Project Name: Tanya
 * @Package: com.srct.service.frame
 */
package com.srct.service.frame;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

}

