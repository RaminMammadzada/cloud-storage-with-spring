package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    @PostConstruct()
    public void postConstruct() {
        System.out.println("Creating CredentialService bean");
    }


    public Integer addCredential(Credential credential) {
        return this.credentialMapper.insert(credential);
    }

    public Integer deleteCredential(Integer noteId) {
        return this.credentialMapper.deleteItem(noteId);
    }

    public List<Credential> getAllCredentials(Integer userId) {
        return this.credentialMapper.getAllCredentials(userId);
    }

    public Integer editCredential(Credential credential) {
        return this.credentialMapper.updateCredential(credential);
    }

    public Credential getCredential(Integer credentialId) {
        return this.credentialMapper.getCredentialById(credentialId);
    }
}
