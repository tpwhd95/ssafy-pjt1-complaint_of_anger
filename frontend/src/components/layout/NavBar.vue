<template>
  <!-- <nav
    class="d-flex justify-content-around navbar navbar-light fixed-top border-bottom border-danger p-1"
    style="padding: 16px; background-color: #ffffff;"
  >-->
  <nav
    class="container navbar navbar-light fixed-top border-bottom p-1"
    style="padding: 16px; background-color: #ffffff;"
  >
    <!-- 1 -->
    <b-button v-b-toggle.sidebar-1 class="float-left bg-danger border-danger">MENU</b-button>
    <div v-if="this.$session.get('email') != null"></div>

    <!-- 2 -->
    <div class="text-center">
      <router-link to="/" style="text-decoration: none;">
        <!-- <h1 class="ft-bhs" style="margin: 0px;">분노의 민원</h1> -->
        <img src="@/assets/Logo_1.png" alt="logo-image" style="width: 40%; margin-bottom: 10px;" />
      </router-link>
    </div>

    <!-- 3 -->
    <div>
      <div>
        <b-button
          v-if="this.$session.get('email') == null"
          v-b-modal.modal-1
          class="float-right bg-danger rounded border-danger"
          style="padding: 7px 13px;"
        >
          <div>
            <a style="color: white; text-decoration: none;">로그인</a>
          </div>
        </b-button>

        <div>
          <b-button
            v-if="this.$session.get('email') != null && this.$session.get('isSNS') == 0"
            v-b-modal.modal-memberInfo
            class="bg-danger rounded border-danger"
            style="padding: 7px 13px;"
          >
            <div>
              <a style="color: white; text-decoration: none;">회원정보</a>
            </div>
          </b-button>&nbsp;
          <b-button
            v-if="this.$session.get('email') != null"
            v-on:click="sessionDistroy"
            class="bg-danger rounded border-danger"
            style="padding: 7px 13px;"
          >
            <div>
              <a style="cursor: pointer; color: white; text-decoration: none;">로그아웃</a>
            </div>
          </b-button>
        </div>
      </div>

      <!-- 모달 -->
      <div>
        <b-modal id="modal-1" title="로그인" style="padding: 16px 32px;" hide-footer>
          <div class="modal-body">
            <form @submit.prevent>
              <div class="form-group">
                <input
                  type="text"
                  class="form-control"
                  id="email"
                  ref="email"
                  placeholder="이메일을 입력하세요"
                  v-model="email"
                />
              </div>
              <div class="form-group">
                <input
                  @keypress.enter="checkHandlerLogin"
                  type="password"
                  class="form-control"
                  id="pw"
                  ref="pw"
                  placeholder="비밀번호를 입력하세요"
                  v-model="pw"
                />
              </div>
              <div class="form-group">
                <!-- 로그인 버튼 -->
                <a
                  class="btn btn-primary text-decoration-none m-auto"
                  @click="checkHandlerLogin"
                  style="width: 100%; height: 50px;"
                >
                  <p style="color: white; font-size: 20px; margin-top: 4px;">로그인</p>
                </a>
                <br />
                <hr />
                <!-- 네이버 로그인 버튼 -->
                <div class="text-center">
                  <img
                    @click="naverLogin"
                    width="60%"
                    style="cursor: pointer;"
                    src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png"
                  />
                </div>
              </div>
            </form>
          </div>

          <div class="d-flex justify-content-around">
            <b-button class="btn btn-primary" v-b-modal.modal-multi-1>이메일 찾기</b-button>
            <b-button class="btn btn-primary" v-b-modal.modal-multi-2>비밀번호 찾기</b-button>
            <b-button class="btn btn-primary" v-b-modal.modal-multi-3 style="margin: 0px 10px;">회원가입</b-button>
          </div>
        </b-modal>
        <!-- 이메일 찾기 -->
        <b-modal id="modal-multi-1" title="이메일 찾기" hide-footer>
          <div class="modal-body">
            <div class="form-group">
              <label>이름 :</label>
              <input
                type="text"
                class="form-control"
                id="name"
                ref="name"
                placeholder="이름을 입력하세요"
                v-model="name"
              />
            </div>
            <div class="form-group">
              <label>성별 :</label>
              <input
                type="text"
                class="form-control"
                id="gender"
                ref="gender"
                placeholder="남 또는 여"
                v-model="gender"
              />
            </div>
            <div class="form-group">
              <label>생년월일 :</label>
              <input
                type="text"
                class="form-control"
                id="birth"
                ref="birth"
                placeholder="2020-01-01"
                v-model="birth"
              />
            </div>
            <div class="form-group">
              <label>핸드폰 번호 :</label>
              <input
                type="text"
                class="form-control"
                id="phone"
                ref="phone"
                placeholder="핸드폰 번호를 입력하세요"
                v-model="phone"
              />
            </div>
            <div class="form-group">
              <button class="btn btn-primary btn-lg btn-block login-btn" @click="findEmail">이메일 찾기</button>
            </div>
          </div>
        </b-modal>
        <!-- 비밀번호 찾기 -->
        <b-modal id="modal-multi-2" title="비밀번호 찾기" hide-footer>
          <div class="modal-body">
            <div class="form-group">
              <label>이메일 :</label>
              <input
                type="text"
                class="form-control"
                id="email"
                ref="email"
                placeholder="이메일을 입력하세요"
                v-model="email"
              />
            </div>
            <div class="form-group">
              <label>이름 :</label>
              <input
                type="text"
                class="form-control"
                id="name"
                ref="name"
                placeholder="이름을 입력하세요"
                v-model="name"
              />
            </div>
            <div class="form-group">
              <label>성별 :</label>
              <input
                type="text"
                class="form-control"
                id="gender"
                ref="gender"
                placeholder="남 또는 여"
                v-model="gender"
              />
            </div>
            <div class="form-group">
              <label>생년월일 :</label>
              <input
                type="text"
                class="form-control"
                id="birth"
                ref="birth"
                placeholder="2020-01-01"
                v-model="birth"
              />
            </div>
            <div class="form-group">
              <label>핸드폰 번호 :</label>
              <input
                type="text"
                class="form-control"
                id="phone"
                ref="phone"
                placeholder="핸드폰 번호를 입력하세요"
                v-model="phone"
              />
            </div>
            <div class="form-group">
              <button
                class="btn btn-primary btn-lg btn-block login-btn"
                @click="findPassword"
              >비밀번호 찾기</button>
            </div>
          </div>
        </b-modal>
        <!-- 회원가입 -->
        <b-modal id="modal-multi-3" title="회원가입" hide-footer>
          <div class="modal-body">
            <div class="form-group">
              <label>이메일 :</label>
              <input
                type="text"
                class="form-control"
                id="email"
                ref="email"
                placeholder="이메일을 입력하세요"
                v-model="email"
              />
            </div>
            <div class="form-group">
              <label>비밀번호 :</label>
              <input
                type="password"
                class="form-control"
                id="pw"
                ref="pw"
                placeholder="비밀번호를 입력하세요"
                v-model="pw"
              />
            </div>
            <div class="form-group">
              <label>이름 :</label>
              <input
                type="text"
                class="form-control"
                id="name"
                ref="name"
                placeholder="이름을 입력하세요"
                v-model="name"
              />
            </div>
            <div class="form-group">
              <label>성별 :</label>
              <input type="radio" id="one" value="남" v-model="gender" />
              <label for="one">남</label>
              <input type="radio" id="two" value="여" v-model="gender" />
              <label for="two">여</label>
              <!-- <input
                type="text"
                class="form-control"
                id="gender"
                ref="gender"
                placeholder="남 또는 여"
                v-model="gender"
              />-->
            </div>
            <div class="form-group">
              <label>생년월일 :</label>
              <input
                type="text"
                class="form-control"
                id="birth"
                ref="birth"
                placeholder="2020-01-01"
                v-model="birth"
              />
            </div>
            <div class="form-group">
              <label>핸드폰 번호 :</label>
              <input
                type="text"
                class="form-control"
                id="phone"
                ref="phone"
                placeholder="핸드폰 번호를 입력하세요"
                v-model="phone"
              />
            </div>
            <div class="form-group">
              <button
                class="btn btn-primary btn-lg btn-block login-btn"
                @click="checkHandlerInsert"
              >회원가입</button>
            </div>
          </div>
        </b-modal>
        <!-- 회원정보 -->
        <b-modal id="modal-memberInfo" title="회원정보" hide-footer>
          <div class="form-group">
            <label class="d-flex">
              이메일:
              <div class="ml-1" v-text="this.$session.get('email')"></div>
            </label>
          </div>
          <div class="form-group">
            <label>비밀번호:</label>
            <input
              type="password"
              class="form-control"
              id="pw"
              ref="pw"
              placeholder="비밀번호를 입력하세요"
              v-model="pw"
            />
          </div>
          <div class="form-group">
            <label>비밀번호 확인:</label>
            <input
              type="password"
              class="form-control"
              id="pw_re"
              ref="pw_re"
              placeholder="비밀번호 한번 더 입력하세요"
              v-model="pw_re"
            />
          </div>
          <div class="form-group">
            <label class="d-flex">
              이름:
              <div class="ml-1" v-text="this.$session.get('name')"></div>
            </label>
            <input
              type="text"
              class="form-control"
              id="name"
              ref="name"
              placeholder="이름을 입력하세요"
              v-model="name"
            />
          </div>
          <div class="form-group">
            <label>성별 :</label>
            <input type="radio" id="one" value="남" v-model="gender" />
            <label for="one">남</label>
            <input type="radio" id="two" value="여" v-model="gender" />
            <label for="two">여</label>
            <!-- <label class="d-flex">
              성별:
              <div class="ml-1" v-text="this.$session.get('gender')"></div>
            </label>-->
            <input
              type="text"
              class="form-control"
              id="gender"
              ref="gender"
              placeholder="남 또는 여"
              v-model="gender"
            />
          </div>
          <div class="form-group">
            <label class="d-flex">
              생년월일:
              <div class="ml-1" v-text="this.$session.get('birth')"></div>
            </label>
            <input
              type="text"
              class="form-control"
              id="birth"
              ref="birth"
              placeholder="2020-01-01"
              v-model="birth"
            />
          </div>
          <div class="form-group">
            <label class="d-flex">
              핸드폰 번호:
              <div class="ml-1" v-text="this.$session.get('phone')"></div>
            </label>
            <input
              type="text"
              class="form-control"
              id="phone"
              ref="phone"
              placeholder="핸드폰 번호를 입력하세요"
              v-model="phone"
            />
          </div>
          <div class="form-group d-flex justify-content-around">
            <button class="btn btn-info" @click="checkHandlerModify">회원정보수정</button>
            <button class="btn btn-info" @click="deleteHandler">
              <div style="padding: 0px 12px; color: white;">회원탈퇴</div>
            </button>
          </div>
        </b-modal>
      </div>
    </div>
  </nav>
</template>

<script>
import http from "@/util/http-common";
import { mapGetters } from "vuex";

var CLIENT_ID = process.env.VUE_APP_NAVER_CLIENT_ID;

export default {
  name: "NavBar",
  props: {
    type: { type: String },
  },
  data: function () {
    return {
      // 백엔드에서 필요로 하는 데이터
      email: this.$session.get("email"),
      pw: "", // 백엔드로 보낼 때 password 변수에 담아서 ex) password: this.pw
      pw_re: "",
      name: this.$session.get("name"),
      gender: this.$session.get("gender"),
      birth: this.$session.get("birth"),
      phone: this.$session.get("phone"),

      // 토큰 관리
      jwt: module.require("jsonwebtoken"),
      access_token: this.$route.query.token,
      redirectURI: "http://localhost:8399/ssafy/api/sns/login",
      state: 123,
      naverLoginURL:
        "https://nid.naver.com/oauth2.0/authorize?response_type=code",
    };
  },
  created() {
    // 네이버 로그인
    if (this.access_token != null) {
      this.$session.set("userNo", this.jwt.decode(this.access_token).userNo);
      this.$session.set("email", this.jwt.decode(this.access_token).email);
      this.$session.set("name", this.jwt.decode(this.access_token).name);
      this.$session.set("gender", this.jwt.decode(this.access_token).gender);
      this.$session.set("birth", this.jwt.decode(this.access_token).birth);
    }
  },
  mounted: function () {
    // this.$store.dispatch("getViolations");
    // console.log("여기는 마운티드");
    // console.log(this.$session.get("email"));
    // console.log(this.$session.get("userNo"));
  },
  computed: {
    ...mapGetters(["violationitems"]),
  },
  methods: {
    validEmail: function (email) {
      var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      return re.test(email);
    },
    validBirth: function (birth) {
      var re = /^\d{4}-\d{2}-\d{2}$/;
      return re.test(birth);
    },

    // 네이버 아이디 로그인
    naverLogin() {
      // this.naverLoginURL += "&client_id=" + this.CLIENT_ID;
      this.naverLoginURL += "&client_id=" + CLIENT_ID;
      this.naverLoginURL += "&redirect_uri=" + this.redirectURI;
      this.naverLoginURL += "&state=" + this.state;

      location.href = this.naverLoginURL;
    },
    // 로그인
    checkHandlerLogin() {
      let err = true;
      let msg = "";
      !this.email && ((msg = "아이디를 입력해주세요"), (err = false)),
        this.$refs.email.focus();
      err && !this.pw && ((msg = "비밀번호를 입력해주세요"), (err = false)),
        this.$refs.pw.focus();

      console.log(this.email + " " + this.pw);
      if (!err) alert(msg);
      else this.loginHandler();
    },
    loginHandler() {
      http
        .post(`/user/login`, {
          email: this.email,
          password: this.pw,
        })
        .then(({ data }) => {
          // JSON 형태
          let msg = "로그인에 실패하였습니다.";

          if (data.success === "success") {
            msg = "로그인이 완료되었습니다.";
            //alert(data.userinfo.userNo);
            this.$session.set("email", this.email);
            this.$session.set("userNo", data.userinfo.userNo);
            this.$session.set("name", data.userinfo.name);
            this.$session.set("gender", data.userinfo.gender);
            this.$session.set("birth", data.userinfo.birth);
            this.$session.set("phone", data.userinfo.phone);
            this.$session.set("token", data.token);
            // sessionStorage.setItem("vue-session-key", "this.email");
            // sessionStorage.setItem("vue-session-key", "data.userinfo.userNo");

            // this.$store.dispatch("getViolations");
            // console.log("로그인 데이터");
            // console.log(this.$session.get("email"));
            console.log(this.$session.get("name"));
            this.$router.push("/");
            this.$router.go();
          }
          alert(msg);
          // this.getVio();
        })
        .catch(() => {
          alert("에러가 발생했습니다-login.");
        });
    },
    // getVio() {
    //   this.$store.dispatch("getViolations");
    // },

    // 회원가입
    checkHandlerInsert() {
      let err = true;
      let msg = "";
      if (!this.validEmail(this.email)) {
        (msg = "이메일 형식을 지켜주세요"),
          (err = false),
          this.$refs.email.focus();
      } else if (this.pw.length <= 6) {
        (msg = "비밀번호를 7자 이상 입력해주세요"),
          (err = false),
          this.$refs.pw.focus();
      } else if (!this.validBirth(this.birth)) {
        (msg = "올바른 생년월일을 입력해주세요"),
          (err = false),
          this.$refs.birth.focus();
      } else if (this.gender == null) {
        alert("성별을 선택해 주세요");
        (msg = "성별을 선택해 주세요"),
          (err = false),
          this.$refs.gender.focus();
      }
      err &&
        !this.phone &&
        ((msg = "핸드폰번호를 입력해주세요"),
        (err = false),
        this.$refs.phone.focus());
      err &&
        !this.name &&
        ((msg = "이름을 입력해주세요"), (err = false), this.$refs.name.focus());

      if (!err) alert(msg);
      else this.insertHandler();
    },

    insertHandler() {
      http
        .post(`/user`, {
          email: this.email,
          password: this.pw,
          name: this.name,
          gender: this.gender,
          birth: this.birth,
          phone: this.phone,
        })
        .then(({ data }) => {
          // 여기서부터 코딩
          let msg = "회원가입에 실패하였습니다.";
          if (data === "success") {
            msg = "회원가입이 완료되었습니다.";
            this.$router.push("/");
            this.$router.go();
          }
          alert(msg);
          this.$root.$emit("bv::hide::modal", "modal-multi-3");
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
      this.email = null;
      this.pw = null;
      this.name = null;
      this.gender = null;
      this.birth = null;
      this.phone = null;
    },

    // 회원수정
    checkHandlerModify() {
      let err = true;
      let msg = "";
      !this.pw &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.pw.focus());
      err &&
        !this.pw_re &&
        ((msg = "재입력 비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.pw_re.focus());
      err &&
        !this.name &&
        ((msg = "이름을 입력해주세요"), (err = false), this.$refs.name.focus());
      err &&
        !this.email &&
        ((msg = "이메일을 입력해주세요"),
        (err = false),
        this.$refs.email.focus());
      err &&
        !this.gender &&
        ((msg = "성별을 입력해주세요"),
        (err = false),
        this.$refs.gender.focus());
      err &&
        !this.birth &&
        ((msg = "생년월일을 입력해주세요"),
        (err = false),
        this.$refs.birth.focus());
      err &&
        !this.phone &&
        ((msg = "핸드폰번호를 입력해주세요"),
        (err = false),
        this.$refs.phone.focus());
      err &&
        this.pw != this.pw_re &&
        ((msg = "비밀번호와 재입력 비밀번호가 다릅니다."),
        (err = false),
        this.$refs.pw_re.focus());

      if (!err) alert(msg);
      else this.modifyHandler();
    },
    modifyHandler() {
      http
        .put(
          `/user`,
          {
            email: this.$session.get("email"),
            password: this.pw,
            name: this.name,
            gender: this.gender,
            birth: this.birth,
            phone: this.phone,
          },
          {
            headers: {
              token: this.$session.get("token"),
            },
          }
        )
        .then(({ data }) => {
          // 여기서부터 코딩
          let msg = "회원수정에 실패하였습니다.";
          if (data.success === "success") {
            msg = "회원수정이 완료되었습니다.";
            this.$session.set("userNo", data.userinfo.userNo);
            this.$session.set("name", data.userinfo.name);
            this.$session.set("gender", data.userinfo.gender);
            this.$session.set("birth", data.userinfo.birth);
            this.$session.set("phone", data.userinfo.phone);
          }
          this.$root.$emit("bv::hide::modal", "modal-memberInfo");
          alert(msg);
          this.$router.go();
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },

    // 세션 삭제
    sessionDistroy() {
      if (this.access_token != null) {
        alert("네이버 로그아웃");
        http.post(`/sns/logout`).then(({ data }) => {
          if (data == "success") {
            alert("로그아웃 " + data);
            location.href = "http://localhost:8080/";
          }
        });
      }
      this.$session.destroy();
      this.jwt = "";
      this.access_token = "";
      this.email = "";
      this.pw = "";
      this.name = "";
      this.birth = "";
      this.phone = "";
      this.joindate = "";
      this.$router.push("/");
      this.$router.go();
    },

    // 회원삭제
    deleteHandler() {
      console.log(this.$session.get("email"));
      http
        .post(
          `/user/del`,
          JSON.stringify({
            email: this.$session.get("email"),
          }),
          {
            headers: {
              token: this.$session.get("token"),
            },
          }
        )
        .then(({ data }) => {
          let msg = "회원삭제에 실패하였습니다..";
          if (data === "success") {
            msg = "회원삭제가 완료되었습니다.";
          }
          this.$root.$emit("bv::hide::modal", "modal-memberInfo");
          alert(msg);
          this.$session.destroy();
          this.$router.go();
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },

    // 이메일 찾기
    findEmail() {
      http
        .post(`/user/findEmail`, {
          name: this.name,
          gender: this.gender,
          birth: this.birth,
          phone: this.phone,
        })
        .then(({ data }) => {
          console.log(data);
          if (data == "fail") {
            alert("없는 회원입니다.");
          }
          if (data != "fail") {
            alert(data);
            this.$root.$emit("bv::hide::modal", "modal-multi-1");
          }
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },

    // 비밀번호 찾기
    findPassword() {
      http
        .post(`/user/findPassword`, {
          email: this.email,
          name: this.name,
          gender: this.gender,
          birth: this.birth,
          phone: this.phone,
        })
        .then(({ data }) => {
          if (data == "fail") {
            alert("잘못된 정보입니다.");
          }
          if (data != "fail") {
            alert(
              "임시 비밀번호 입니다. 로그인 후 비밀번호를 수정해 주세요." + data
            );
            this.$root.$emit("bv::hide::modal", "modal-multi-2");
          }
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },
  },
};
</script>

<style>
/* 로그인 모달 */
#modal-1___BV_modal_header_ {
  padding: 16px 16px 16px 32px;
}
</style>
