package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.MesssageDTO;
import org.example.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController extends Controller {
    
    private final MailSenderService mailService;

    @Autowired
    public MailController(MailSenderService mailService) {
        this.mailService = mailService;
    }

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String send(@RequestBody MesssageDTO messsageDTO, HttpServletResponse response) {
        try {
            mailService.sendEmail(messsageDTO.getDestination(), messsageDTO.getSubject(), messsageDTO.getBody());
            return "OK";
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "ERROR";
        }
    }
}
