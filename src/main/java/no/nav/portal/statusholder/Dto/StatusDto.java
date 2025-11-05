package no.nav.portal.statusholder.Dto;

import java.util.UUID;

public class StatusDto {

    ServiceStatus status;

    UUID serviceId;

    String description;


    public StatusDto() {
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public StatusDto setStatus(ServiceStatus status) {
        this.status = status;
        return this;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public StatusDto setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public StatusDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
