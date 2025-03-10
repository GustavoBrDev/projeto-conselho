package MODELS.ENTITY.LOGS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Abstração para itens editáveis
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see AddItem, ChangeItem
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class EditableItem {

    private String fieldName;
}
