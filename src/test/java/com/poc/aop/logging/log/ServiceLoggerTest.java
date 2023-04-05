package com.poc.aop.logging.log;

import com.poc.aop.logging.entity.Entity;
import com.poc.aop.logging.exception.EntityNotFoundException;
import com.poc.aop.logging.service.EntityService;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.Mockito.doThrow;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class ServiceLoggerTest {
    private static final int ID = 1;
    private static final String DESCRIPTION = "desc";

    @InjectMocks
    ServiceLogger serviceLogger;

    @Mock
    EntityService entityService;

    EntityService proxy;

    @BeforeEach
    void init() {
        AspectJProxyFactory factory = new AspectJProxyFactory(entityService);
        factory.addAspect(serviceLogger);
        proxy = factory.getProxy();
    }

    @Test
    void shouldLogException_onServiceAspect(CapturedOutput capturedOutput) {
        // given
        doThrow(EntityNotFoundException.class).when(entityService).delete(ID);

        // when
        Assertions.assertThrows(EntityNotFoundException.class, () -> proxy.delete(ID));

        // then
        assertThat(capturedOutput).contains(EntityNotFoundException.class.getSimpleName())
                .contains(EntityService.class.getSimpleName());
    }

    @Test
    void shouldLogDataChange_onServiceAspect(CapturedOutput capturedOutput) {
        // when
        proxy.persist(new Entity(ID, DESCRIPTION));

        // then
        assertThat(capturedOutput).contains("Repository data change happened")
                .contains(EntityService.class.getSimpleName())
                .contains(String.valueOf(ID))
                .contains(DESCRIPTION);
    }

    @Test
    void shouldLogEntityOperation_onServiceAspect(CapturedOutput capturedOutput) {
        // when
        proxy.update(new Entity(ID, DESCRIPTION), ID);

        // then
        assertThat(capturedOutput).contains("Entity operation")
                .contains(EntityService.class.getSimpleName())
                .contains(String.valueOf(ID))
                .contains(DESCRIPTION);
    }
}