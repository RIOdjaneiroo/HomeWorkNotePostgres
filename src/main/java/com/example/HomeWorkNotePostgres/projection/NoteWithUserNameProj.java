package com.example.HomeWorkNotePostgres.projection;

import java.util.UUID;

public interface NoteWithUserNameProj {

    UUID getNoteId();
    String getTitle();
    String getContent();
    String getUsername();
}
