package com.eta.houzezbackend.controller;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.dto.PropertyCreateDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.service.AgentService;
import com.eta.houzezbackend.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;
    private final PropertyService propertyService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgentGetDto signUp(@Valid @RequestBody AgentSignUpDto agentSignUpDto) {
        return agentService.signUpNewAgent(agentSignUpDto);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public AgentGetDto signIn(@RequestAttribute String username) {
        return agentService.signIn(username);
    }

    @GetMapping("/{id}")
    public AgentGetDto getAgent(@PathVariable Long id) {
        return agentService.getAgent(id);
    }


    @PatchMapping("/{id}")
    public Agent activeAgent(@RequestParam String token, @PathVariable Long id) {
        return agentService.setAgentToActive(token);
    }

    @RequestMapping(method = {RequestMethod.HEAD})
    public void getAgentByEmail(@RequestParam String email) {
        agentService.findByEmail(email);
    }

    @PostMapping("/resend-email")
    @ResponseStatus(HttpStatus.OK)
    public void resendEmail(@RequestBody Map<String, String> map) {
        agentService.resendEmail(map.get("email"));
    }

    @PostMapping("/{id}/properties/create-property")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyGetDto addProperty(@Valid @RequestBody PropertyCreateDto propertyCreateDto, @PathVariable long id) {
        return propertyService.createNewProperty(propertyCreateDto, id);
    }

    @GetMapping("/{id}/properties/get-propertylistbyagent")
    public ResponseEntity<Map<String, Object>> getPropertiesByAgent(@PathVariable long id, @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return propertyService.getPropertiesByAgent(id, page, size);
    }
}
