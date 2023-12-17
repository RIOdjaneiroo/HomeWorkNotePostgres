package com.example.HomeWorkNotePostgres.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

    private UUID id;
    private Long userId;
    private String title;
    private String content;
    private LocalDate lastUpdatedDate;
    private LocalDate createdDate;
}
