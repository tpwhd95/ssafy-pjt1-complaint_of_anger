<template>
  <div class="mt-3">
    <h1 class="text-center">공지사항 글쓰기</h1>
    <div class="container" align="center">
      <div class="col-lg-6" align="center">
        <form id="writeform" method="post" action>
          <input type="hidden" name="act" id="act" value="write" />
          <div class="form-group" align="left">
            <label for="title">제목:</label>
            <input
              type="text"
              class="form-control"
              id="title"
              ref="title"
              placeholder="제목을 입력하세요"
              v-model="notice_title"
            />
          </div>
          <div class="form-group" align="left">
            <label for="content">내용:</label>
            <textarea
              class="form-control"
              rows="15"
              id="content"
              ref="content"
              placeholder="내용을 입력하세요"
              v-model="notice_content"
            ></textarea>
          </div>
          <div class="d-flex justify-content-around mt-3" style="width: 100%;">
            <button type="button" class="btn btn-primary" @click="checkHandler">글작성</button>
            <button type="reset" class="btn btn-warning">초기화</button>
            <router-link to="/noticeboard" class="btn btn-info">취소</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "notice-Form",
  props: {
    type: { type: String },
  },
  data: function () {
    return {
      notice_no: "",
      notice_id: "",
      notice_title: "",
      notice_content: "",
      notice_regtime: "",
    };
  },
  methods: {
    checkHandler() {
      let err = true;
      let msg = "";
      !this.notice_title &&
        ((msg = "제목을 입력해주세요"),
        (err = false),
        this.$refs.notice_title.focus());
      err &&
        !this.notice_content &&
        ((msg = "내용을 입력해주세요"),
        (err = false),
        this.$refs.notice_content.focus());

      if (!err) alert(msg);
      else this.createHandler();
    },
    createHandler() {
      http
        .post(
          "/notice",
          {
            notice_title: this.notice_title,
            notice_id: this.$session.get("email"),
            notice_content: this.notice_content,
          },
          {
            headers: {
              token: this.$session.get("token"),
            },
          }
        )
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push("/noticeboard");
        })
        .catch(() => {
          alert("등록 처리시 에러가 발생했습니다.");
        });
    },
  },
};
</script>

<style>
button {
  margin-right: 20px;
  margin-left: 20px;
}
</style>
