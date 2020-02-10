<template>
    <div class="wrapC">
        <div class="wrapper">
            <div class="header" style="width:100%; height:40px">
                <div style="float:left;">
                <button v-on:click="goBack">
                    <img src="../../assets/images/backIcon.png" style="width:35px;">
                </button>
                </div>
                <p style="vertical-align: middle;padding: 8px 5px;float:left;">내가 스크랩 한 피드</p>
            </div>
                <div v-if="!post" style="margin-top:20px; text-align:center"> 게시물이 없습니다.</div>
                  <div style="margin-top:50px" v-for="(item,index) in post" v-bind:key="item.num">
                    <v-card
                            max-width="100%"
                            class="mx-auto"
                            style="margin-bottom:100px"
                    >
                    <v-list-item>
                        <v-list-item-avatar><img src="../../assets/images/profile_default.png"></v-list-item-avatar>
                        <v-list-item-content style="padding-left:5%">
                        <v-list-item-title class="headline">{{item.title}}</v-list-item-title>
                        <v-list-item-subtitle style="width:50px">{{item.nickname}} <div style="float:right"> {{getTime(item.date)}}</div> </v-list-item-subtitle>
                        <!-- <v-list-item-subtitle>{{getTime(item.date)}}</v-list-item-subtitle> -->
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
                                <img v-bind:src="item.image"  style="width:100%; heigh:auto; ">
                            <br>
                            <br><br><hr><br>
                            주소 : {{item.address}} 
                            </v-card-text>
                            <hr>
                        <v-spacer></v-spacer>

                    <div style="width:100%">
                    <div style="margin-bottom:10px; margin-top:10px; padding-left:5px">
                        <div style="width:33%; float:left;">

                        <button class="animated rubberBand" v-if="like[index]===true" @click="toggledelete(item.num, index)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/likefill.png"></button>
                        <button v-if="like[index]===false" @click="toggleadd(item.num, index)"><img class="animated rubberBand" style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                        </div>
                        <div style="width:33%; float:left; text-align:center; margin-top:3px">

                        <button @click="commentview(item.num, index)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                        </div>
                        <div style="width:33%; float:left; text-align:right; padding-right:10px; ; margin-top:3px">
                        <button><img style="width:26px; margin-bottom:5px" src="../../assets/images/share.png"></button>

                        </div>
                        <br>
                    </div>
                    <br>
                  
                        <div v-if="like[index]===true">
                            <p v-if="likelist[index] === 1">
                                {{nick}}님<span>이 좋아합니다.</span>
                            </p>
                            <p v-else>
                                {{nick}}님 외  {{ likelist[index] - 1 }} 명이 좋아합니다
                            </p>
                        </div>

                        <div v-if="like[index]===false">
                            <p>
                                {{ likelist[index] }} 명이 좋아합니다
                            </p>
                        </div>

                        <p>
                            {{ item.count_comment }} 개의 댓글이 있습니다.
                        </p>

                        <div v-if="coment[index]===true">
                            <div v-for="cmt in todolist" v-bind:key="cmt.date" >
                                <div v-if="cmt[0].num==item.num">
                                    <div style="margin-bottom:5px" v-for="cmts in cmt" v-bind:key="cmts.date" >
                                        <h5 style="float:left; margin-left:5px; margin-right:20px; font-weight:bold;"> {{ cmts.nickname }}</h5> &nbsp; 
                                        <h5 style="float:left; ">{{ cmts.comment }} 
                                        </h5>
                                        <span style="float:right; margin-right:20px; font-weight:lighter; color:red" v-if="cmts.author==mynum || item.author == mynum" @click="removeComent(item.num,cmts,index)">X</span>
                                        <br>
                                    </div>                
                                </div>
                            </div>
                  
                            <div style="width:30%; float:right; margin-right:5px; margin-top:17px">
                                <button style="height:30px;" class="comment-ok" @click="addcomment(item.num,index)"
                                :disabled="!isSubmit"
                                :class="{disabled : !isSubmit}"
                                >댓글달기</button>
                            </div>
                            <div style="margin-left:5px; width:60%;">
                                <v-text-field style="color:blue; width:90%" label="댓글입력" v-model="newcomment" id="newcomment" hide-details="auto">
                                </v-text-field>
                            </div>
                        </div>
                    </div>
                    
                    </v-card>
                </div>
            
        </div>
    </div>
</template>


<script>
    import Vue from 'vue'
    import moment from 'moment'
    import VueMomentJS from 'vue-momentjs'
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
        created () {
            this.getPostByNum()

        },
        watch : {
            newcomment: function(v) {
                this.checkForm();
            }
        },
        computed : {
            ...mapState(['userinfo']),
        },
        methods: {
            goBack() {
                var router = this.$router;
                router.go(-1)
            },
            checkForm() {
                if (this.newcomment.length < 1)
                    this.error.newcomment = '1자이상'
                else
                    this.error.newcomment = false
                
                let isSubmit = true
                Object
                .values(this.error)
                .map(v => {
                    if (v) 
                        isSubmit = false;
                    }
                )
                this.isSubmit = isSubmit;
            },
            getTime(time) {
                return moment(time).fromNow();
            },
    
            getPostByNum() { //포스트가져오기
                this.nickname = this.$store.state.userinfo.nickName
                this.nick = this.$store.state.userinfo.nickName
                http.get("/post/myhscrappost?nickname="+ this.$store.state.userinfo.nickName)
                .then(Response => {
                
                    this.post = Response.data.object; 
                    
                    //좋아요와 댓글 토글용 배열 생성
                    for (let index = 0; index < this.post.length; index++) {
                        if(this.post[index].islike==1){
                            this.like.push(true)
                        }else{
                            this.like.push(false)
                        }
                        this.likelist.push(this.post[index].count_like);
                        this.coment.push(false)
                        
                    } 
                })
                .catch(Error => {
                    console.log(Error)
                })
            },

            toggleadd(num, index) { //좋아요를 클릭할때
               this.likelist[index]++;
               this.$set(this.like,index,!this.like[index])
                
                //좋아요 서버로 전송하기
                let form = new FormData()
                form.append('postnum', num)
                form.append('email', this.$store.state.userinfo.email)

                http.post('/postlike/like',form)
                .then(response => {
                     
                })  
                .catch(Error => {
                     console.log(Error)
                })
            },
            toggledelete(num, index) { //좋아요를 해제할때
                this.likelist[index]--;
                this.$set(this.like,index,!this.like[index])

                http.delete("/postlike/unlike?postnum="+num + '&email=' + this.$store.state.userinfo.email)
                .then(response => {
                     
                })
                .catch(Error => {
                     console.log(Error)
                })
            },
            commentview(num,index){ //댓글 버튼 누를 때
            this.todolist = [];
            //댓글 불러오기
            if(this.coment[index]==false){
                    http.get('/comment/comment?num='+num)
                .then(response => {
                    if(response.data.object!=null){
                        this.todolist.push(response.data.object)
                    } 
                })
                .catch(Error => {
                    console.log(Error)
                })

            //댓글 숨기기
            }else if(this.coment[index]==true){
                    for (let index = 0; index < this.todolist.length; index++) {
                        if(this.todolist[index][0].num==num){
                            this.$delete(this.todolist,index);
                        }   
                    }
                }

                this.$set(this.coment,index,!this.coment[index])
            }
            ,
            addcomment(num,index) {
                
                let form = new FormData()
                form.append('comment', this.newcomment)
                form.append('email', this.$store.state.userinfo.email)
                form.append('num', num)
                http.post("/comment/comment", form)
                .then(response => {
                   
                    //댓글 재등록
                    this.commentview(num, index);
                    this.commentview(num, index);
                   
                    //댓글 초기화
                    this.newcomment=''

                    //댓글 수 갱신하기
                    this.refresh();

                })

            },
            refresh(){ //댓글 수 갱신하기

                http.get("/post/post/{num}?num="+this.num + '&email=' + this.$store.state.userinfo.email)
                .then(Response => {

                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            removeComent(num, cmt, index){
                
                http.delete("/comment/comment?num=" + cmt.num + "&nickname=" + cmt.nickname + "&date=" + cmt.date)
                .then(response => {

                    //삭제 완료시 댓글창, 댓글수 바로 갱신
     
                    this.commentview(num, index);
                    this.commentview(num, index);
                

                    this.refresh();
                    
                })
                .catch(Error =>{
                })
            },

            //밑은 알람 메소드
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
               
            }
           
        },
        data: () => {
            return {
                isSubmit: false,
                error:{
                    comment:false
                },
                nick:'',
                nickname : '',
                num:0,
                intro:'',
                auth:1,
                email:'', 
                like:[],
                coment:[],
                todolist:[],
                newcomment: "",
                myEmail:'',
                feeds: 0,
                post : [],
                commentNum:'',
                userAlarmCount: 0,
                likelist:[],
                mynum:0,
                commentcount:0
                
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