<template>
<div class="wrapC">
<div class="wrapper">
  <div class="profile-card js-profile-card">
    <div class="profile-card__cnt js-profile-cnt">
        <img src="../../assets/images/profile_default.png" style="margin-bottom:10px;">
      <div class="profile-card__name">{{nickname}}</div>
      <div class="profile-card__txt">{{intro}}</div>
 
        <div class="profile-card-ctr">
            <button class="profile-card__button button--orange"></button>
        </div>
        <br>

      <div class="profile-card-inf">
        <div class="profile-card-inf__item">
          <div class="profile-card-inf__title">{{follower}}</div>
          <div class="profile-card-inf__txt">Followers</div>
        </div>

        <div class="profile-card-inf__item">
          <div class="profile-card-inf__title">{{following}}</div>
          <div class="profile-card-inf__txt">Following</div>
        </div>
      </div>
    </div>
  </div>
  <hr>
  <div v-if="!post" style="margin-top:20px; text-align:center"> 게시물이 없습니다.</div>
  <div v-for="item in post" v-bind:key="item.num" style="margin-bottom:40px">
      <v-card v-if="item === post[-1]">
          <h1 style="margin-bottom:100px">asdasd</h1>
      </v-card>
    <v-card v-else
        max-width="100%"
        class="mx-auto"
        style="margin-bottom:15px"
    >
        <v-list-item>
          {{item.date}}<br>
        <v-list-item-avatar><img src="../../assets/images/profile_default.png"></v-list-item-avatar>
        <v-list-item-content style="padding-left:5%">
            <v-list-item-title class="headline">{{item.title}}</v-list-item-title>
            <v-list-item-subtitle>{{nickname}}   </v-list-item-subtitle>
        </v-list-item-content>
        </v-list-item>
        <v-col cols="12" sm="3">
            <div v-for="star in item.count_star" :key="star.num">
                <v-icon style="color:red; float : left">mdi-star</v-icon>
            </div>
            <div v-for="star in (5-item.count_star)" :key="star.num">
                <v-icon style="float : left">mdi-star</v-icon>
            </div>
          </v-col>
          <br>
        <v-card-text>
        {{item.content}}
        <br><br><hr><br>
        주소 : {{item.address}}
        </v-card-text>
        <hr>
        <v-spacer></v-spacer>
                <div style="width:100%" v-if="item.islike===1">
                    <button @click="toggle(item.num)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/likefill.png"></button>
                    <button @click="togglecomment"><img style="width:26px; margin-left:10px; margin-bottom:5px" src="../../assets/images/comment.png"></button><br>
                    <p v-if="item.count_like === 1">
                        {{nickname}} 님이 좋아합니다
                    </p>
                    <p v-else>
                    {{nickname}}  외  {{ item.count_like - 1 }} 명이 좋아합니다
                    </p>
  
                </div>
                <div style="width:100%;" v-else>
                    <button @click="toggle(item.num)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                    <button @click="togglecomment"><img style="width:26px; margin-left:10px; margin-bottom:5px" src="../../assets/images/comment.png"></button><br>
                     {{ item.count_like  }} 명이 좋아합니다
                </div>
                <div style="margin-left:10px; margin-bottom:5px">
                </div>
                <div v-if="commenttoggle" style="margin-bottom:50px">
                    <ul>
                       <li v-for="item in comment" :key="item" style="height:20px">
                            <h5 style="float:left; margin-left:15px; margin-right:20px"> 닉네임</h5> &nbsp; <h5 style="margin-left:25px">{{ item.content }}</h5>
                            <br>
                        </li>
                    </ul>
                    <div style="width:30%; float:right; margin-right:20px; margin-top:17px">
                        <button style="height:30px;" class="check-button" @click="addcomment">댓글달기</button>
                    </div>
                    <div style="margin-left:5px; width:60%;">
                        <v-text-field style="color:blue; width:100%" label="댓글입력" v-model="newcomment" id="newcomment" hide-details="auto">
                        </v-text-field>
                        
                    </div>
                </div>
    </v-card>  
    </div> 

</div>
</div>

</template>

<script>
    import NavigationBar from '../../components/common/NavigationBar'
    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'
    import '../../assets/css/profile.scss'
    import UserApi from '../../apis/UserApi'
    import Axios from 'axios'
    import http from '../../../http-common'
    

    export default {
        name: 'App',
        props:['propsNickname'],
        components: {
            
        },
        created () {
            this.feeds = 5;
            this.nickname = this.propsNickname;
            this.getUserByNickname(this.nickname);
        },
        watch: {
        },
        methods: {
            // getComment(num) {
            //     let form = new FormData()
            //     form.append('num', num)
            //     http.get("http://192.168.31.103:8080/comment/getProfile", form)
            //     .then(Response => {
            //     })
            //     .catch(Error => {
            //         console.log(Error)
            //     })
            // },
            getFollower() {
                let form = new FormData()
                http.get("/follow/follower?email=" + this.email)
                .then(Response => {
                    this.follower = Response.data;
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getFollowing() {
                let form = new FormData()
                http.get("/follow/following?email=" + this.email)
                .then(Response => {
                this.following = Response.data;
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getUserByNickname(nick) {
                let form = new FormData()
                form.append('nickname', nick)
                http.get("/user/userinfo/{nickname}?nickname=" + nick)
                .then(Response => {
                    console.log(Response)
                    this.num = Response.data.num;
                    this.intro = Response.data.intro;
                    this.email = Response.data.email;
                    this.getPostByNum(this.num);
                    this.getFollower(this.email);
                    this.getFollowing(this.email);
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getPostByNum(num) {
                let form = new FormData()
                form.append('num', num)
                http.get("/post/post/{num}?num="+num + '&email=' + this.$store.state.userinfo.email)
                .then(Response => {
                    this.post = Response.data.object; 
                    // const time = new Date()
                    // var moment = require('moment')
                    // this.day = moment(time.getTime()).add(-this.a, "m").format("mm분전")
                    // console.log(this.post)
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            toggle(num) {
                this.like = !this.like
                let form = new FormData()
                form.append('num', num)
                form.append('email',this.$store.state.userinfo.email)
                http.post("/post/post", form)
                .then(response => {
                    
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            togglecomment() {
                this.commenttoggle = !this.commenttoggle
            },
            addcomment: function () {
                this.comment.push({
                    id: Date.now(),
                    content: this.newcomment,
                })
                this.newcomment = ''
            },
        },
        computed: {
            calculation() {
                const moment = require("moment");
                var now = moment().format('YYYY')
                return now
            }
        },

        data: () => {
            return {
                a : 20,
                day:'',
                now: '',
                date:'',
                num:0,
                email : '',
                feeds: 0,
                nickname : '',
                intro:'',
                follower:0,
                following:0,
                post : [],
                comment : [],
                commenttoggle: false,
                newcomment: "",
                like:false,
            }
        }

  }
</script>