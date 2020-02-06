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
  <div v-if="auth==1 && isfollow==0" style="margin-top:20px; text-align:center">
      비공개 계정입니다.
  </div>
  <div v-if="auth==0 || (auth==1 && isfollow==1)">
  <div v-if="!post" style="margin-top:20px; text-align:center"> 게시물이 없습니다.</div>
  <div v-for="(item,index) in post" v-bind:key="item.num">
    <v-card
        max-width="100%"
        class="mx-auto"
        style="margin-bottom:100px"
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
                <v-icon style="float : left">md                                                                                                                                                                                                                                                                                                                                                                             i-star</v-icon>
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
                    <button @click="toggledelete(item.num)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/likefill.png"></button>
                    <button @click="commentview(item.num, index)"><img style="width:26px; margin-left:100px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                    <button><img style="width:26px; margin-left:100px; margin-bottom:5px" src="../../assets/images/share.png"></button>
                    <p v-if="item.count_like === 1">
                        {{nick}} 님이 좋아합니다
                    </p>
                    <p v-else>
                    {{nick}}님 외  {{ item.count_like - 1 }} 명이 좋아합니다
                    </p>
                    <p>
                        {{ item.count_comment }} 개의 댓글이 있습니다.
                    </p>
                  <div v-for="cmt in todolist" v-bind:key="cmt.date" >
                        <div v-if="cmt[0].num==item.num">
                             <div v-for="cmts in cmt" v-bind:key="cmts.date" >
                                <h5 style="float:left; margin-left:5px; margin-right:20px"> {{ cmts.nickname }}</h5> &nbsp; <h5 style="margin-left:25px">{{ cmts.comment }}</h5>
                             </div>                
                        </div>
                  </div>
                  
                      <div style="width:30%; float:right; margin-right:5px; margin-top:17px">
                                <button style="height:30px;" class="check-button" @click="addcomment(item.num,index)">댓글달기</button>
                            </div>
                            <div style="margin-left:5px; width:60%;">
                                <v-text-field style="color:blue; width:100%" label="댓글입력" v-model="newcomment" id="newcomment" hide-details="auto">
                                </v-text-field>
                            </div>
                  
  
                </div>
                <div style="width:100%;" v-else>
                    <button @click="toggleadd(item.num)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                    <button @click="commentview(item.num, index)"><img style="width:26px; margin-left:100px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                    <button><img style="width:26px; margin-left:100px; margin-bottom:5px" src="../../assets/images/share.png"></button>
                    <p>
                        {{ item.count_like }} 명이 좋아합니다
                    </p>
                     <p>    
                        {{ item.count_comment }} 개의 댓글이 있습니다.
                    </p>
                     <div v-for="cmt in todolist" v-bind:key="cmt.date" >
                        <div v-if="cmt[0].num==item.num">
                             <div v-for="cmts in cmt" v-bind:key="cmts.date" >
                                <h5 style="float:left; margin-left:5px; margin-right:20px"> {{ cmts.nickname }}</h5> &nbsp; <h5 style="margin-left:25px">{{ cmts.comment }}</h5>
                             </div>                
                        </div>
                  </div>
                  <div v-if="commenttoggle[index]==true">
                      <div style="width:30%; float:right; margin-right:5px; margin-top:17px">
                                <button style="height:30px;" class="check-button" @click="addcomment(item.num,index)">댓글달기</button>
                            </div>
                            <div style="margin-left:5px; width:60%;">
                                <v-text-field style="color:blue; width:100%" label="댓글입력" v-model="newcomment" id="newcomment" hide-details="auto">
                                </v-text-field>
                            </div>
                  </div>
                </div>
    </v-card>  
    </div> 
    <div
    style="margin-bottom:70px"></div>
</div>
</div>
</div>

</template>

<script>
    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'
    import '../../assets/css/profile.scss'
    import {mapState} from 'vuex';
    import UserApi from '../../apis/UserApi'
    import Axios from 'axios'
    import http from '../../../http-common'
    import NavigationBar from '../../components/common/NavigationBar'
    import {fireDB} from '../../main'
    

    export default {
        name: 'App',
        props:['propsNickname'],
        components: {
           
        },
        created () {
            if(this.$store.state.userinfo!=null) {
                this.myEmail = this.$store.state.userinfo.email
                this.nick = this.$store.state.userinfo.nickName
            }
            this.feeds = 5;
            this.nickname = this.propsNickname;
            this.getUserByNickname(this.nickname);
            this.followcheck(this.nickname);
        },
        watch: {
        },
        computed : {
            ...mapState(['userinfo']),
        },
        methods: {
            addcomment(num,index) {
                let form = new FormData()
                form.append('comment', this.newcomment)
                form.append('email', this.$store.state.userinfo.email)
                form.append('num', num)
                http.post("/comment/comment", form)
                .then(response => {
                    console.log('response')
                    /////////////////////////////댓글 재등록
                    this.todolist[0].push({"nickname" : this.$store.state.userinfo.nickName,
                    "comment" : this.newcomment})
                    console.log(this.todolist)
                    this.newcomment=''
                })
            },
            setAlarm(alarm) {
                this.userAlarmCount = alarm;
            },
            updateAlarmToFirebase() {
                console.log(this.email + ":" + this.userAlarmCount)
                fireDB.collection('Alarm').doc(this.email)
                .set({
                    count : this.userAlarmCount + 1
                })
            },
            getAlarmFromFirebase() {
                let whoami = this;
                let count = 0;
                fireDB.collection('Alarm').doc(this.email).get().then(function(doc) {
                if(doc.data()==undefined) {
                    count = 0;
                } else {
                    count = doc.data().count;
                }
                whoami.setAlarm(count);
                }).catch(function(error) {
                    console.log(error)
                })
            },
            watchAlarmFromFirebase() {
                let whoami = this;
                let count=0;
                fireDB.collection('Alarm').doc(this.email).onSnapshot( {
                    includeMetadataChanges: true    
                },function(doc) {
                    if(doc.data()==undefined) {
                        count = 0;
                    } else {
                        count = doc.data().count;
                    }
                    whoami.setAlarm(count);
                })
            },
            togglecomment(num) {
                this.commenttoggle = !this.commenttoggle
                http.get("/comment/comment?num=" + num)
                .then(response => {
                    this.comment = response.data.object
                    // for(this.i=0; this.i< this.comment.length; this.i++){
                    //     if (this.comment[this.i].num==num){
                    //         alert('asd')
                    //         this.comments.push(this.comment[this.i].comment)
                    //     }
                    // }
                    
                })
                .catch(Error => {
                    console.log(Error)
                })
                alert("댓글이 등록되었습니다.")
               
            },
            
            unfollowgo(){
                let form = new FormData()
                let myn  = this.$store.state.userinfo.nickName;
                form.append('mynickname', myn)
                form.append('nickname',this.nickname)
                http.post("/follow/unFollow", form)
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
                if(this.auth==0) {
                    let form = new FormData();
                    let myn  = this.$store.state.userinfo.nickName;
                    form.append('mynickname', myn)
                    form.append('nickname',this.nickname)
                    http.post("/follow/follow", form)
                    .then(Response => {
                        this.isfollow = 1;
                        // console.log(Response.data)
                        this.getFollower();
                        this.getFollowing();
                        this.updateAlarmToFirebase();


                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                }
                else if(this.auth==1) {
                    let form = new FormData();
                    let myn  = this.$store.state.userinfo.nickName;
                    form.append('mynickname', myn)
                    form.append('nickname',this.nickname)
                    
                    http.post("/follow/nonfollow", form)
                    .then(Response => {
                        console.log(Response)
                        if(Response.data==='success') {
                            this.updateAlarmToFirebase();
                            alert("팔로우가 요청되었습니다.")
                        }
                        else if(Response.data==='failed') {
                            alert("이미 팔로우 신청을 하였습니다.")
                        }
                    })
                    .catch(Error => {
                        console.log(Error)
                    })

                }
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
           getFollower() {
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
                    console.log(Response)
                    this.num = Response.data.num;
                    this.intro = Response.data.intro;
                    this.email = Response.data.email;
                    this.auth = Response.data.auth;
                    this.getFollowing(this.email);
                    this.getFollower(this.email);
                    this.getPostByNum(this.num);
                    this.getAlarmFromFirebase();
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
                    for (let index = 0; index < this.post.length; index++) {
                        this.state.push(false);
                        this.commenttoggle.push(false)
                    }
                   console.log(this.state)
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            toggleadd(num) {
                this.like = !this.like
                let form = new FormData()
                form.append('postnum', num)
                form.append('email', this.$store.state.userinfo.email)
                http.post('/postlike/like',form)
                .then(response => {
                  
                    console.log(response.data.object);
                    this.post = response.data.object; 
                })
                .catch(Error => {
                    console.log(Error)
                })
            },

            toggledelete(num) {
                http.delete("/postlike/unlike?postnum="+num + '&email=' + this.$store.state.userinfo.email)
                .then(response => {
                    this.post = response.data.object
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            commentview(num,index){
               
                if(this.commenttoggle[index]==false){
                    this.commenttoggle[index] = true;
                }else{
                    this.commenttoggle[index] = false;
                }

  

                if(this.state[index]==false){
                    http.get('/comment/comment?num='+num)
                .then(response => {
                    if(response.data.object!=null){
                    this.todolist.push(response.data.object)
                    console.log(this.todolist)
                    this.state[index] = true;
                    }
                
                    
                })
                .catch(Error => {
                    console.log(Error)
                })

                }else if(this.state[index]==true){

                    for (let index = 0; index < this.todolist.length; index++) {
                        if(this.todolist[index][0].num==num){
                            this.$delete(this.todolist,index);
                        }
                        
                    }
                    console.log("삭제")
                    console.log(this.todolist)
                    this.state[index] = false;
                }
                
                
             }
           
        },
        data: () => {
            return {
                nick:'',
                num:0,
                email:'',
                myEmail:'',
                feeds: 0,
                nickname : '',
                intro:'',
                follower:0,
                following:0,
                post : [],
                commentNum:'',
                newcomment: "",
                like:true,
                isfollow:0,
                auth:1,
                userAlarmCount: 0,
                todolist:[],
                state:[],
                commenttoggle:[],
           

            }
        }

    }
</script>
<style>
p {

    margin-left:5px;
    color: gray;
    font-size:12px;
}
</style>