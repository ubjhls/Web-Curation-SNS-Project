<template>
    <div class="user join">
        <v-app>
        <div class="header" style="width:100%; height:40px">
            <div style="float:left;">
            <button v-on:click="goBack">
                <img src="../../assets/images/backIcon.png" style="width:35px;">
            </button>
            </div>
            <p style="vertical-align: middle;padding: 8px 5px;float:left;">내 정보 수정</p>
        </div>
        <div class="wrapC">
            <br>
            <br>
            <br>
            <img src="../../assets/images/fooddeuk_logoo.png" style="height:70px; margin-left:120px">
                 <v-text-field style="color:blue;" v-model="email" label="이메일" id="email" hide-details="auto" disabled="disabled">
                     
                 </v-text-field>

                <br>
           
                 <div style="width:80%; float:left">
                    <v-text-field style="color:blue" v-model="nickName" id="nickName" label="닉네임" hide-details="auto"></v-text-field>
                 </div>
 
                <div style="width:20%; float:left">
                    <button class="check-button" v-on:click="checkNick">중복체크</button>
                </div>
                    
                <div style="clear:both"></div>

                <div class="error-text" v-if="error.nickName">
                    {{error.nickName}}
                </div>
                <div class="error-text" v-if="exist_nickName_confirm === 'a'">
                    {{ exist_nickName }}
                </div>
                <div class="error-textt" v-else-if="exist_nickName_confirm ==='b'"> 
                    {{ exist_nickName }}
                </div>
                <br>
           
            <div>
                <v-text-field style="color:blue" v-model="name" id="name" label="이름" hide-details="auto" disabled="disabled">
                </v-text-field>
            </div>
            <br>
            <v-text-field style="color:blue" v-model="intro" id="intro" label="한줄소개" hide-details="auto"></v-text-field>
            <div class="error-text" v-if="error.intro">
                    {{error.intro}}
                </div>
            
            <br><br>
            <v-switch
            label="내 피드 공개 설정"
            v-model="auth"
            ></v-switch>
            


            <br>
            <br>
                <button class="btn btn--back btn--ok" v-on:click="submit"
                :disabled="!isSubmit"
                :class="{disabled : !isSubmit}">
                    저 장
                </button>
            </div>

        </v-app>   
        </div>    
</template>

<script>

    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'
    import PV from 'password-validator'
    import * as EmailValidator from 'email-validator';
    import UserApi from '../../apis/UserApi'
    import Axios from 'axios'
    import http from '../../../http-common'

    import {mapState} from 'vuex';

    export default {
        computed : {
            ...mapState(['userinfo']),
        },
        created() {
            this.component = this;
            this
                .passwordSchema
                .is().min(8)
                .is().max(100)
                .has()
                .digits()
                .has()
                .letters();
            if(this.$store.state.userinfo!=null) {
                this.email = this.$store.state.userinfo.email;
                this.name = this.$store.state.userinfo.name;
            }

            this.getMyInfo();
        },
        watch: {
            nickName: function (v) {
                this.checkForm();
            },
            isTerm: function (v) {
                this.checkForm();
            },
            intro: function (v) {
                this.checkForm();
            },
            exist_nickName: function (v) {
                this.checkForm();
            },
            auth : function(v) {
                this.checkForm();
            },
            authData : function(v) {
                this.checkForm();
            }
        },methods: { 
            goBack() {
                var router = this.$router;


                if(this.isSubmit) {
                    let con = confirm("저장하지 않고 나가시겠습니까?");
                    if(con) {
                        router.go(-1);
                    }
                }
                else {
                    router.go(-1);
                }
            },
            getMyInfo() {
                let form = new FormData()
                 form.append('email', this.email)
                 http.get("/user/myinfo?email=" + this.email)
                 .then(Response => {
                    this.nickName = Response.data.nickname;
                    this.nicknameTemp = this.nickName;
                    this.authData = Response.data.auth;
                    this.intro = Response.data.intro;
                    this.introTemp = this.intro;
                    if(this.authData==0) {
                        this.auth = true;
                    }
                    else if(this.authData==1) {
                        this.auth = false;
                    }
                 })
                 .catch(Error => {
                 })
            },
            checkNick() {
                var router = this.$router;
                let {nickName} = this;
                let data = {
                    nickName
                }
                this.isCheck = true;
                if(this.nickName.length!=0 && this.nickName!=this.nicknameTemp) {
                UserApi.requestCheckNick(data, res =>{
                    if(res) {
                        this.exist_nickName = '존재하는 닉네임입니다. 다시 설정해주세요.';
                        this.exist_nickName_confirm = 'a'
                    }
                    else {
                        this.exist_nickName = '사용가능한 닉네임입니다.';
                        this.exist_nickName_confirm = 'b'
                    }
                },error=>{  
                    var router = this.$router;
                    router.push({name:"ErrorPage",params:{
                        "nickName" : this.nickName,
                        "email" : this.email,
                        "route" : this.$route.name
                    }});
                    this.isSubmit = true;
                })
                }
                
            },

            checkForm() {

                if(this.nickName.length > 15)
                    this.error.nickName = "닉네임은 2 ~ 15자 이내로 작성해주세요";
                else if(this.nickName.length < 2)
                    this.error.nickName = "닉네임은 2 ~ 15자 이내로 작성해주세요";
                else
                    this.error.nickName = false;

                if (this.intro.length < 2)
                    this.error.intro = "자기소개는 2 ~ 20자 이내로 작성해주세요";
                else if(this.intro.length > 20)
                    this.error.intro = "자기소개는 2 ~ 20자 이내로 작성해주세요"
                else   
                    this.error.intro = false


                if(this.auth) {
                    this.authData = 0;
                } else if(!this.auth) {
                    this.authData = 1;
                }
                let isSubmit = true;
                Object
                    .values(this.error)
                    .map(v => {
                        if (v) 
                            isSubmit = false;
                        }
                    )

                if(this.introTemp==this.intro)
                    isSubmit = false;

                this.isSubmit = isSubmit;
            },
            submit() {
                if (this.isSubmit) {
                    let {nickName, email, intro, authData} = this;


                    
                    let data = {
                        nickName,
                        email,
                        intro,
                        authData
                    }
                    this.isSubmit = false;
                    UserApi.requestModifyProfile(data, res => {
                        if(res=="success") {
                            alert("정보수정이 완료되었습니다.")
                            this.$router.push({name:"MainPage"})
                        }
                    }, error => {
                        var router = this.$router;
                        router.push({name:"ErrorPage", params:{
                            "nickName": this.nickName,
                            "email" : this.email,
                            "name" : this.name,
                            "intro": this.intro,
                            "route" : this.$route.name
                        }});
                    })
                }
            }
        },
        data: () => {
            return {
                intro: '',
                introTemp:'',
                name: '',
                email: '',
                password: '',
                passwordConfirm: '',
                passwordSchema: new PV(),
                nickName: '',
                nicknameTemp:'',
                isTerm: false,
                isLoading: false,
                error: {
                    exist_nickName:false,   
                    nickName: false,
                    intro: true,
                },
                isCheck: false,
                isSubmit: false,
                termPopup: false,
                exist_nickName: false,
                exist_nickName_confirm: false,
                auth: false,
                authData:0,
            }
        },
    }
</script>