<template>
<div class="wrapC">
<div class="wrapper">
  <div class="profile-card js-profile-card">
    <div class="profile-card__cnt js-profile-cnt">
        <img src="../../assets/images/profile_default.png" style="margin-bottom:10px;">
      <div class="profile-card__name">{{nickname}}</div>
      <div class="profile-card__txt">{{intro}}</div>
 
        
        <div class="profile-card-ctr" v-if="isfollow==0">
          
            <button class="profile-card__button button--orange" @click="followgo()">Follow</button>
        </div>
        <div class="profile-card-ctr" v-if="isfollow==1">
          
            <button class="profile-card__button button--gray" @click="unfollowgo()">UnFollow</button>
        </div>

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
  <div v-for="item in post" v-bind:key="item.num">
    <v-card
        max-width="100%"
        class="mx-auto"
        style="margin-bottom:15px"
    >
        <v-list-item>
        <v-list-item-avatar><img src="../../assets/images/profile_default.png"></v-list-item-avatar>
        <v-list-item-content style="padding-left:5%">
            <v-list-item-title class="headline">{{item.title}}</v-list-item-title>
            <v-list-item-subtitle>{{nickname}}   </v-list-item-subtitle>
        </v-list-item-content>
        </v-list-item>
        <div style="margin-left:16px">{{item.date}}</div>
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
                <div style="width:100%;">
                    <button @click="toggle"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                    <button @click="getComment(item.num)"><img style="width:26px; margin-left:10px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                </div>
                <div style="margin-left:10px; margin-bottom:5px">
                    {{ item.count_like }} 명이 좋아합니다
                </div>
                <div v-if="commentNum==item.num">
                    <ul>
                        <li v-for="item in comment" :key="item">
                            <h5 style=" margin-left:15px;"> 닉네임</h5> <h5 style="margin-left:25px">{{ item.comment }}</h5>
                            <br>
                        </li>
                    </ul>
                    <div>
                        <div style="margin-left:5px; width:60%;">
                            <v-text-field style="color:blue; width:70%" label="댓글입력" v-model="newcomment" id="newcomment" hide-details="auto">
                            </v-text-field>
                            
                        </div>
                        <div style="width:30%;">
                            <button style="height:30px" class="check-button" @click="addcomment">댓글달기</button>
                        </div>
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
            this.followcheck(this.nickname);
        },
        watch: {
        },
        methods: {
            unfollowgo(){
                let form = new FormData()
                let myn  = this.$store.state.userinfo.nickName;
                form.append('mynickname', myn)
                form.append('nickname',this.nickname)
                Axios.post("http://192.168.31.103:8080/follow/unFollow", form)
                .then(Response => {
                    this.isfollow = 0;
                    // console.log(Response.data)
                    this.getFollower();
                    this.getFollowing();
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            followgo(){
                let myn  = this.$store.state.userinfo.nickName;
                http.get("/follow/follow?mynickname=" + myn + "&nickname=" + this.nickname)
                .then(Response => {
                    this.isfollow = 1;
                    // console.log(Response.data)
                    this.getFollower();
                    this.getFollowing();
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            followcheck(nick) {
                let myn  = this.$store.state.userinfo.nickName;
 
                http.get("/follow/follow?mynickname=" + myn + "&nickname=" + nick)
                .then(Response => {
                    if(Response.data==0){
                       
                        this.isfollow = 0;
                    }else if(Response.data==1){
                       
                        this.isfollow = 1;
                        
                    }else{
                         this.isfollow = -1;
                    }
                    // console.log("check: " + Response.data)
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getComment(num) {
                http.get("/comment/comment?num=" + num)
                .then(Response => {
                    this.commentNum = Response.data.object[0].num;
                })
                .catch(Error => {
                    console.log(Error)
                })
            },getFollower() {
                let form = new FormData()
                http.get("/follow/follower?email="+this.email)
                .then(Response => {
                this.follower = Response.data;
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getFollowing() {
                let form = new FormData()
                http.get("/follow/following?email="+this.email)
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
                http.get("/user/userinfo/{nickname}?nickname="+nick)
                .then(Response => {
                    this.num = Response.data.num;
                    this.intro = Response.data.intro;
                    this.email = Response.data.email;
                    this.getPostByNum(this.num);
                    this.getFollowing(this.email);
                    this.getFollower(this.email);
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getPostByNum(num) {
                let form = new FormData()
                form.append('num', num)
                http.get("/post/post/{num}?num="+num)
                .then(Response => {
                    this.post = Response.data.object; 
                    // console.log(this.post)
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            toggle () {
                this.like = !this.like
            },
            addcomment: function () {
                this.comment.push({
                    id: Date.now(),
                    content: this.newcomment,
                })
                this.newcomment = ''
            },
        },
        data: () => {
            return {
                num:0,
                email:'',
                feeds: 0,
                nickname : '',
                intro:'',
                follower:0,
                following:0,
                post : [],
                comment : [],
                commentNum:0,
                newcomment: "",
                like:true,
                isfollow:0,
            }
        }

    }
</script>