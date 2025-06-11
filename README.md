# AsyncReportAggregator

## Overview
This project demonstrates asynchronous report generation by fetching data from two external services in parallel using `CompletableFuture`. The goal is to maintain 95th percentile latency under 300 ms under high load.

## How to Run
1. Build the project using Maven:
   ```bash
   mvn clean install
   ```

2. Run the test using:
   ```bash
   mvn test
   ```

## Key Concepts
- CompletableFuture
- thenCombine for parallel async composition
- Custom ThreadPool
- Timeout handling using `orTimeout`