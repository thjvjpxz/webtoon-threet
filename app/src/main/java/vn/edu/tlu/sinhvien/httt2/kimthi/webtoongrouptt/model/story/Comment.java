package vn.edu.tlu.sinhvien.httt2.kimthi.webtoongrouptt.model.story;

import java.io.Serializable;

public class Comment implements Serializable {
    private int id;
    private int like;
    private String content;
    private String updatedAt;
    private User user;

    public Comment() {}

    public Comment(int id, int like, String content, String updatedAt, User user) {
        this.id = id;
        this.like = like;
        this.content = content;
        this.updatedAt = updatedAt;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User implements Serializable {
        private int id;
        private String name;
        private String avatar;
        private Level level;

        public User() {}

        public User(int id, String name, String avatar, Level level) {
            this.id = id;
            this.name = name;
            this.avatar = avatar;
            this.level = level;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Level getLevel() {
            return level;
        }

        public void setLevel(Level level) {
            this.level = level;
        }
    }
    public class Level implements Serializable {
        private int id;
        private String level;
        private String image;
        private String style;

        public Level() {}

        public Level(int id, String level, String image, String style) {
            this.id = id;
            this.level = level;
            this.image = image;
            this.style = style;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }
    }
}
