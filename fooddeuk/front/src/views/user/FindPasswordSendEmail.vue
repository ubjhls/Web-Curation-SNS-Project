<template>
    <div class="user" id="login">
        <div class="wrapC">
            <h1>입력하신 이메일로 임시 비밀번호를 발송했습니다.</h1>
            <h1>메일함을 확인해 주세요</h1>
            <h2 style="text-align:center">입력하신 이메일 주소는 </h2>
            <h2 style="text-align:center; margin-top:10px">{{this.$route.params.email}} 입니다</h2>
            <br/>
            <button @click="resendgo" class="btn btn--back btn--ok" style="background-color: white; color: black;">
                메일이 도착하지 않았나요?
            </button>
            <router-link v-bind:to="{name:'Login'}">
                <button class="btn btn--cancel" style="background-color: green;">
                    로그인 하러 가기
                </button>
            </router-link>
        </div>
    </div>
</template>

<script>

    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'

    export default {
        components: {},
        created() {},
        watch: {},
        methods: {
            resendgo() {
                this.email = this.$route.params.email
                this.password = this.$route.params.password
                this.nickName = this.$route.params.nickName
                var router = this.$router;
                router.push({
                    name: "FindPasswordReSendEmail",
                    params: {
                        "nickName": this.nickName,
                        "email": this.email,
                        "password": this.password
                    }
                });
            }
        },
        data: () => {
            return {email: "", password: "", nickName: ""}
        }
    }
</script>