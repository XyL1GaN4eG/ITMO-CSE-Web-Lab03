package beans;

import com.google.gson.Gson;
import database.DatabaseManager;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ResultTable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
    private List<Point> results;
    private DatabaseManager dbManager;
    private final Lock lock = new ReentrantLock();

    public ResultTable() {

    }

    @PostConstruct
    public void init() throws SQLException, IOException {
        dbManager = new DatabaseManager();
        dbManager.createTable();
        results = dbManager.getDataFromTable();
    }

    public Point getLastResult() {
        if (results == null || results.isEmpty()) {
            // Возвращаем null или создаем пустой объект Point по умолчанию
            return null; // Или new Point(0, 0, 0, false, null, 0);
        }
        return results.get(results.size() - 1);
    }

    public void addPoint(Point row) {
        dbManager.addPoint(row);
        try {
            lock.lock();
            results.add(row);
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        dbManager.clear();
        try {
            lock.lock();
            results.clear();
        } finally {
            lock.unlock();
        }
    }

    public String getX() {
        return new Gson().toJson(getResults().stream().map(Point::getX).collect(Collectors.toList()));
    }

    public String getY() {
        return new Gson().toJson(getResults().stream().map(Point::getY).collect(Collectors.toList()));
    }

    public String getR() {
        return new Gson().toJson(getResults().stream().map(Point::getR).collect(Collectors.toList()));
    }

    public String getHit() {
        return new Gson().toJson(getResults().stream().map(Point::isHit).collect(Collectors.toList()));
    }
}
