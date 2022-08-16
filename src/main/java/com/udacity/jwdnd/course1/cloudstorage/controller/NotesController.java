package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {
    private final UserService userService;
    private final NoteService noteService;

    public NotesController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/addOrEditNote")
    public String addOrEditNote(Note note, Authentication authentication) {
        if (note.getNoteId() == null) {
            Integer userId = this.userService.getUser(authentication.getName()).getUserId();
            note.setUserId(userId);
            this.noteService.addNote(note);
        } else {
            this.noteService.editNote(note);
        }

        return "redirect:/home";
    }

    @GetMapping("/notes/delete/{id}")
    public String deleteNote(@PathVariable("id") Integer noteId) {
        this.noteService.deleteNote(noteId);
        return "redirect:/home";
    }
}
