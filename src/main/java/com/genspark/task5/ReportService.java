/*package com.genspark.task5;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ReportService {

    private final UserActivityService activityService = new UserActivityService();
    private final UserProfileService profileService = new UserProfileService();

    // Synchronous and slow under load
    public CompletableFuture<String>  generateFullReport(String userId) {
        String activity = activityService.fetchActivityBlocking(userId); // ~280ms
        String profile = profileService.fetchProfileBlocking(userId);   // ~280ms
        return CompletableFuture.completedFuture("REPORT::{" + activity + ", " + profile + "}");
    }

    static class UserActivityService {
        public String fetchActivityBlocking(String userId) {
            try { TimeUnit.MILLISECONDS.sleep(280); } catch (InterruptedException ignored) {}
            return "Activity{" + userId + "}";
        }
    }

    static class UserProfileService {
        public String fetchProfileBlocking(String userId) {
            try { TimeUnit.MILLISECONDS.sleep(280); } catch (InterruptedException ignored) {}
            return "Profile{" + userId + "}";
        }
    }
}*/

package com.genspark.task5;

import java.util.concurrent.*;

public class ReportService {

    private final UserActivityService activityService = new UserActivityService();
    private final UserProfileService profileService = new UserProfileService();
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    // Async refactored method
    public CompletableFuture<String> generateFullReport(String userId) {
        CompletableFuture<String> activityFuture = CompletableFuture.supplyAsync(() -> activityService.fetchActivityBlocking(userId), executor);
        CompletableFuture<String> profileFuture = CompletableFuture.supplyAsync(() -> profileService.fetchProfileBlocking(userId), executor);

        return activityFuture.thenCombine(profileFuture, (activity, profile) ->
            "REPORT::{" + activity + ", " + profile + "}"
        ).orTimeout(300, TimeUnit.MILLISECONDS);
    }

    static class UserActivityService {
        public String fetchActivityBlocking(String userId) {
            try { TimeUnit.MILLISECONDS.sleep(280); } catch (InterruptedException ignored) {}
            return "Activity{" + userId + "}";
        }
    }

    static class UserProfileService {
        public String fetchProfileBlocking(String userId) {
            try { TimeUnit.MILLISECONDS.sleep(280); } catch (InterruptedException ignored) {}
            return "Profile{" + userId + "}";
        }
    }
}