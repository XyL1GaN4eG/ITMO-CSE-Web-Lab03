package lab03.coordinatesBeans;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * Managed bean for dealing with the X value in a JSF application.
 * This bean includes methods for getting and setting the R value, validating it, and initializing it with a default value if necessary.
 */
@Data
@NoArgsConstructor
@Getter
public class XCoordinateBean implements Serializable {
    private Double x = 0.0;

    public void validateRBeanValue(FacesContext facesContext, UIComponent uiComponent, Object o){
        if (o == null){
            FacesMessage message = new FacesMessage("X value should be in (-4, 4) interval");
            throw new ValidatorException(message);
        }
    }

    @PostConstruct
    public void init() {
        if (x == null || x == 0.0) {
            x = 0.0; // Default value
        }
    }
}