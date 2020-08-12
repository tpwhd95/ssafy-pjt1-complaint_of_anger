<template>
  <div>
    <Map :items="this.$store.state.violationitems" />
  </div>
</template>

<script>
import http from "@/util/http-common";

import Map from "@/components/KakaoMap.vue";
// import Map from "@/components/NaverMap.vue";

export default {
  name: "MapMain",
  data() {
    return {
      items: [],
    };
  },
  components: {
    Map,
  },
  created() {
    http
      .get(`/violation/${this.$session.get("userNo")}`, {
        headers: {
          token: this.$session.get("token"),
        },
      })
      .then(({ data }) => {
        if (data) {
          // this.local_violationitems = data;
          // this.items = data;
          this.$store.state.violationitems = data;
          // alert("ㅔㅗ");
        } else {
          alert(" 실패했습니다.");
        }
      })
      .catch(() => {
        alert("에러가 발생했습니다.");
      });
  },
};
</script>

<style></style>
