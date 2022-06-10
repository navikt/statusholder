package no.nav.portal.statusholder.controller;


import no.nav.portal.statusholder.Dto.StatusDto;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class Controller {
    StatusDto statusDto = new StatusDto();

    Map<UUID, StatusDto> allServices = new HashMap();


    @GetMapping("/status/{uuid}")
    @ResponseBody
    public StatusDto getStatus(@PathVariable UUID uuid) {
        return allServices.get(uuid);
    }


    @RequestMapping(value = "/status", method = RequestMethod.POST, consumes = "application/json")
    public void postStatus(@RequestBody StatusDto statusDto) {
        allServices.put(statusDto.getServiceId(),statusDto);

    }



}