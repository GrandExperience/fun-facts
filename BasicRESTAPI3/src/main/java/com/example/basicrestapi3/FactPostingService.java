package com.example.basicrestapi3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactPostingService {

    private static final Logger log = LoggerFactory.getLogger(FactPostingService.class);

    @Autowired
    private FactDao factDao;

    @Autowired
    private ActiveFactDao activeFactDao;

    public boolean postDailyFact() {
        Fact selected = factDao.getRandomActive();
        if (selected == null) {
            log.warn("Daily posting skipped: no active facts available in the database.");
            return false;
        }

        ActiveFact activeFact = new ActiveFact(selected);
        activeFactDao.save(activeFact);
        factDao.deactivate(selected);

        log.info("Posted fact #{} to active table and deactivated it.", selected.getId());
        return true;
    }
}