package no.nav.portal.statusholder.Dto;

import java.util.Optional;

public enum ServiceStatus  {
    OK("OK"),
    DOWN("DOWN"),
    ISSUE("ISSUE"),
    UNKNOWN("UNKNOWN");

    private String dbRepresentation;

    ServiceStatus(String dbRepresentation) {
        this.dbRepresentation = dbRepresentation;
    }


}
