package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @PostConstruct()
    public void postConstruct() {
        System.out.println("Creating NoteService bean");
    }


    public Integer addNote(Note note) {
        return this.noteMapper.insert(note);
    }

    public Integer deleteNote(Integer noteId) {
        return this.noteMapper.deleteItem(noteId);
    }

    public List<Note> getAllNotes(Integer userId) {
        return this.noteMapper.getAllNotes(userId);
    }

    public Integer editNote(Note note) {
        return this.noteMapper.updateNote(note);
    }
}
