<<<<<<<< HEAD:src/main/java/conselho/estudante/com/projetoconselho/MODELS/ENTITY/LOGS/ChangeItem.java
package conselho.estudante.com.projetoconselho.MODELS.ENTITY.LOGS;
========
package conselho.estudante.com.projetoconselho.ENTITY.LOGS;
>>>>>>>> 2883d1ba51d6f2ad915f17c95b5cc0a8f5f3cbf2:src/main/java/conselho/estudante/com/projetoconselho/ENTITY/LOGS/ChangeItem.java

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
