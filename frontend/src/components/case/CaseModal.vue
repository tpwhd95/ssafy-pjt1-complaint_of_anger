<template>
  <b-modal :id="'modal' + violationitem.violationNo" title="제보 상세보기" hide-footer>
    <!-- 동영상 -->
    <!-- 이거 자주 접속하면 요금폭탄맨~~~ -->
    <div class="text-center justify-content-center p-0">
      <video id="video1" style="width:100%" controls>
        <source :src="violationitem.videoUrl" type="video/mp4" />
      </video>
      <a :href="violationitem.videoUrl" download class="btn btn-success" style="width:100%">영상 다운로드</a>
    </div>
    <hr />
    <!-- 세부사항 -->
    <div>
      <p>
        위반항목 :
        <b-form-select
          v-model="violationitem.item"
          :options="options2"
          class="mb-0"
          value-field="item"
          text-field="name"
          disabled-field="notEnabled"
        ></b-form-select>
      </p>
      <p>
        <label>위반일자 :</label>
        <input
          type="text"
          class="form-control"
          id="date"
          readonly
          ref="date"
          v-model="violationitem.date"
        />
      </p>

      <p>
        <label>위반시간 :</label>
        <input
          type="text"
          class="form-control"
          id="time"
          readonly
          ref="time"
          v-model="violationitem.time"
        />
      </p>
      <p>
        <label>위반차량번호 :</label>
        <input type="text" class="form-control" id="item" ref="item" v-model="violationitem.carNum" />
      </p>
      <p>
        <label>위반장소 :</label>
        <input type="text" class="form-control" id="item" ref="item" v-model="violationitem.spot" />
      </p>
      <p>
        <label>위반위치 :</label>
        <input
          type="text"
          class="form-control"
          id="item"
          readonly
          ref="item"
          v-model="violationitem.address"
        />
      </p>
      <p>
        <label>신고내용 :</label>
        <input
          type="text"
          class="form-control"
          id="item"
          ref="item"
          v-model="violationitem.contents"
        />
      </p>
      <hr />
      <div class="d-flex justify-content-between mb-3">
        <b-button variant="primary" style="width: 45%;" @click="saveHandler">저장하기</b-button>
        <b-button variant="danger" style="width: 45%;" @click="deleteHandler">삭제하기</b-button>
      </div>
      <b-button
        variant="info"
        style="width: 100%;"
        href="http://onetouch.police.go.kr/"
        onclick="window.open(this.href);return false;"
        target="_blank"
      >스마트 국민제보로 신고하러 가기</b-button>
    </div>
  </b-modal>
</template>

<script>
import http from "@/util/http-common";
// import vuePlayer from "@algoz098/vue-player";

export default {
  name: "CaseModal",
  components: {
    // vuePlayer,
  },
  props: {
    violationitem: {
      type: Object,
    },
    date: {
      type: Object,
    },
    time: {
      type: Object,
    },
  },
  data() {
    return {
      selected2: "신호위반",
      options2: [
        { item: "신호위반", name: "신호위반" },
        { item: "끼어들기 금지 위반", name: "끼어들기 금지 위반" },
        { item: "통행의 금지 및 제한 위반", name: "통행의 금지 및 제한 위반" },
        {
          item: "교차로 통행방법위반(꼬리물기)",
          name: "교차로 통행방법위반(꼬리물기)",
        },
        {
          item: "제차 신호 조작 불이행(방향지시등)",
          name: "제차 신호 조작 불이행(방향지시등)",
        },
        { item: "중앙선침범", name: "중앙선침범" },
        { item: "적재물 추락방지 조치위반", name: "적재물 추락방지 조치위반" },
        { item: "적재중량, 적재용량초과", name: "적재중량, 적재용량초과" },
        { item: "지정차로 위반", name: "지정차로 위반" },
        { item: "진로변경 위반", name: "진로변경 위반" },
        { item: "교차로 통행방법 위반", name: "교차로 통행방법 위반" },
        { item: "고속도로 갓길통행 위반", name: "고속도로 갓길통행 위반" },
        { item: "기타", name: "기타" },
      ],
    };
  },
  methods: {
    saveHandler() {
      http
        .put(
          `/violation`,
          {
            violationNo: this.violationitem.violationNo,
            userNo: this.violationitem.userNo,
            item: this.violationitem.item,
            carNum: this.violationitem.carNum,
            spot: this.violationitem.spot,
            contents: this.violationitem.contents,
          },
          {
            headers: {
              token: this.$session.get("token"),
            },
          }
        )
        .then(({ data }) => {
          let msg = "저장에 실패하였습니다.";
          if (data === "success") {
            msg = "저장이 완료되었습니다.";
          }
          this.$root.$emit(
            "bv::hide::modal",
            "modal" + this.violationitem.violationNo
          );
          alert(msg);
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },
    deleteHandler() {
      http
        .delete(
          `/violation/${this.violationitem.userNo}/${this.violationitem.violationNo}`,
          {
            headers: {
              token: this.$session.get("token"),
            },
          }
        )
        .then(({ data }) => {
          let msg = "삭제에 실패하였습니다.";
          if (data === "success") {
            msg = "삭제가 완료되었습니다.";
          }
          this.$root.$emit(
            "bv::hide::modal",
            "modal" + this.violationitem.violationNo
          );
          alert(msg);
          this.$router.go();
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },
  },
};
</script>

<style>
</style>