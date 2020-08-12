!<template>
  <div class="container mt-3" align="center">
    <div class="col-lg-6" align="center">
      <h1>공지사항</h1>
      <div class="form-group" align="center">
        <div class="form-group">
          <h5>제목 :</h5>
          <input
            type="text"
            class="form-control"
            id="title"
            ref="title"
            placeholder="제목을 입력하세요"
            v-model="notice_title"
          />
        </div>
      </div>
      <div class="form-group2" align="center">
        <div class="form-group">
          <h5>내용 :</h5>
          <textarea
            class="form-control"
            rows="15"
            id="content"
            name="content"
            v-model="notice_content"
          ></textarea>
        </div>
      </div>
    </div>
    <div class="d-flex justify-content-around" style="width: 40%;">
      <button type="button" class="btn btn-primary" @click="checkHandlerModify">수정완료</button>
      <router-link to="/noticeboard" class="btn btn-info">목록으로</router-link>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import http from "@/util/http-common";
export default {
  name: "read",
  data: function () {
    return {
      notice_no: this.$session.get("notice_no"),
      notice_id: this.$session.get("notice_id"),
      notice_title: this.$session.get("notice_title"),
      notice_content: this.$session.get("notice_content"),
    };
  },
  computed: {
    ...mapGetters(["item"]),
  },
  methods: {
    created() {
      this.$store.dispatch(
        "getNotice",
        `/notice/${this.$session.get("noticeDetailKey")}`
      );
    },
    checkHandlerModify() {
      let err = true;
      let msg = "";
      !this.notice_title &&
        ((msg = "제목을 입력해주세요"),
        (err = false),
        this.$refs.notice_title.focus());
      err &&
        !this.notice_content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.notice_content.focus());

      if (!err) alert(msg);
      else this.modifyHandler();
    },
    modifyHandler() {
      http
        .put(
          `/notice/${this.notice_no}`,
          {
            notice_no: this.notice_no,
            notice_id: this.notice_id,
            notice_title: this.notice_title,
            notice_content: this.notice_content,
          },
          {
            headers: {
              token: this.$session.get("token"),
            },
          }
        )
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          this.$router.push("/noticeboard");
          alert(msg);
          this.$router.go();
        })
        .catch(() => {
          alert("수정 처리시 에러가 발생했습니다.");
        });
    },
  },
};
</script>

<style>
h1 {
  margin-bottom: 50px;
}
h4 {
  text-align: left;
}
button {
  margin-right: 60px;
}
h5 {
  text-align: left;
}
</style>
