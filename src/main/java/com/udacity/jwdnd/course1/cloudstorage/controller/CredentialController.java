package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/addOrEditCredential")
    public String addOrEditNote(Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        SecureRandom random;

        if (credential.getCredentialId() == null) {
            Integer userId = this.userService.getUser(authentication.getName()).getUserId();
            credential.setUserId(userId);

            random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);

            String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), encodedKey);
            credential.setKey(encodedKey);
            credential.setPassword(encryptedPassword);
            this.credentialService.addCredential(credential);
        } else {

            random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);

            String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), encodedKey);
            credential.setKey(encodedKey);
            credential.setPassword(encryptedPassword);

            this.credentialService.editCredential(credential);
        }
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        return "redirect:/home";
    }

    @GetMapping("/credentials/delete/{id}")
    public String deleteCredential(@PathVariable("id") Integer credentialId, RedirectAttributes redirectAttributes) {
        this.credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("activeTab", "credentials");
        return "redirect:/home";
    }
}
