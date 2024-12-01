package lab03.handlers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class NavigationController {
    public String toMainPage() {
        return "main.xhtml";
    }

    public String toIndexPage() {
        return "index.xhtml";
    }
}
