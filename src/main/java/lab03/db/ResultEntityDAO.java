package lab03.db;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entity class representing a row in the "result" table.
 */
@Data
@AllArgsConstructor
public class ResultEntityDAO {
    private double x;
    private double y;
    private double r;
    private boolean isIn;
    private long executionTime;
    private LocalDateTime serverTime;
}