package beans;

import lombok.Getter;
import javax.faces.context.FacesContext;
import java.io.Serial;
import java.io.Serializable;


@Getter
public class TimezoneHandler implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String timezone;

    public void setTimezone() {
        timezone = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("timezone");
    }
}
