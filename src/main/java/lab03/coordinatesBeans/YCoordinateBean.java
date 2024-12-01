package lab03.coordinatesBeans;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Getter
@Data
@NoArgsConstructor
public class YCoordinateBean implements Serializable {
    @Setter
    private Double y;
    private boolean isYValid = false;

    // Метод валидации Y
    public void validateY() {
        if (y == null || y < -5 || y > 3) {
            FacesContext.getCurrentInstance().addMessage("y-value",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Некорректный ввод, введите число в диапазоне от -5 до 3", null));
            isYValid = false;
        } else {
            isYValid = true;
        }
    }

    @PostConstruct
    public void init() {
        if (y == null || y == 0.0) {
            y = -1.0;
        }
    }
}