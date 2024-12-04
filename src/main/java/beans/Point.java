package beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.SlideEndEvent;
import util.AreaChecker;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class Point implements Serializable {
    private double x;
    private double y;
    private double r=2;
    private double hiddenR;
    private boolean hit;
    private long attemptTime;
    private double executionTime;
    private transient ResultTable table;
    private AreaChecker areaChecker = new AreaChecker();

    public Point(double x, double y, double r, boolean hit, long attemptTime, double executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.attemptTime = attemptTime;
        this.executionTime = executionTime;
    }

    public void check() {
        long start = System.nanoTime();
        long attemptTime = System.currentTimeMillis();
        boolean hit = areaChecker.check(x, y, r);
        long executionTime = System.nanoTime() - start;
        table.addPoint(new Point(x, y, r, hit, attemptTime, executionTime));
    }
}
