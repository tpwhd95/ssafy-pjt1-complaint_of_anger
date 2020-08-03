<template>
  <nav
    class="d-flex justify-content-around navbar navbar-light fixed-top border-bottom border-info p-1"
    style="padding: 16px; background-color: #ffffff;"
  >
    <!-- 1 -->
    <b-button v-b-toggle.sidebar-1 class="bg-info border-info">MENU</b-button>

    <!-- 2 -->
    <router-link to="/" class="text-center" style="text-decoration: none;">
      <!-- <h1 class="ft-bhs" style="margin: 0px;">분노의 민원</h1> -->
      <img src="@/assets/Logo_1.png" alt="logo-image" style="width: 30%;" />
    </router-link>

    <!-- 3 -->
    <div>
      <div>
        <b-button
          v-if="this.$session.get('email') == null"
          v-b-modal.modal-1
          class="bg-info rounded border-info"
          style="padding: 7px 13px;"
        >
          <div>
            <a style="color: white; text-decoration: none;">로그인</a>
          </div>
        </b-button>

        <div>
          <b-button
            v-if="this.$session.get('email') != null"
            v-b-modal.modal-memberInfo
            class="bg-info rounded border-info"
            style="padding: 7px 13px;"
          >
            <div>
              <a style="color: white; text-decoration: none;">회원정보</a>
            </div>
          </b-button>&nbsp;
          <b-button
            v-if="this.$session.get('email') != null"
            v-on:click="sessionDistroy"
            class="bg-info rounded border-info"
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
                  type="passward"
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
              <button
                class="btn btn-primary btn-lg btn-block login-btn"
                @click="checkHandlerInsert"
              >회원가입</button>
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
                @click="checkHandlerInsert"
              >회원가입</button>
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
            <label>이름:</label>
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
            <label>성별:</label>
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
            <label>생년월일:</label>
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
            <label>핸드폰 번호:</label>
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

export default {
  name: "NavBar",
  props: {
    type: { type: String },
  },
  created() {
    // 네이버 로그인
    this.$session.set("email", this.jwt.decode(this.access_token).email);
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
      CLIENT_ID: "px2gRec1H_fbAwS22rLW",
      redirectURI: "http://localhost:9999/ssafy/api/sns/login",
      state: 123,
      naverLoginURL:
        "https://nid.naver.com/oauth2.0/authorize?response_type=code",
    };
  },
  methods: {
    // 네이버 아이디 로그인
    naverLogin() {
      this.naverLoginURL += "&client_id=" + this.CLIENT_ID;
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
        .post(`/login`, {
          email: this.email,
          password: this.pw,
        })
        .then(({ data }) => {
          let msg = "로그인에 실패하였습니다.";
          if (data === "success") {
            msg = "로그인이 완료되었습니다.";
            this.$session.set("email", this.email);
            console.log(this.$session.get("email"));
            this.$router.push("/");
            this.$router.go();
          }
          alert(msg);
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },

    // 회원가입
    checkHandlerInsert() {
      let err = true;
      let msg = "";
      !this.email &&
        ((msg = "이메일을 입력해주세요"),
        (err = false),
        this.$refs.email.focus());
      err &&
        !this.pw &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.pw.focus());
      err &&
        !this.name &&
        ((msg = "이름을 입력해주세요"), (err = false), this.$refs.name.focus());
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

      if (!err) alert(msg);
      else this.insertHandler();
    },
    insertHandler() {
      http
        .post(``, {
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
        .put(``, {
          email: this.$session.get("email"),
          password: this.pw,
          name: this.name,
          gender: this.gender,
          birth: this.birth,
          phone: this.phone,
        })
        .then(({ data }) => {
          // 여기서부터 코딩
          let msg = "회원수정에 실패하였습니다.";
          if (data === "success") {
            msg = "회원수정이 완료되었습니다.";
          }
          this.$root.$emit("bv::hide::modal", "modal-memberInfo");
          alert(msg);
        })
        .catch(() => {
          alert("에러가 발생했습니다.");
        });
    },

    // 세션 삭제
    sessionDistroy() {
      if (this.jwt) {
        http.get("/sns/logout").then(({ data }) => {
          alert("네이버 로그아웃 " + data);
        });
      }
    },

    // 회원삭제
    deleteHandler() {
      console.log(this.$session.get("email"));
      http
        .post(
          `del`,
          JSON.stringify({
            email: this.$session.get("email"),
          })
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
  },
};
</script>

<style>
/* 로그인 모달 */
#modal-1___BV_modal_header_ {
  padding: 16px 16px 16px 32px;
}
</style>
