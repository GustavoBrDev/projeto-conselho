<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/LOGS/EditableItem.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS;
========
package conselho.estudante.com.projetoconselho.ENTITY.LOGS;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/LOGS/EditableItem.java

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
