package Devvep.project_task.Controllers;

import Devvep.project_task.Entity.Client;
import Devvep.project_task.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class webController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/adminlogin")
    public String page1() {
        return "adminlogin";
    }




    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        // Hard-coded username and password
        if ("admin".equals(username) && "password".equals(password)) {
            return "redirect:/loggedin";  // Redirect to success page if login is successful
        } else {

            model.addAttribute("error", "Invalid username or password");
            return "adminlogin";
        }
    }
    @GetMapping("/loggedin")
    public String loggedInPage() {
        return "adminpage";
    }


    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "adminpage";
    }

    @PostMapping("/admin/add-client")
    public String addClient(@RequestParam String clientNumber, @RequestParam String name,
                            @RequestParam("images") List<MultipartFile> images, Model model) {
        try {
            clientService.addClient(clientNumber, name, images);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete-client")
    public String deleteClient(@RequestParam String clientNumber) {
        clientService.deleteClientByNumber(clientNumber);
        return "redirect:/admin";
    }

    @GetMapping("/client")
    public String showClientPage() {
        return "client";
    }
    @PostMapping("/client/get-client")
    public String getClientInfo(@RequestParam String clientNumber, Model model) {
        Optional<Client> clientOpt = clientService.getClientByNumber(clientNumber);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();

            // Convert images to Base64
            List<String> base64Images = new ArrayList<>();
            for (byte[] imageData : client.getImages()) {
                String base64Image = Base64.getEncoder().encodeToString(imageData);  // Using java.util.Base64
                base64Images.add(base64Image);
            }

            model.addAttribute("client", client);
            model.addAttribute("images", base64Images);  // Add Base64-encoded images to the model

        } else {
            model.addAttribute("errorMessage", "Client number not found.");
        }
        return "client";
    }



}










