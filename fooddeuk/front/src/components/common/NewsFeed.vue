<template>
<v-app data-app>
    <div class="wrapC">
        <div class="wrapper">
                <div v-if="!post" style="margin-top:20px; text-align:center"> 게시물이 없습니다.</div>
                <div v-else>
                <div v-for="(item,index) in list" v-bind:key="item.num">
                <div style="margin-top:80px">
                </div>
                <div v-if="item.type==='스크랩'">
                    <v-card
                            max-width="100%"
                            class="mx-auto"
                            style="margin-bottom:30px; position:relative"
                    >
                    <v-list-item>
                        <v-list-item-avatar style="height:50px; width:50px"><img src="../../assets/images/profile_default.png"></v-list-item-avatar>
                        <v-list-item-content style="padding-left:5%">
                        <v-list-item-title style="margin-left:5px; margin-top:5px; font-size:15px;">{{item.title}}
                            <div v-if="item.author === mynum">
                            <v-menu offset-y style="float:right;">
                            <template v-slot:activator="{ on }">
                                <v-btn icon v-on="on" style="float:right">
                                    <v-icon>mdi-dots-vertical</v-icon>
                                </v-btn>
                            </template>
                            <v-list>
                                <v-list-item>
                                    <button style="float:right" @click="updateFeed(item.num,item.title,item.content,item.count_star,item.address,item.image)">수정</button>
                                </v-list-item>
                                <v-list-item>
                                    <button style="float:right" @click="removeFeed(item.num)">삭제</button>
                                </v-list-item>
                            </v-list>
                            </v-menu>
                            </div>
                        </v-list-item-title>

                        <v-list-item-subtitle style="width:50px; margin-left:5px">{{item.nickname}} <br>
                         <div style="margin-top:10px; margin-left:2px"> {{getTime(item.date)}}</div> </v-list-item-subtitle>
                        <!-- <v-list-item-subtitle>{{getTime(item.date)}}</v-list-item-subtitle> -->
                        </v-list-item-content>
                        </v-list-item>
                        
                           <div style="margin-left:20px; margin-bottom:20px">
                                {{ item.content }}<br>
                            </div>
                        <p style="text-align:center">
                            <v-card style="margin-left:13px; width:90%; height:100%; text-align:center">
                                <v-img 
                                v-if="item.image!==null"
                                style="width:100%; height:200px;"
                                :src="item.image"
                                class="white--text align-end"
                                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                >
                                <v-card-title v-text="item.scraptitle"></v-card-title>
                                </v-img>
                                <v-img 
                                v-if="item.image==null"
                                style="width:100%; height:200px"
                                src="../../assets/images/noimage.png"  
                                class="white--text align-end"
                                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                                >
                                </v-img>


                                <v-card-actions>
                                <v-spacer></v-spacer>
                                </v-card-actions>
                            </v-card>
                        </p>
                        <v-spacer></v-spacer>


                    <div style="width:100%">
                    <div style="margin-bottom:10px; margin-top:15px; padding-left:5px">
                        <div style="width:33%; float:left;">

                        <button class="animated rubberBand" v-if="like[index]===true" @click="toggledelete(item.num, index)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/likefill.png"></button>
                        <button v-if="like[index]===false" @click="toggleadd(item.num, index)"><img class="animated rubberBand" style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                        </div>
                        <div style="width:33%; float:left; text-align:center; margin-top:3px">

                        <button @click="commentview(item.num, index)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                        </div>

                        <div style="width:33%; float:left; text-align:right; padding-right:10px; ; margin-top:3px">
                        <button @click="noscrap"><img style="width:26px; margin-bottom:5px" src="../../assets/images/share.png"></button>
                        </div>
                        <br>
                    </div>
                    <br>
                  
                        <div v-if="like[index]===true">
                            <p v-if="likelist[index] === 1">
                                {{nickname}}님<span>이 좋아합니다.</span>
                            </p>
                            <p v-else>
                                {{nickname}}님 외  {{ likelist[index] - 1 }} 명이 좋아합니다
                            </p>
                        </div>

                        <div v-if="like[index]===false">
                            <p>
                                {{ likelist[index] }} 명이 좋아합니다
                            </p>
                        </div>

                        <p>
                            {{ commentcount[index] }} 개의 댓글이 있습니다.
                        </p>

                        <div v-if="coment[index]===true">
                            <div v-for="cmt in todolist[index]" v-bind:key="cmt.id" >        
                                <div style="margin-bottom:1px" v-for="cmts in cmt" v-bind:key="cmts.id" >
                                    <h5 style="float:left; margin-left:5px; margin-right:20px; font-weight:bold;"> {{ cmts.nickname }}</h5> &nbsp; 
                                    <h5 style="float:left; ">{{ cmts.comment }} 
                                    </h5>
                                    <span style="float:right; margin-right:20px; font-weight:lighter; color:red" v-if="cmts.author==mynum || item.author == mynum" @click="removeComent(item.num,cmts,index)">X</span>
                                    <br>                 
                                </div>
                            </div>
                  
                            <div style="width:30%; float:right; margin-right:5px; margin-top:17px">
                                <button style="height:30px;" class="comment-ok" @click="addcomment(item.num,index)"
                                :disabled="!isSubmit"
                                :class="{disabled : !isSubmit}"
                                >게시</button>
                            </div>
                            <div style="margin-left:5px; width:60%;">
                                <v-text-field style="color:blue; width:90%" label="댓글입력" v-model="newcomment" id="newcomment" hide-details="auto">
                                </v-text-field>
                            </div>
                        </div>
                    </div>
                    </v-card>

                </div>
                                <div v-if="item.type==='일반'">
                    <v-card
                            max-width="100%"
                            class="mx-auto"
                            style="margin-bottom:100px; position:relative"
                    >
                    <v-list-item>
                        <v-list-item-avatar style="height:50px; width:50px"><img src="../../assets/images/profile_default.png"></v-list-item-avatar>
                        <v-list-item-content style="padding-left:5%">
                        <v-list-item-title style="margin-left:5px; margin-top:5px; font-size:15px;">{{item.title}}
                            <div v-if="item.author === mynum">
                            <v-menu offset-y style="float:right;">
                            <template v-slot:activator="{ on }">
                                <v-btn icon v-on="on" style="float:right">
                                    <v-icon>mdi-dots-vertical</v-icon>
                                </v-btn>
                            </template>
                            <v-list>
                                <v-list-item>
                                    <button style="float:right" @click="updateFeed(item.num,item.title,item.content,item.count_star,item.address,item.image)">수정</button>
                                </v-list-item>
                                <v-list-item>
                                    <button style="float:right" @click="removeFeed(item.num)">삭제</button>
                                </v-list-item>
                            </v-list>
                            </v-menu>
                            </div>
                        </v-list-item-title>

                        <v-list-item-subtitle style="width:50px; margin-left:5px">{{item.nickname}} <br>

                         <div style="margin-top:10px; margin-left:2px"> {{getTime(item.date)}}</div> </v-list-item-subtitle>
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
                                
                                <img v-if="item.image!=='null' || item.image!==null" v-bind:src="item.image"  style="width:100%; heigh:auto; ">
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
                         <v-row style="backgroud:white; float:right; margin-right:2px;" justify="center">
                            <v-dialog v-model="dialog" persistent max-width="290" :retain-focus="false">
                            <template v-slot:activator="{ on }">
                                <v-btn depressed color="white" v-on="on" @click="modal(item.num)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/share.png"></v-btn>
                            </template>
                            <v-card>
                                <v-card-title class="headline">{{nickname}}님의 게시물</v-card-title>
                                 <v-text-field style="color:blue; width:90%; margin-left:10px" label="제목입력" v-model="scraptitle" id="scraptitle" hide-details="auto"></v-text-field>
                                <v-text-field style="color:blue; width:90%; margin-left:10px" label="내용입력" v-model="scrapcontent" id="scrapcontent" hide-details="auto"></v-text-field>
                                <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="green darken-1" text @click="dialog = false">취소</v-btn>
                                <v-btn color="green darken-1" text @click="scrapfeed(modalnum, scraptitle, scrapcontent);">스크랩</v-btn>
                                </v-card-actions>
                            </v-card>
                            </v-dialog>
                        </v-row>
                        <br>
                    </div>
                    <br>
                  
                        <div v-if="like[index]===true">
                            <p v-if="likelist[index] === 1">
                                {{nickname}}님<span>이 좋아합니다.</span>
                            </p>
                            <p v-else>
                                {{nickname}}님 외  {{ likelist[index] - 1 }} 명이 좋아합니다
                            </p>
                        </div>

                        <div v-if="like[index]===false">
                            <p>
                                {{ likelist[index] }} 명이 좋아합니다
                            </p>
                        </div>

                        <p style="margin-bottom:3px">
                            {{ commentcount[index] }} 개의 댓글이 있습니다.
                        </p>

                        <div v-if="coment[index]===true">
                            <div v-for="cmt in todolist[index]" v-bind:key="cmt.id" >        
                                <div style="margin-bottom:1px" v-for="cmts in cmt" v-bind:key="cmts.id" >
                                    <h5 style="float:left; margin-left:5px; margin-right:20px; font-weight:bold;"> {{ cmts.nickname }}</h5> &nbsp; 
                                    <h5 style="float:left; ">{{ cmts.comment }} 
                                    </h5>
                                    <span style="float:right; margin-right:20px; font-weight:lighter; color:red" v-if="cmts.author==mynum || item.author == mynum" @click="removeComent(item.num,cmts,index)">X</span>
                                    <br>                 
                                </div>
                            </div>
                  
                            <div style="width:30%; float:right; margin-right:5px; margin-top:17px">
                                <button style="height:30px;" class="comment-ok" @click="addcomment(item.num,index)"
                                :disabled="!isSubmit"
                                :class="{disabled : !isSubmit}"
                                >게시</button>
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
             <infinite-loading style="margin-top:30%" @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
            </div>
        </div>  
        <div style="margin-bottom:30px">
    </div>
    </div> 
    </v-app>
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
    import InfiniteLoading from 'vue-infinite-loading';


    export default {
        name: 'App',
        props:['propsNickname'],
        created () {
            setTimeout(() => {
                if(this.$store.state.userinfo!=null) {
                    this.myEmail = this.$store.state.userinfo.email
                    this.nickname = this.$store.state.userinfo.nickName
                }
                //포스트 불러오기
            }, 200);
            this.mynum = this.$store.state.userinfo.num
        },
        mounted() {
            this.getUserByNickname(this.nickname);
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
                moment.locale('ko')
                return moment(time).fromNow();
            },
             getUserByNickname(nick) {
                let form = new FormData()
                form.append('nickname', nick)
                http.get("/user/userinfo/{nickname}?nickname="+nick)
                .then(Response => {
                    this.num = Response.data.num;
                    this.intro = Response.data.intro;
                    this.email = Response.data.email;
                    this.auth = Response.data.auth;
                    //검색한 사용자의 피드 다불러오기
                    this.getPostByNum(this.num);
                    //this.getAlarmFromFirebase();
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getPostByNum(num) { //포스트가져오기
               
                http.get("/post/main?nickname="+this.$store.state.userinfo.nickName)
                .then(Response => {
                    this.post = Response.data.object;
                    console.log(this.post)
                    //좋아요와 댓글 토글용 배열 생성
                    for (let index = 0; index < this.post.length; index++) {
                     
                        if(this.post[index].islike==1){
                            this.like.push(true)
                        }else{
                            this.like.push(false)
                        }
                        this.likelist.push(this.post[index].count_like);
                        this.coment.push(false)

                        this.todolist.push([])
                        this.commentcount.push(this.post[index].count_comment)
                    }
                     if(this.post.length!=0){
                            this.infiniteHandler(this.state);
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
                     this.post = response.data.object; 
                     
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
                     this.post = response.data.object
                     
                })
                .catch(Error => {
                     console.log(Error)
                })
            },
            commentview(num,index){ //댓글 버튼 누를 때
            //댓글 불러오기
            if(this.coment[index]==false){
                    http.get('/comment/comment?postnum='+num)
                .then(response => {
                    if(response.data.object!=null){
                        this.todolist[index].push(response.data.object)
                    } 
                
                   
                })
                .catch(Error => {
                    console.log(Error)
                })

            // //댓글 숨기기
             }
            else if(this.coment[index]==true){
                 
                    this.$delete(this.todolist[index],0);
                }
                this.$set(this.coment,index,!this.coment[index])

                //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                        
                        this.$set(this.commentcount,index,Response.data)
                        
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                    
            }
            ,
            addcomment(num,index) {
                let form = new FormData()
                form.append('comment', this.newcomment)
                form.append('email', this.$store.state.userinfo.email)
                form.append('postnum', num)
                http.post("/comment/comment", form)
                .then(response => {
                   
                    //댓글 등록
                    this.$delete(this.todolist[index],0);
                    this.todolist[index].push(response.data.object)

                    //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                    
                        this.$set(this.commentcount,index,Response.data)
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                    
                    //댓글 초기화
                    this.newcomment=''
                })
                
            },
            removeComent(num, cmt, index){
  
                http.delete("/comment/comment?postnum=" + num + "&num="+ cmt.num + "&nickname=" + cmt.nickname + "&date=" + cmt.date)
                .then(response => {
                    //댓글 삭제(갱신까지)
                  
                    this.$delete(this.todolist[index],0);
                    this.todolist[index].push(response.data.object)
                    
                    //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                        
                        this.$set(this.commentcount,index,Response.data)
                        
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                })
                .catch(Error =>{
                })
 
                
            },
            
            removeFeed(num){
                 if (confirm("정말 삭제하시겠습니까??") == true){    //확인
                    http.delete("/post/post?num=" + num + "&mynum=" + this.$store.state.userinfo.num)
                    .then(response => {
                        alert('게시물이 삭제되었습니다.')
              
                        this.post = response.data.object
                    })
                .catch(Error =>{
                })

                }else{   //취소

                    return false;

                }
            },
            updateFeed(num, title, content, count_star, address, image){
                var router = this.$router
                 router.push({
                    name: "UpdateFeed",
                    params: {
                        "num": num,
                        "title": title,
                        "content": content,
                        "address": address,
                        "count_star": count_star,
                        "image": image
                    }
                });
            },
           // //무한 스크롤 메소드
            infiniteHandler($state){    
                this.state = $state
               if(this.post.length!=0){
                setTimeout(()=>{

                    const temp = [];
                    const size = this.list.length;
                    for (let i = size; i< size+3; i++) {
                        if(this.post[i]!=null){
                            temp.push(this.post[i]);
                        }
                    }
                    this.list = this.list.concat(temp);
                    console.log(this.list)
                    $state.loaded();
                 
                    if(this.list.length==this.post.length){
                        $state.complete();
                  
                    }
                    
                },1000)
               }
            },
            //밑은 알람 메소드
            setAlarm(alarm) {
                this.userAlarmCount = alarm;
            },
            updateAlarmToFirebase() {       
     
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
      
                })
                .catch(Error => {
                    console.log(Error)
                })
                alert("댓글이 등록되었습니다.")
               
            },
            scrapfeed(num,title,content) {
                this.dialog= false
                let form = new FormData()
                form.append('postnum', num)
                form.append('title',title)
                form.append('content',content)
                form.append('num',this.$store.state.userinfo.num)
                http.post("/post/scrap", form)
         
                .then(Response => {
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            modal(num){
                this.modalnum = num
            },
            noscrap() {
                alert("이미 스크랩 된 게시물입니다.")
            }
           
        },
        data: () => {
            return {
                modalnum:0,
                dialog: false,
                usernum:0,
                isSubmit: false,
                error:{
                    comment:false
                },
                scraptitle:'',
                scrapcontent:'',
                nickname : '',
                num:0,
                intro:'',
                auth:1,
                email:'', 
                isfollow:0,
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
                commentcount:[],         
                //무한스크롤
                list:[],
                statt:'',
            }
        },
        components:{
            //무한스크롤 구현
            InfiniteLoading
        }
    }
</script>
<style lang="scss" scoped>
#app {

    position:relative;
    z-index:3
}
#app p {
    margin-left:5px;
    margin-bottom:1px;
    color: gray;
    font-size:12px;
}

</style>
