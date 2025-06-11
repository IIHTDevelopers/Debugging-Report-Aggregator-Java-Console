package com.genspark.task5;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    @Test
    void testGenerateFullReport() throws Exception {
        ReportService service = new ReportService();
        long start = System.nanoTime();
        CompletableFuture<String> future = service.generateFullReport("user42");
        String result = future.get(); // waits for completion
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Execution time: " + durationMs + " ms");
        assertTrue(durationMs < 350, "Should return within 350ms");
        assertTrue(result.contains("Activity{user42}"));
        assertTrue(result.contains("Profile{user42}"));
    }
}