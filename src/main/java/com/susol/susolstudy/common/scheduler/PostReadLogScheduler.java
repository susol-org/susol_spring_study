package com.susol.susolstudy.common.scheduler;

import com.susol.susolstudy.dao.PostReadLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class PostReadLogScheduler {

    private final PostReadLogRepository postReadLogRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteOldReadLogs() {
        postReadLogRepository.deleteBeforeDate(LocalDate.now());
    }
}
