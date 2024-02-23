package com.quarkus.developers.services;

import com.quarkus.developers.events.ResourceQuotaInfo;
import com.quarkus.developers.services.messaging.MessagingProducer;
import io.quarkus.scheduler.Scheduled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PollerService {
    private final MessagingProducer messagingProducer;
    private final MonitorFacade monitorFacade;

    @Scheduled(every = "1m")
    void pollResourceQuotas() {
        List<ResourceQuotaInfo> list = monitorFacade.listResourceQuotas("butta");
        list.forEach(messagingProducer::sendMessage);
    }
}
