package com.example.basicrestapi3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyFactScheduler {

    private static final Logger log = LoggerFactory.getLogger(DailyFactScheduler.class);

    @Autowired
    private FactPostingService factPostingService;

    @Scheduled(cron = "${fact.posting.cron}")
    public void postScheduledFact() {
        log.info("Running daily fun fact posting job.");
        factPostingService.postDailyFact();
    }
}
