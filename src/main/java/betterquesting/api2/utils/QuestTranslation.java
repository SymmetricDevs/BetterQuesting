package betterquesting.api2.utils;

import betterquesting.api.properties.IPropertyContainer;
import betterquesting.api.properties.IPropertyType;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.IQuestLine;
import net.minecraft.client.resources.I18n;

import java.util.Map;
import java.util.UUID;

public class QuestTranslation {
    private static final String QUEST_NAME_KEY = "betterquesting.quest.%s.name";
    private static final String QUEST_DESCRIPTION_KEY = "betterquesting.quest.%s.desc";
    private static final String QUEST_LINE_NAME_KEY = "betterquesting.questline.%s.name";
    private static final String QUEST_LINE_DESCRIPTION_KEY = "betterquesting.questline.%s.desc";

    /**
     * We'll look up translation keys directly from the map, to avoid needing to perform string
     * comparison to check if a key is missing.
     */
    private static final Map<String, String> translations;
    static {
        translations = I18n.i18nLocale.properties;
    }

    public static String translate(String text, Object... args) {
        if (!I18n.hasKey(text)) {
            return text;
        }

        return I18n.format(text, args);
    }

    public static String translateTrimmed(String text, Object... args) {
        return translate(text, args).replaceAll("\r", "");
    }
    public static String buildQuestNameKey(UUID questId) {
        return String.format(QUEST_NAME_KEY, questId);
    }

    public static String translateQuestName(UUID questId, IQuest quest) {
        return translateProperty(buildQuestNameKey(questId), quest, NativeProps.NAME);
    }

    public static String translateQuestName(Map.Entry<UUID, IQuest> entry) {
        return translateQuestName(entry.getKey(), entry.getValue());
    }

    public static String buildQuestDescriptionKey(UUID questId) {
        return String.format(QUEST_DESCRIPTION_KEY, questId);
    }

    public static String translateQuestDescription(UUID questId, IQuest quest) {
        return translateProperty(buildQuestDescriptionKey(questId), quest, NativeProps.DESC);
    }

    public static String translateQuestDescription(Map.Entry<UUID, IQuest> entry) {
        return translateQuestDescription(entry.getKey(), entry.getValue());
    }

    public static String buildQuestLineNameKey(UUID questLineId) {
        return String.format(QUEST_LINE_NAME_KEY, questLineId);
    }

    public static String translateQuestLineName(UUID questLineId, IQuestLine questLine) {
        return translateProperty(buildQuestLineNameKey(questLineId), questLine, NativeProps.NAME);
    }

    public static String translateQuestLineName(Map.Entry<UUID, IQuestLine> entry) {
        return translateQuestLineName(entry.getKey(), entry.getValue());
    }

    public static String buildQuestLineDescriptionKey(UUID questLineId) {
        return String.format(QUEST_LINE_DESCRIPTION_KEY, questLineId);
    }

    public static String translateQuestLineDescription(UUID questLineId, IQuestLine questLine) {
        return translateProperty(
                buildQuestLineDescriptionKey(questLineId), questLine, NativeProps.DESC);
    }

    public static String translateQuestLineDescription(Map.Entry<UUID, IQuestLine> entry) {
        return translateQuestLineDescription(entry.getKey(), entry.getValue());
    }

    /**
     * Returns the translation, if one exists for {@code key}.
     * If no translation exists, then {@code property} is fetched from {@code container}.
     */
    private static String translateProperty(
            String key, IPropertyContainer container, IPropertyType<String> property) {
        String translation = translations.get(key);
        if (translation != null) {
            return String.format(translation);
        }

        return container.getProperty(property);
    }
}
