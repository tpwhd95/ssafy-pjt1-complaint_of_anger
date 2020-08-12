<template>
  <div>
    <br />
    <h1 class="text-center">제보목록</h1>
    <!-- <div class="d-flex justify-content-between align-items-center"> -->
    <div class="row align-items-center">
      <!-- filter button -->
      <div class="col-3.5">
        <b-form-checkbox-group
          v-model="selected"
          :options="options"
          class="mb-3"
          value-field="item"
          text-field="name"
          disabled-field="notEnabled"
          @input="optionController"
        ></b-form-checkbox-group>
      </div>
      <!-- items dropdown -->
      <div class="col-2.5">
        <b-form-select
          v-model="selected2"
          :options="options2"
          class="mb-0"
          value-field="item"
          text-field="name"
          disabled-field="notEnabled"
          @input="optionController"
        ></b-form-select>
      </div>
      <!-- find term calendar -->
      <div class="col d-flex">
        <b-form-group id="input-group-3" label="시작 날짜 선택">
          <b-form-datepicker
            id="example-datepicker1"
            v-model="startdate"
            class="mb-2"
            @input="optionController"
            label-help
          ></b-form-datepicker>
        </b-form-group>
        <b-form-group id="input-group-3" label="종료 날짜 선택">
          <b-form-datepicker
            id="example-datepicker2"
            v-model="enddate"
            class="mb-2"
            @input="optionController"
            label-help
          ></b-form-datepicker>
        </b-form-group>
      </div>
      <!-- search bar -->
      <div class="col">
        <span class="mr-2">search :</span>
        <input class="mr-2" type="text" v-model="searchText" @keydown.enter="optionController" />
        <!-- <b-button variant="danger" @click="optionController">Button</b-button> -->
      </div>
    </div>
    <!-- cards -->
    <div
      v-if="searchviolationitems.length == 0 && selected2 == '전체' && searchText.length == 0 && selected.length == 0 && startdate.length == 0 && enddate.length == 0"
    >
      <b-container class="bv-example-row">
        <b-row>
          <Case
            v-for="violationitem in this.$store.state.violationitems.slice().reverse()"
            :key="violationitem.violationNo"
            :violationitem="violationitem"
          />
        </b-row>
      </b-container>
    </div>
    <div v-else>
      <b-container class="bv-example-row">
        <b-row>
          <Case
            v-for="violationitem in searchviolationitems.slice().reverse()"
            :key="violationitem.violationNo"
            :violationitem="violationitem"
          />
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";

// 기존 폼
// import Case from "@/components/case/Case.vue";
// 유튜브 폼
import Case from "@/components/case/CaseTube.vue";

export default {
  name: "CaseList",
  components: {
    Case,
  },
  props: {},
  data() {
    return {
      local_violationitems: [],
      searchText: "",
      searchviolationitems: [],
      selected: [],
      options: [
        { item: 0, name: "신고 미접수" },
        { item: 1, name: "접수 완료" },
        { item: 2, name: "처리 완료" },
      ],
      startdate: "",
      enddate: "",
      templist_click: [],
      templist_select: [],
      templist_find: [],
      selected2: "전체",
      options2: [
        { item: "전체", name: "전체" },
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
  created() {
    http
      .get(`/violation/${this.$session.get("userNo")}`, {
        headers: {
          token: this.$session.get("token"),
        },
      })
      .then(({ data }) => {
        if (data != null) {
          this.local_violationitems = data;
          this.$store.state.violationitems = data;
        } else {
          alert(" 실패했습니다.");
        }
      })
      .catch(() => {
        alert("에러가 발생했습니다.");
      });
  },
  methods: {
    clickFilter() {
      const emptyItems = new Array();
      this.templist_click = emptyItems;
      if (this.selected.length != 0) {
        for (var i in this.local_violationitems) {
          if (
            this.selected.includes(this.local_violationitems[i].reportStatus)
          ) {
            this.templist_click.push(this.local_violationitems[i]);
          }
        }
      } else {
        this.templist_click = this.local_violationitems;
      }
    },
    selectItem() {
      const emptyItems = new Array();
      this.templist_select = emptyItems;
      if (this.selected2 != "전체") {
        for (var i in this.templist_click) {
          if (this.selected2 == this.templist_click[i].item) {
            this.templist_select.push(this.templist_click[i]);
          }
        }
      } else {
        this.templist_select = this.templist_click;
      }
    },
    findTerm() {
      const emptyItems = new Array();
      this.templist_find = emptyItems;
      if (this.startdate.length != 0 && this.enddate.length != 0) {
        for (var i in this.templist_select) {
          if (
            this.startdate <= this.templist_select[i].date &&
            this.templist_select[i].date <= this.enddate
          ) {
            this.templist_find.push(this.templist_select[i]);
          }
        }
      } else {
        this.templist_find = this.templist_select;
      }
    },
    searchOnlist() {
      const emptyItems = new Array();
      this.searchviolationitems = emptyItems;
      for (var i in this.templist_find) {
        if (
          this.templist_find[i].contents.indexOf(this.searchText) != -1 ||
          this.templist_find[i].spot.indexOf(this.searchText) != -1 ||
          this.templist_find[i].carNum.indexOf(this.searchText) != -1 ||
          this.templist_find[i].address.indexOf(this.searchText) != -1
        ) {
          this.searchviolationitems.push(this.templist_find[i]);
        }
      }
    },
    optionController() {
      this.clickFilter();
      this.selectItem();
      this.findTerm();
      this.searchOnlist();
    },
  },
  computed: {},
};
</script>

<style></style>
