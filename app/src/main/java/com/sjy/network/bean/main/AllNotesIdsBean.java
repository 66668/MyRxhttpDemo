package com.sjy.network.bean.main;

import com.thinkernote.ThinkerNote.bean.CommonBean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 格式{code msg data {}}
 */

public class AllNotesIdsBean extends CommonBean implements Serializable {
    List<NoteIdItemBean> note_ids;

    public AllNotesIdsBean(int code, String msg) {
        super(code, msg);
    }

    public class NoteIdItemBean implements Serializable {

        long id;
        int update_at;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(int update_at) {
            this.update_at = update_at;
        }
    }

    public List<NoteIdItemBean> getNote_ids() {
        return note_ids;
    }

    public void setNote_ids(List<NoteIdItemBean> note_ids) {
        this.note_ids = note_ids;
    }
}
