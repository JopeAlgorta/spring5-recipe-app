package guru.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author jpalg
 * Created at 1/24/2022
 */
@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    private Long id;
    private RecipeCommand recipe;
    private String recipeNotes;
}
