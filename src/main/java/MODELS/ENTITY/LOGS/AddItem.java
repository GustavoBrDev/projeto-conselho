package MODELS.ENTITY.LOGS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Classe utilizada quando um item foi adicionado
 * @author Gustavo Stinghen
 * @since 10/03/2025
 * @see EditableItem
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class AddItem extends EditableItem{

    private List<Object> addedItems;
}
