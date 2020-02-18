<template>
  <v-navigation-drawer v-model="propDrawer" temporary>
      <v-list-item>
        <v-list-item-avatar style="margin-top:20%; margin-left:41%">
          <div v-if="!this.picture">
            <v-btn @click="fileInputClick" fab x-large dark style="background-color:#fb8c00">
            <v-icon>mdi-account-circle</v-icon>
            </v-btn>
        </div>
        <div v-else>
             <v-img @click="fileInputClick"
             style="width:60px;height:60px;border-radius:50%;"
             :src="this.picture"></v-img>
        </div>
        </v-list-item-avatar>
      </v-list-item>
      <div style="padding-top:10px; text-align:center">
        <p>{{userinfo.nickName}}</p>
        <p>{{userinfo.email}}</p>
      </div>
      <div style="text-align:center; padding-top:20px">
        <div style="float:left; width:50%" @click="goFollow">팔로워</div>
        <div @click="goFollowing">팔로잉</div>
      </div>
      <div style="text-align:center; padding-top:10px; padding-bottom:20px">
        <div style="float:left; width:50%" @click="goFollow">{{this.follower}}</div>
        <div @click="goFollowing">{{this.following}}</div>
      </div>
       <v-divider style="margin-left:10%; width:80%"></v-divider>
      <v-list dense>
        <div style="padding-top:3%; padding-bottom:3%; padding-left:10%" @click="LikeFeed">
          내가 좋아한 피드
        </div>
        <div style="padding-top:3%; padding-bottom:3%; padding-left:10%" @click="ScrapFeed">
          내가 스크랩 한 피드
        </div>
        <div style="padding-top:3%; padding-bottom:3%; padding-left:10%" @click="CurationFeed">
          나의 큐레이션 피드
        </div>
      </v-list>
      <v-divider style="margin-left:10%; width:80%"></v-divider>
      <v-list dense>
        <div style="padding-top:3%; padding-bottom:3%; padding-left:10%" @click="modifyProfile">
          회원정보 수정
        </div>
        <div style="padding-top:3%; padding-bottom:3%; padding-left:10%" @click="modifyPassword">
          비밀번호 설정
        </div>
        <div style="padding-top:3%; padding-bottom:3%; padding-left:10%" @click="Address">
          뉴스피드 설정
        </div>
      </v-list>
      <v-divider style="margin-left:10%; width:80%"></v-divider>
     
      <v-divider style="margin-left:10%; width:80%"></v-divider>
      <v-list dense>
        <div style="padding-top:5%; padding-bottom:5%; padding-left:10%" @click="statistics">
          통계
        </div>
      </v-list>
      <v-divider style="margin-left:10%; width:80%"></v-divider>
      <v-list dense>
        <div style="padding-top:5%; padding-bottom:5%; padding-left:10%" @click="$store.dispatch('logout')">
          로그아웃
        </div>
      </v-list>
      <v-row justify="center">
<v-dialog
  v-model="dialog"
  max-width="290"
  style="position:relative; z-index:1006;"
>
<v-card>
<v-card-title>
<v-file-input 
v-model="chosenFile"
@change="updatePicture($event)"
label="File input"
></v-file-input>
</v-card-title>
<v-card-actions>
          <v-btn btn btn--ok color="green darken-1" text @click="submit" :disabled="dialogResult===false" :class="{disabled : !dialog}">Agree</v-btn>
          <v-btn color="green darken-1" text @click="dialog = false">Disagree</v-btn>
          <v-btn color="green darken-1" text @click="deletePicture">삭제</v-btn>
        
</v-card-actions>
</v-card>
</v-dialog>
  </v-row>
    </v-navigation-drawer>
  
</template>
<script>
import {mapState} from 'vuex';
import Axios from 'axios';
import http from '../../../http-common'
  export default {
    props : ['drawer'],
    created() {
       if(this.$store.state.userinfo!=null) {
                this.nick = this.$store.state.userinfo.nickName
                this.email = this.$store.state.userinfo.email;
                http.get("/user/userinfo/{nickname}?nickname="+this.nick)
                .then(Response => {
                    this.mynum = Response.data.num;
                })
                .catch(Error => {
                    console.log(Error)
                })
            
            }
      this.getProfile(this.nick);
      this.getFollowing();
      this.getFollower();
    },
    data () {
      return {
        propDrawer : this.drawer,
        nickName : '',
        email : '',
        follower : 0,
        following : 0,
        nick : '',
        picture: '',
        dialog : false,
        dialogResult : false,
        chosenFile:null,
        mynum: 0,
      }
    },
    watch : {
      propDrawer : function(v) {
        this.updateParentNavi();
      }
    },
    methods : {
      LikeFeed() {
        this.$router.push({name:"LikeFeed"})
      },
      ScrapFeed() {
        this.$router.push({name:"ScrapFeed"})
      },
      CurationFeed() {
        this.$router.push({name:"CurationFeed"})
      },
      
      Address() {
        this.$router.push({name:"Address"})
      },
      statistics() {
        this.$router.push({name : "Statistics"})
      },
      updateParentNavi() {
        this.$emit('child', this.propDrawer)
      },  
      modifyPassword() {
        var router = this.$router;
          router.push({
              name: "ModifyPassword"
          });
      },
      modifyProfile() {
        this.$router.push({
          name:"ModifyProfile"
        })
      },
      getFollower() {
        let form = new FormData()
        form.append('email', this.email)
        http.get("/follow/follower?email="+this.email)
        .then(Response => {
          // console.log(Response)
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
          // console.log(Response)
          this.following = Response.data;
        })
        .catch(Error => {
            console.log(Error)
        })
      },
      goFollow(){
        this.$router.push(
          {name:"FollowPage"}
        )
        
      },
      goFollowing(){
        this.$router.push(
          {name:"FollowingPage"}
        )
      },
      updatePicture(event){
        this.dialog = event
        let formdata = new FormData
        formdata.append('image', this.dialog)
        Axios.post('https://api.imgur.com/3/image',formdata, {headers:{Authorization: 'Client-ID d15c5b033075c6e'}})
        .then(Response => {
            this.dialogResult = Response.data.data.link
            
        })
        .catch(Error =>{
        })
      },
      submit(){
          this.dialog = false
          let form = new FormData()
          form.append('num', this.mynum)
          form.append('picture', this.dialogResult)
          http.post('profile/insertPicture', form)
          .then(Response => {
              if(Response.data.data==='success'){
                  this.getProfile(this.nick)
              }
              
          })
      },
      fileInputClick(){
          this.dialog = true;
          this.chosenFile = null;
          this.dialogResult = false;
      },
      deletePicture(){
          let num = this.mynum
          http.get("profile/deletePicture/?num=" + num)
          .then(Reponse =>{
              console.log(Response)
              this.picture = '';
              this.dialog = false
          })
          .catch(Error => {
              console.log(Error)
          })
          
      },
      getProfile(nick){
          http.get("/profile/profile/?nickname=" + nick)
          .then(Response => {
              this.picture = Response.data.picture;
              console.log(this.picture)
          })
          .catch(Error => {
              console.log(Error)
          })
      },
    },
    computed : {
      ...mapState(['userinfo']),
    }
  }
</script>