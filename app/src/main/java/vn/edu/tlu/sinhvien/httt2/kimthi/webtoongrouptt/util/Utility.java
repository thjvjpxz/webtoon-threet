package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.util;

public class Utility {
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return sb.toString().trim();
    }

    public static String getFullHtml(String tag) {
        return "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<link href='https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind" +
                ".min.css' rel='stylesheet'>" +
                "<title>Chapter Content</title>" +
                "</head>" +
                "<body>" +
                tag +
                "</body>" +
                "</html>";
    }

    public static String getFullHtmlStyle(String tag, String bgColor, String textColor, String textSize) {
        return "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<link href='https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind" +
                ".min.css' rel='stylesheet'>" +
                "<title>Chapter Content</title>" +
                "<style>" +
                "body {" +
                "background-color: " + bgColor + ";" +
                "color: " + textColor + ";" +
                "font-size: " + textSize + ";" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                tag +
                "</body>" +
                "</html>";
    }
}
