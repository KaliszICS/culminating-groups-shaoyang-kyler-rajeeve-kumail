package data.save;

import entities.characters.*;
import entities.items.*;
import entities.equipment.*;
import systems.inventory.*;
import java.io.Serializable;
import java.util.*;

/**
 * 游戏数据类 - 包含所有需要保存的游戏状态信息
 * 实现Serializable接口以支持Java序列化
 */
public class GameData implements Serializable {
    private static final long serialVersionUID = 1L;

    // 玩家信息
    private List<PlayableCharacter> playerCharacters;
    private PlayableCharacter currentMainCharacter;

    // 背包数据
    private List<Item> inventoryItems;
    private int inventoryMaxWeight;
    private Map<String, List<Item>> equippedItemsByCharacter;

    // 游戏进度
    private int currentStoryProgress;
    private List<String> completedQuests;
    private List<String> unlockedLocations;

    // 抽卡系统数据
    private int pityCounter5Star;
    private int pityCounter4Star;
    private boolean guaranteed5Star;
    private List<String> pullHistory;

    // 游戏设置
    private GameSettings gameSettings;

    // 系统数据
    private Date saveDate;
    private long playTimeInSeconds;
    private String gameVersion;

    /**
     * 默认构造函数
     */
    public GameData() {
        this.playerCharacters = new ArrayList<>();
        this.inventoryItems = new ArrayList<>();
        this.equippedItemsByCharacter = new HashMap<>();
        this.completedQuests = new ArrayList<>();
        this.unlockedLocations = new ArrayList<>();
        this.pullHistory = new ArrayList<>();
        this.gameSettings = new GameSettings();
        this.saveDate = new Date();
        this.gameVersion = "1.0.0";
        this.currentStoryProgress = 0;
        this.playTimeInSeconds = 0;
        this.pityCounter5Star = 0;
        this.pityCounter4Star = 0;
        this.guaranteed5Star = false;
    }

    /**
     * 完整构造函数
     */
    public GameData(List<PlayableCharacter> playerCharacters,
                    List<Item> inventoryItems,
                    int currentStoryProgress,
                    long playTimeInSeconds) {
        this();
        this.playerCharacters = playerCharacters;
        this.inventoryItems = inventoryItems;
        this.currentStoryProgress = currentStoryProgress;
        this.playTimeInSeconds = playTimeInSeconds;
    }

    // Getter 和 Setter 方法

    public List<PlayableCharacter> getPlayerCharacters() {
        return playerCharacters;
    }

    public void setPlayerCharacters(List<PlayableCharacter> playerCharacters) {
        this.playerCharacters = playerCharacters;
    }

    public PlayableCharacter getCurrentMainCharacter() {
        return currentMainCharacter;
    }

    public void setCurrentMainCharacter(PlayableCharacter currentMainCharacter) {
        this.currentMainCharacter = currentMainCharacter;
    }

    public List<Item> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<Item> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public int getInventoryMaxWeight() {
        return inventoryMaxWeight;
    }

    public void setInventoryMaxWeight(int inventoryMaxWeight) {
        this.inventoryMaxWeight = inventoryMaxWeight;
    }

    public Map<String, List<Item>> getEquippedItemsByCharacter() {
        return equippedItemsByCharacter;
    }

    public void setEquippedItemsByCharacter(Map<String, List<Item>> equippedItemsByCharacter) {
        this.equippedItemsByCharacter = equippedItemsByCharacter;
    }

    public int getCurrentStoryProgress() {
        return currentStoryProgress;
    }

    public void setCurrentStoryProgress(int currentStoryProgress) {
        this.currentStoryProgress = currentStoryProgress;
    }

    public List<String> getCompletedQuests() {
        return completedQuests;
    }

    public void setCompletedQuests(List<String> completedQuests) {
        this.completedQuests = completedQuests;
    }

    public List<String> getUnlockedLocations() {
        return unlockedLocations;
    }

    public void setUnlockedLocations(List<String> unlockedLocations) {
        this.unlockedLocations = unlockedLocations;
    }

    public int getPityCounter5Star() {
        return pityCounter5Star;
    }

    public void setPityCounter5Star(int pityCounter5Star) {
        this.pityCounter5Star = pityCounter5Star;
    }

    public int getPityCounter4Star() {
        return pityCounter4Star;
    }

    public void setPityCounter4Star(int pityCounter4Star) {
        this.pityCounter4Star = pityCounter4Star;
    }

    public boolean isGuaranteed5Star() {
        return guaranteed5Star;
    }

    public void setGuaranteed5Star(boolean guaranteed5Star) {
        this.guaranteed5Star = guaranteed5Star;
    }

    public List<String> getPullHistory() {
        return pullHistory;
    }

    public void setPullHistory(List<String> pullHistory) {
        this.pullHistory = pullHistory;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public long getPlayTimeInSeconds() {
        return playTimeInSeconds;
    }

    public void setPlayTimeInSeconds(long playTimeInSeconds) {
        this.playTimeInSeconds = playTimeInSeconds;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    /**
     * 添加已完成的支线任务
     * @param questId 支线任务ID
     */
    public void addCompletedQuest(String questId) {
        if (!completedQuests.contains(questId)) {
            completedQuests.add(questId);
        }
    }

    /**
     * 检查支线任务是否已完成
     * @param questId 支线任务ID
     * @return 是否已完成
     */
    public boolean isQuestCompleted(String questId) {
        return completedQuests.contains(questId);
    }

    /**
     * 解锁新地点
     * @param locationId 地点ID
     */
    public void unlockLocation(String locationId) {
        if (!unlockedLocations.contains(locationId)) {
            unlockedLocations.add(locationId);
        }
    }

    /**
     * 检查地点是否已解锁
     * @param locationId 地点ID
     * @return 是否已解锁
     */
    public boolean isLocationUnlocked(String locationId) {
        return unlockedLocations.contains(locationId);
    }

    /**
     * 添加抽卡记录
     * @param pullRecord 抽卡记录
     */
    public void addPullRecord(String pullRecord) {
        pullHistory.add(pullRecord);
        // 限制历史记录长度，防止过大
        if (pullHistory.size() > 1000) {
            pullHistory = pullHistory.subList(pullHistory.size() - 500, pullHistory.size());
        }
    }

    /**
     * 显示游戏数据摘要
     */
    public void displaySummary() {
        System.out.println("=== 游戏数据摘要 ===");
        System.out.println("存档时间: " + saveDate);
        System.out.println("游戏版本: " + gameVersion);
        System.out.println("主线进度: 第 " + currentStoryProgress + " 章");
        System.out.println("游戏时长: " + formatPlayTime(playTimeInSeconds));
        System.out.println("角色数量: " + playerCharacters.size());
        System.out.println("背包物品: " + inventoryItems.size() + " 个");
        System.out.println("已完成支线: " + completedQuests.size() + " 个");
        System.out.println("已解锁地点: " + unlockedLocations.size() + " 个");
        System.out.println("抽卡历史记录: " + pullHistory.size() + " 条");
        System.out.println("====================");
    }

    /**
     * 格式化游戏时间
     * @param seconds 总秒数
     * @return 格式化后的时间字符串
     */
    private String formatPlayTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}

/**
 * 游戏设置类
 */
class GameSettings implements Serializable {
    private static final long serialVersionUID = 2L;

    private int volumeMaster;
    private int volumeMusic;
    private int volumeSfx;
    private String language;
    private String displayMode;
    private int graphicsQuality;
    private boolean subtitlesEnabled;
    private String controlScheme;

    public GameSettings() {
        this.volumeMaster = 80;
        this.volumeMusic = 70;
        this.volumeSfx = 75;
        this.language = "English";
        this.displayMode = "CommandLine";
        this.graphicsQuality = 2; // 0=低, 1=中, 2=高
        this.subtitlesEnabled = true;
        this.controlScheme = "default";
    }

    // Getter 和 Setter 方法
    public int getVolumeMaster() {
        return volumeMaster;
    }

    public void setVolumeMaster(int volumeMaster) {
        this.volumeMaster = Math.max(0, Math.min(100, volumeMaster));
    }

    public int getVolumeMusic() {
        return volumeMusic;
    }

    public void setVolumeMusic(int volumeMusic) {
        this.volumeMusic = Math.max(0, Math.min(100, volumeMusic));
    }

    public int getVolumeSfx() {
        return volumeSfx;
    }

    public void setVolumeSfx(int volumeSfx) {
        this.volumeSfx = Math.max(0, Math.min(100, volumeSfx));
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    public int getGraphicsQuality() {
        return graphicsQuality;
    }

    public void setGraphicsQuality(int graphicsQuality) {
        this.graphicsQuality = Math.max(0, Math.min(2, graphicsQuality));
    }

    public boolean isSubtitlesEnabled() {
        return subtitlesEnabled;
    }

    public void setSubtitlesEnabled(boolean subtitlesEnabled) {
        this.subtitlesEnabled = subtitlesEnabled;
    }

    public String getControlScheme() {
        return controlScheme;
    }

    public void setControlScheme(String controlScheme) {
        this.controlScheme = controlScheme;
    }

    @Override
    public String toString() {
        return String.format("音量: 主音量%d%%, 音乐%d%%, 音效%d%%, 语言:%s, 显示模式:%s, 画质:%d, 字幕:%s",
                volumeMaster, volumeMusic, volumeSfx, language,
                displayMode, graphicsQuality, subtitlesEnabled ? "开启" : "关闭");
    }
}