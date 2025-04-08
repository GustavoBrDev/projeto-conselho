package conselho.estudante.com.projetoconselho.models.entity.logs;

import lombok.*;

/**
 * Classe utilizada para log de alteração de item
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see EditableItem
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeItem extends EditableItem {

    private Object originalValue;
    private Object newValue;

    public ChangeItem(String name, Object oldValue, Object newValue) {
        super(name);
        this.originalValue = oldValue;
        this.newValue = newValue;
    }
}
