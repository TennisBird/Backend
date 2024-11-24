package org.tennis_bird.api.data;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfoRequest {
    @JsonAlias("code")
    private String code;
    @JsonAlias("title")
    private String title;
    @JsonAlias("description")
    private String description;
    @JsonAlias("status")
    private String status = "open";
    @JsonAlias("priority")
    private String priority = "medium";
    @JsonAlias("estimate")
    private Integer estimate;
}
