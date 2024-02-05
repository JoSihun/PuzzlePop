package com.ssafy.puzzlepop.engine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class User {
    private String id;
//    private String sessionId;

    @Override
    public boolean equals(Object obj) {
        User u = (User)obj;
        if (this.id.equals(u.getId())) {
            return true;
        }

        return false;
    }
}
