# Java Database Caching Service 

A high-performance caching layer implemented in Java to accelerate slow database queries using a custom Least Recently Used (LRU) eviction strategy. This project serves as a practical demonstration of core data structures and system design principles for performance optimization.

![Java](https://img.shields.io/badge/Java-11+-orange?style=for-the-badge&logo=java)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

## The Problem: High Latency Queries 

In many systems, repeatedly fetching the same data from a database can lead to significant performance bottlenecks. Each query adds network latency, disk I/O, and processing overhead, resulting in a poor user experience and wasted computational resources.

## The Solution: An Intelligent Caching Layer 

This project implements a **memoization service** that sits between the application and a simulated slow database. By caching the results of recent queries in memory, the service can return subsequent identical requests almost instantly, bypassing the slow database entirely.

When a query is received:
1.  The service first checks the **LRU Cache**.
2.  **Cache Hit:** If the result is present, it's returned immediately (e.g., <1ms).
3.  **Cache Miss:** If the result is not present, the service queries the slow database (e.g., >2000ms), stores the result in the cache for next time, and then returns it.



---

## Key Components

| Component           | Description                                                                                                                                | Technology Stack                             |
| ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------   | -------------------------------------------- |
| **LRU Cache**       | A custom, generic cache built from scratch. Uses a `HashMap` for O(1) lookups and a `Doubly Linked List` for O(1) usage tracking/eviction. | Java, HashMap, Doubly Linked List            |
| **Slow Database**   | A mock database that reads from `User_Data.csv` and simulates a 2-second delay to clearly demonstrate the cache's impact.                  | Java, File I/O                               |
| **Database Service**| The public-facing service that orchestrates the caching logic, connecting the application to the cache and the database.                   | Java                                         |
| **Main Application**| A driver class that runs a sequence of queries to demonstrate cache hits, misses, and the LRU eviction policy in action.                   | Java                                         |

---

## Getting Started

### Prerequisites
* Java Development Kit (JDK) 8 or higher.
* Git for cloning the repository.

### Installation & Execution

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/YourUsername/Java-Caching-Service.git](https://github.com/YourUsername/Java-Caching-Service.git)
    cd Java-Caching-Service
    ```

2.  **Ensure `User_Data.csv` is present** in the root directory.

3.  **Compile all Java files:**
    ```sh
    javac *.java
    ```

4.  **Run the application:**
    ```sh
    java Main
    ```
    Observe the console output to see the real-time difference between a cache HIT and a cache MISS.

---

## Future Improvements

This implementation serves as a strong proof-of-concept. The following features could be added to make it production-ready:

-   [ ] **Thread Safety:** Implement synchronization to allow the cache to be safely used in a multithreaded environment.
-   [ ] **Time-To-Live (TTL):** Add a TTL for cache entries to automatically evict stale data after a certain period.
-   [ ] **Memory Constraints:** Extend the eviction policy to consider the memory size of cached items, not just the entry count.
-   [ ] **Unit Testing:** Develop a comprehensive suite of JUnit tests to validate the cache's logic and behavior.
