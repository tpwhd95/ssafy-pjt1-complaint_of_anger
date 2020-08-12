<template>
  <b-col cols="4">
    <div>
      <div
        v-b-modal="modalId(violationitem.violationNo)"
        variant="info"
        style="width: 100%;"
        class="shadow mb-4 bg-white rounded p-1"
      >
        <div class="mx-auto" no-body style="max-width: 20rem;">
          <div style="pointer-events: none">
            <video style="width:100%">
              <source :src="violationitem.videoUrl" type="video/mp4" />
            </video>
          </div>
          <p>violationitem.reportStatus: {{violationitem.reportStatus}}</p>
          <p>selected1: {{ selected1 }}</p>
          <p>selectedNo: {{selectedNo}}</p>
          <b-list-group flush>
            <b-list-group-item class="mt-1 p-0" @click="clickPrevent">
              <!-- <b-form-select
                v-model="selected1"
                :options="options1"
                :class="classes"
                value-field="item"
                text-field="name"
                disabled-field="notEnabled"
                style="width:100% "
              ></b-form-select>-->
              <b-form-select
                v-model="selected1"
                :options="options1"
                :class="classes"
                value-field="item"
                text-field="name"
                disabled-field="notEnabled"
                style="width:100% "
              ></b-form-select>
            </b-list-group-item>
            <div class="d-flex justify-content-between text-secondary">
              <p class="mb-0">{{ violationitem.date }} {{ violationitem.time }}</p>
            </div>
            <div>
              <p class="mb-0" style="font-size: 18px">{{ violationitem.address }}</p>
            </div>
          </b-list-group>
          <CaseModal :violationitem="violationitem" :date="date" :time="time" />
        </div>
      </div>
    </div>
  </b-col>
</template>

<script>
import { mapGetters } from "vuex";
import moment from "moment";
import http from "@/util/http-common";

import CaseModal from "@/components/case/CaseModal.vue";

export default {
  name: "Case",
  components: {
    CaseModal,
  },
  props: {
    violationitem: {
      type: Object,
    },
  },
  data() {
    return {
      selected1: "",
      options1: [
        { item: "0", name: "신고 미접수" },
        { item: "1", name: "접수 완료" },
        { item: "2", name: "처리 완료" },
      ],
      selectedNo: this.violationitem.reportStatus,
      classes: "",
      date: "",
      time: "",
      statusChanged: 0,
    };
  },
  mounted: function () {
    this.getFormatDate(this.violationitem);
    this.mountStatus();
    // this.changeColor();
    // this.DateTransform();
  },
  updated() {
    console.log("Document Updated");
    this.changeStatus();
    // this.changeColor();
  },
  watch: {
    selectedNo: function () {
      // alert("here!");
      this.violationitem.reportStatus = this.selectedNo;
      http
        .put(
          `/violation/${this.violationitem.userNo}/${this.violationitem.violationNo}/${this.violationitem.reportStatus}`,
          // `/violation`,
          {
            userNo: this.violationitem.userNo,
            violationNo: this.violationitem.violationNo,
            reportStatus: this.violationitem.reportStatus,
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
            msg =
              "상태가 성공적으로 변경되었단다." +
              this.violationitem.reportStatus;
          }
          alert(msg);
          // console.log(data);
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },
  },
  methods: {
    modalId(i) {
      return "modal" + i;
    },
    indexPlus(i) {
      return (i = ++i);
    },
    getFormatDate(violationitem) {
      return moment(new Date(violationitem.date)).format("YYYY.MM.DD");
    },
    mountStatus() {
      var selected = this.violationitem.reportStatus;
      if (selected == 0) {
        this.selected1 = 0;
        this.classes = "bg-secondary text-light";
        // this.violationitem.reportStatus = 0;
      } else if (selected == 1) {
        this.selected1 = 1;
        this.classes = "bg-primary text-white";
        // this.violationitem.reportStatus = 1;
      } else if (selected == 2) {
        this.selected1 = 2;
        this.classes = "bg-success text-white";
        // this.violationitem.reportStatus = 2;
      }
    },
    changeStatus() {
      // console.log("changeStatus on");
      var selected = this.selected1;
      if (selected == 0) {
        this.classes = "bg-secondary text-light";
      } else if (selected == 1) {
        this.classes = "bg-primary text-white";
      } else if (selected == 2) {
        this.classes = "bg-success text-white";
      }
      this.selectedNo = this.selected1;
      // http
      //   .put(`/violation`, {
      //     reportStatus: changedNum,
      //   })
      //   .then(({ data }) => {
      //     let msg = "저장에 실패하였습니다.";
      //     if (data === "success") {
      //       msg = "저장이 완료되었습니다.";
      //     }
      //     alert(msg);
      //   })
      //   .catch(() => {
      //     alert("에러가 발생했습니다.");
      //   });
    },
    updateStatus() {
      if (
        this.selected1 === "접수 완료" &&
        this.violationitem.reportStatus != 0
      ) {
        http
          .put(
            `/violation`,
            {
              reportStatus: 0,
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
            alert(msg);
          })
          .catch(() => {
            alert("에러가 발생했습니다.");
          });
      }
    },
    onClickCheck() {
      alert("check");
    },
    // changeColor() {
    //   var selected = this.selected1;
    //   if (selected == "신고 미접수") {
    //     this.classes = "bg-secondary text-light";
    //     this.violationitem.reportStatus = 0;
    //   } else if (selected == "접수 완료") {
    //     this.classes = "bg-primary text-white";
    //   } else if (selected == "처리 완료") {
    //     this.classes = "bg-success text-white";
    //   }
    // },
    clickPrevent(event) {
      event.stopPropagation();
    },
    // DateTransform() {
    //   var date =
    //     this.violationitem.date.slice(0, 4) +
    //     "년 " +
    //     this.violationitem.date.slice(5, 7) +
    //     "월 " +
    //     this.violationitem.date.slice(8, 10) +
    //     "일 ";
    //   var time =
    //     this.violationitem.time.slice(0, 2) +
    //     "시 " +
    //     this.violationitem.time.slice(3, 5) +
    //     "분";
    //   this.date = date;
    //   this.time = time;
    // },
  },
  computed: {
    ...mapGetters(["violationitems"]),
  },
};
</script>

<style>
</style>