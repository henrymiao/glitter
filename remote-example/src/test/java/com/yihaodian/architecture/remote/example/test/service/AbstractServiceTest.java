package com.yihaodian.architecture.remote.example.test.service;

import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath:applicationContext-test.xml")
public abstract class AbstractServiceTest  extends AbstractJUnit4SpringContextTests  {

}
