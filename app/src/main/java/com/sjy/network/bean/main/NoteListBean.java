package com.sjy.network.bean.main;

import com.thinkernote.ThinkerNote.bean.CommonBean;

import java.io.Serializable;
import java.util.List;

/**
 * 登录返回数据的bean
 */

public class NoteListBean extends CommonBean implements Serializable {
    List<NoteItemBean> notes;
    int pagenum;
    int count;

    public NoteListBean(int code, String msg) {
        super(code, msg);
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NoteItemBean> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteItemBean> notes) {
        this.notes = notes;
    }

    public class NoteItemBean implements Serializable {
        long id;
        long size;
        String update_at;
        int folder_id;
        String title;
        String summary;
        String content_digest;
        String create_at;
        int trash;
        List<TagItemBean> tags;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getContent_digest() {
            return content_digest;
        }

        public void setContent_digest(String content_digest) {
            this.content_digest = content_digest;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public int getFolder_id() {
            return folder_id;
        }

        public void setFolder_id(int folder_id) {
            this.folder_id = folder_id;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        public List<TagItemBean> getTags() {
            return tags;
        }

        public void setTags(List<TagItemBean> tags) {
            this.tags = tags;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public int getTrash() {
            return trash;
        }

        public void setTrash(int trash) {
            this.trash = trash;
        }

        public class TagItemBean implements Serializable{
            String name;

            long id;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
