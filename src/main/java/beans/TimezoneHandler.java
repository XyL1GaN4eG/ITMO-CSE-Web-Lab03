package beans;

import lombok.Getter;

import javax.faces.context.FacesContext;
import java.io.Serial;
import java.io.Serializable;


public class TimezoneHandler implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Getter
    private String timezone;

    public void setTimezone() {
        timezone = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("timezone");
    }
}
