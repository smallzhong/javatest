import java.util.regex.Pattern;

public class yuchu_checkPathAvailable {
    public enum OSType {
        LINUX,
        WINDOWS,
        OTHERS;
    }

    private static final Pattern linux_path_pattern = Pattern.compile("(/([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255}/)*([a-zA-Z0-9][a-zA-Z0-9_\\-]{0,255})|/)");
    private static final Pattern windows_path_pattern = Pattern.compile("(^[A-Z]:((\\\\|/)([a-zA-Z0-9\\-_]){1,255}){1,255}|([A-Z]:(\\\\|/)))");

    /**
     * check path is valid in windows and linux
     *
     * @param path     path to be validate
     * @param platform valid value: linux,windows
     * @return whether the path is valid
     **/
    public static boolean checkPathValid(String path, String platform) {
        if (platform.toLowerCase().equals(OSType.LINUX.name().toLowerCase()))
            return checkPatternMatch(linux_path_pattern, path);
        if (platform.toLowerCase().equals(OSType.WINDOWS.name().toLowerCase()))
            return checkPatternMatch(windows_path_pattern, path);
        return false;
    }

    private static boolean checkPatternMatch(Pattern pattern, String target) {
        return pattern.matcher(target).matches();
    }

}
