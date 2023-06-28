package no.nav.portal.statusholder.controller;


import no.nav.portal.statusholder.Dto.StatusDto;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class Controller {
    StatusDto statusDto = new StatusDto();

    Map<UUID, StatusDto> allServices = new HashMap();

    @GetMapping("/status")
    @ResponseBody
    public List<StatusDto> getAllStatuse() {
        return new ArrayList<>(allServices.values());
    }


    @GetMapping("/status/{uuid}")
    @ResponseBody
    public StatusDto getStatus(@PathVariable UUID uuid) {
        return allServices.get(uuid);
    }



    @RequestMapping(value = "/status", method = RequestMethod.POST, consumes = "application/json")
    public void postStatus(@RequestBody StatusDto statusDto) {
        allServices.put(statusDto.getServiceId(),statusDto);

    }

    @RequestMapping(value = "/statuses", method = RequestMethod.POST, consumes = "application/json")
    public void postStatuses(@RequestBody List<StatusDto> statusDto) {
        statusDto.forEach(s->
                allServices.put(s.getServiceId(),s)
        );

    }

    @GetMapping("/checkSatusEndpoint")
    public Boolean checkUrl(@RequestParam("url") String url) {
        return Util.checkStatusEndpoint(url);
    }

    @GetMapping("/isAlive")
    @ResponseBody
    public String isAlive() {
        return "Status: Ok";
    }


}