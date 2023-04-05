package com.poc.aop.logging.log;

import com.poc.aop.logging.service.EntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class ExecutionTimeLoggerTest {
    @InjectMocks
    ExecutionTimeLogger executionTimeLogger;

    @Mock
    EntityService entityService;

    EntityService proxy;

    @BeforeEach
    void init() {
        AspectJProxyFactory factory = new AspectJProxyFactory(entityService);
        factory.addAspect(executionTimeLogger);
        proxy = factory.getProxy();
    }

    @Test
    void shouldLogTime_onExecutionTimeAspect(CapturedOutput capturedOutput) {
        // when
        proxy.findAll();

        // then
        assertThat(capturedOutput).contains(EntityService.class.getSimpleName())
                .contains("executed in");
    }

}