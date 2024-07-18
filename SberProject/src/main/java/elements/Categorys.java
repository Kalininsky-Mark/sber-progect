package elements;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Categorys {
    Uncategorized("Choose here (optional)", true, ""),
    HighPriority("High priority", false, "border-3 border-danger"),
    MediumPriority("Medium priority", false, "border-2 border-warning"),
    LowPriority("Low priority", false, "border-info"),
    NoPriority("No priority", false, "");

    private final String Name;

    private final boolean defaultValue;

    private final String attributes;
}
