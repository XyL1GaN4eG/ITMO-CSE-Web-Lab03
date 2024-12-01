package lab03.coordinatesBeans;

import lab03.db.ResultDAO;
import lab03.db.ResultEntityDAO;
import lab03.handlers.AreaCheckerClass;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Named("main_bean")
@Data
@NoArgsConstructor
public class MainBean implements Serializable {
    private AreaCheckerClass areaChecker = AreaCheckerClass.getInstance();
    private ResultDAO resultDAO = new ResultDAO();
    private final DateTimeFormatter yyyymmddhhmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Inject
    private XCoordinateBean xBean;

    @Inject
    private YCoordinateBean yBean;

    @Inject
    private RCoordinateBean rBean;

    private List<ResultEntityDAO> results = new ArrayList<>();

    public void submit() {
        long startTime = System.nanoTime();
        var isIn = areaChecker.validate(xBean,
                yBean,
                rBean);
        var res = new ResultEntityDAO(
                xBean.getX(),
                yBean.getY(),
                rBean.getR(),
                isIn,
                System.nanoTime() - startTime,
                LocalDateTime.now());
        resultDAO.addNewResult(res);

    }

    public void reset() {
        xBean.init();
        yBean.init();
        rBean.init();
    }
}
