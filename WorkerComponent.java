package backend.worker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkerComponent {

    private final YourService yourService;

    @Scheduled(cron = "${application.cronExpression}")
    public void execute() {
        log.info("Worker started");
        yourService.yourMethod();
        log.info("Worker finished");
    }
}
