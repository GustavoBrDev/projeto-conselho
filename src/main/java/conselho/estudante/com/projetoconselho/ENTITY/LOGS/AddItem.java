<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/LOGS/AddItem.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS;
========
package conselho.estudante.com.projetoconselho.ENTITY.LOGS;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/LOGS/AddItem.java

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
