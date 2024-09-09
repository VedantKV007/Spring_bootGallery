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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class webController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public String home() {
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

        if ("admin".equals(username) && "password".equals(password)) {
            return "redirect:/admin";
        } else {

            model.addAttribute("error", "Invalid username or password");
            return "adminlogin";
        }
    }


    @GetMapping("/admin")
    public String AdminPage(Model model) {
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "adminpage";
    }

    /*@PostMapping("/admin/add-client")
    public String addClient(@RequestParam String clientNumber, @RequestParam String name,
                            @RequestParam("images") List<MultipartFile> images, Model model) {
        try {
            clientService.addClient(clientNumber, name, images);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin";
    }*/


    @PostMapping("/admin/add-client")
    public String Add_client(@RequestParam String clientNumber,
                             @RequestParam String name,
                            @RequestParam("images") List<MultipartFile> images,
                            Model model) {
        try {
            clientService.Add_Client(clientNumber, name, images);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }


    @PostMapping("/admin/delete-client")
    public String Delete_Client(@RequestParam String clientNumber) {
        clientService.deleteClientByNumber(clientNumber);
        return "redirect:/admin";
    }

    @GetMapping("/client")
    public String showClientPage()
    {
        return "client";
    }

    @PostMapping("client/get-client")
    public String getClientInfo(@RequestParam String clientNumber, RedirectAttributes redirectAttributes) {
        Optional<Client> clientOpt = clientService.GetClientByNumber(clientNumber);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();

            // Convert client images to Base64
            List<String> base64Images = new ArrayList<>();
            for (byte[] imageData : client.getImages()) {
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                base64Images.add(base64Image);
            }


            redirectAttributes.addFlashAttribute("client", client);
            redirectAttributes.addFlashAttribute("images", base64Images);


            return "redirect:/view-client";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Client number not found.");
            return "redirect:/view-client";
        }
    }


    @GetMapping("/view-client")
    public String View_ClientData(Model model) {

        return "clinetdata";
    }
}












