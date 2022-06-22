package com.kashiuno.bpoevrika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
@Controller
public class BpoEvrikaApplication {

    private final Runtime runtime;
    public static final String GET_USERS_COMMAND = "wmic UserAccount get Name";

    public BpoEvrikaApplication(Runtime runtime) {
        this.runtime = runtime;
    }

    public static void main(String[] args) {
        SpringApplication.run(BpoEvrikaApplication.class, args);
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("exist")
    public String exist(Model model, @RequestParam String user) throws IOException {
        Process p = runtime.exec(GET_USERS_COMMAND);
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        boolean exist = false;
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            if (line.isBlank() && "Name".equals(line.trim())) continue;
            if (line.trim().equals(user.trim())) exist = true;
        }
        model.addAttribute("user", user);
        model.addAttribute("exist", exist);
        return "exist";
    }

    @EventListener({ApplicationReadyEvent.class})
    public void applicationReadyEvent() {
        browse("http://localhost:8080");
    }

    public void browse(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
