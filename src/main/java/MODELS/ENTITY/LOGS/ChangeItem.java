package MODELS.ENTITY.LOGS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
}
