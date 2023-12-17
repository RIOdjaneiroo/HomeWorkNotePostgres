package com.example.HomeWorkNotePostgres.controller;

import com.example.HomeWorkNotePostgres.controller.request.CreateNoteRequest;
import com.example.HomeWorkNotePostgres.dto.NoteDto;
import com.example.HomeWorkNotePostgres.exception.NoteNotFoundException;
import com.example.HomeWorkNotePostgres.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private final NoteService noteService;
    @GetMapping("/list")
    public String getNoteList(Model model) {
        List<NoteDto> notes = noteService.listAll();
        model.addAttribute("notes", notes);
        return "note/list";
    }
    @GetMapping("/create")
    public String showCreateNoteForm(Model model) {
        model.addAttribute("createNoteRequest", new CreateNoteRequest());
        return "note/create";
    }

    @PostMapping("/create")
    public String createNote(@ModelAttribute CreateNoteRequest createNoteRequest) {
        // встановлюємо user_id змінимо процедуру в майдутньому
        Long userId = 1L;
        NoteDto newNote = new NoteDto();
        newNote.setTitle(createNoteRequest.getTitle());
        newNote.setContent(createNoteRequest.getContent());
        newNote.setUserId(userId);
        noteService.add(newNote);

        // редірект на сторінку списку нотаток або на сторінку редагування новоствореної нотатки
        return "redirect:/note/list"; // або "redirect:/note/edit?id=" + newNote.getId();
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam UUID id) {
        try {
            noteService.deleteById(id);
        } catch (NoteNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String showEditNoteForm(@RequestParam UUID id, Model model) {
        try {
            NoteDto noteToEdit = noteService.getById(id);
            model.addAttribute("note", noteToEdit);
            return "note/edit";
        } catch (NoteNotFoundException e) {
            e.printStackTrace();
            return "redirect:/note/list";
        }
    }

    @PostMapping("/edit")
    public String editNote(@RequestParam UUID id,
                           @RequestParam String title,
                           @RequestParam String content,
                           @RequestParam Long userId,
                           Model model) throws NoteNotFoundException {
        // отримуємо існуючу нотатку
        NoteDto existingNote = noteService.getById(id);
        // оновлюємо userId
        existingNote.setUserId(userId);
        existingNote.setTitle(title);
        existingNote.setContent(content);

        // змінюємо дату модифікації
        existingNote.setLastUpdatedDate(LocalDate.now());

        // Зберегти оновлену нотатку
        noteService.update(existingNote);

        return "redirect:/note/list";
    }
}

