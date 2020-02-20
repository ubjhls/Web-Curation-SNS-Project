<template>
<v-app>
<div class="wrapC">
    <div>
    <div action="" class="search">
    <v-tabs style="margin-top:80px">
        <v-tab @click="checkchange(ncheckname)">닉네임 검색</v-tab>
        <v-tab @click="checkchange(fcheckname)">피드 검색</v-tab> 
    </v-tabs>
    <br>
    <div v-if="ncheck">
        <div class="field" style="float:left; width:80%;">
            <input v-model="NewName" type="text" class="input-search" @keyup.enter="AddName">
    
        </div>
            <div style="width:20%; float:left">
                <button class="check-button" @click="AddName" :disabled="!canClick">검색</button>
            </div>

    <br>
    <h1 style="margin-top:60px" v-if="isSearch=='history'">최근 검색</h1>
    <h1 style="margin-top:60px" v-if="isSearch=='search'">검색 결과</h1>
        <div v-if="history.length && isSearch=='history'" style="padding-top:20px">
            <ul>
                <li class="nick-list" v-for="item in history" :key="item.id">
                    <div class="profile-card-search" style="margin-left:-20px">
                        <img :src="item.picture" alt="picture">
                        <p @click="goProfileByNickname(item.nickname)" style="float:left; width:100px; height:20px; margin-top:15px; margin-right:90px">{{ item.nickname }}</p>
                        <button style="margin-top:18px" @click="RemoveName(item.nickname)">
                        X</button>
                    </div>
                    <br>
                </li>
            </ul>
        </div>
        
        <div v-if="searchResult.length==0 && isSearch=='search'" style="padding-top:10px; text-align:center;">
            검색 결과가 없습니다.
        </div>
        <div v-if="searchResult.length!=0 && isSearch=='search'" style="padding-top:10px">
            <ul>
                <li class="nick-list" v-for="(item,index) in searchResult" :key="item.nickName">
                    <div class="profile-card-search" style="margin-left:-20px">
                        <a @click="goProfileByNickname(item.nickname)"><img :src="item.picture" alt="profile card">
                        <p style="float:left; width:100px; color:gray; height:20px; margin-top:15px; margin-right:90px" v-html="searchAlias[index]"></p></a>
                        
                    </div>
                    <br>
                </li>
            </ul>
        </div>
    </div>
    <div v-if="fcheck">
        <div class="field" style="float:left; width:80%;">
            <input v-model="searchContents" type="text" class="input-search" @keyup.enter="serachFeed">
        </div>
         <div style="width:20%; float:left">
                <button class="check-button" @click="serachFeed">검색</button>
        </div>
        <div style="clear:both;"></div>
        <div v-if="post.length==0" style="padding-top:20px; text-align:center">검색 결과가 없습니다.</div>
        <div v-else>
            <v-list three-line>
                <br>
                <h3>검색결과</h3>
                <template v-for="(item,index) in post" >
                    <v-list-item
                    :key="item.title"
                    @click="detail(index)"
                    >
                    <v-list-item-avatar>
                        <v-img :src="item.picture"></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                        <div style="margin-bottom:10px">
                    <v-list-item-title v-html="item.title"></v-list-item-title>
                    <v-list-item-subtitle v-html="item.content"></v-list-item-subtitle>
                        </div>
                    </v-list-item-content>
                    </v-list-item>
                  
            </template>
            </v-list>
        </div>
    </div>
    <div style="margin-bottom:30px">
    </div>
    </div>
    </div>
      <div class="text-center" id="app">
                        <v-dialog
                        v-model="dialog"
                        width="500"
                        :retain-focus="false"
                        >
                        <v-card>
                        <v-card-title
                        class="headline grey lighten-2"
                        primary-title>
                        피드 검색
                        </v-card-title>

                         <v-card
                            max-width="100%"
                            class="mx-auto"
                            style="position:relative"
                    >
                    <v-list-item>
                        <v-list-item-avatar style="height:50px; width:50px" @click="goProfileByNickname(postdetail.nickname)"><img :src="postdetail.picture"></v-list-item-avatar>
                        <v-list-item-content style="padding-left:5%">
                        <v-list-item-title style="margin-left:5px; margin-top:5px; font-size:15px;" v-html="postdetail.title">
                        </v-list-item-title>
                        <v-list-item-subtitle style="width:50px; margin-left:5px" v-html="postdetail.nickname"> <br>
                         <div style="margin-top:10px; margin-left:2px"> {{getTime(postdetail.date)}}</div> </v-list-item-subtitle>
                        </v-list-item-content>
                        </v-list-item>
                            <v-col cols="12" sm="3">
                                <div v-for="star in postdetail.count_star" :key="star.num">
                                    <v-icon style="color:red; float : left">mdi-star</v-icon>
                                </div>
                                
                            </v-col>
                            <br>
                            <v-card-text>
                                <div v-html="postdetail.content"></div>
                                <img v-if="postdetail.image!=='null' || postdetail.image!==null" v-bind:src="postdetail.image"  style="width:100%; heigh:auto; ">
                            <br>
                            <br><br><hr><br>
                            주소 : 
                            <div v-html="postdetail.address">
                            </div> 
                            </v-card-text>

                    <div style="width:100%">
                    <div style="margin-bottom:10px; margin-top:15px; padding-left:5px">
                        <div style="width:33%; float:left;">
                            <button class="animated rubberBand" v-if="postdetail.islike==1" @click="toggledelete(postdetail.num)"><img style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/likefill.png"></button>
                            <button v-if="postdetail.islike==0" @click="toggleadd(postdetail.num)"><img class="animated rubberBand" style="width:30px; margin-left:10px; margin-bottom:5px" src="../../assets/images/like.png"></button>
                        </div>
                        <div style="width:33%; float:left; text-align:center; margin-top:3px">

                        <button @click="commentview(postdetail.num)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/comment.png"></button>
                        </div>
                         <v-row style="backgroud:white; float:right; margin-right:2px;" justify="center">
                            <v-dialog v-model="searchdialog" persistent max-width="290" :retain-focus="false">
                            <template v-slot:activator="{ on }">
                                <v-btn depressed color="white" v-on="on" @click="modal(postdetail.num)"><img style="width:26px; margin-bottom:5px" src="../../assets/images/share.png"></v-btn>
                            </template>
                            <v-card style="height:400px">
                                <v-card-title class="headline">{{postdetail.nickname}}님의 게시물</v-card-title>
                                 <v-text-field style="color:blue; width:90%; margin-left:10px" label="제목입력" v-model="searchtitle" id="searchtitle" counter
                                maxlength="13"></v-text-field>
                                <v-textarea style="color:blue; width:90%; margin-left:10px" 
                                v-model="searchcontent" 
                                label="내용입력" 
                                id="searchcontent" 
                                counter
                                maxlength="50"
                                full-width
                                single-line>
                                </v-textarea>
                                <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="green darken-1" text @click="searchdialog = false">취소</v-btn>
                                <v-btn color="green darken-1" text @click="scrapfeed(postdetail.num, searchtitle, searchcontent);">스크랩</v-btn>
                                </v-card-actions>
                            </v-card>
                            </v-dialog>
                        </v-row>
                    </div>
                    <br>
                    </div>
                    <br>
                    
                        <div v-if="postdetail.islike==1" style="margin-top:5px; margin-left:5px">
                            <p v-if="postdetail.islike == 1 && postdetail.count_like == 1 " style="color: gray; font-size:12px;">
                                {{myNick}}님<span>이 좋아합니다.</span>
                            </p>
                            <p v-else style="color: gray; font-size:12px;">
                                {{myNick}}님 외  {{ postdetail.count_like - 1 }} 명이 좋아합니다
                            </p>
                        </div>
                    
                        <div v-if="postdetail.islike==0" style="margin-top:5px; margin-left:5px">
                            <p style="color: gray; font-size:12px;">
                                {{ postdetail.count_like }} 명이 좋아합니다
                            </p>
                        </div>
                        
                        <p style="color: gray; font-size:12px; margin-left:5px">
                            {{ postdetail.count_comment }} 개의 댓글이 있습니다.
                        </p>

                        <div v-if="commentcheck==true">
                            <div v-for="cmt in todolist" v-bind:key="cmt.id" >        
                                    <h5 style="float:left; margin-left:5px; margin-right:20px; font-weight:bold;"> {{ cmt.nickname }}</h5> &nbsp; 
                                    <h5 style="float:left; ">{{ cmt.comment }} 
                                    </h5>
                                    <span style="float:right; margin-right:20px; font-weight:lighter; color:red" v-if="cmt.author==mynum || cmt.author == mynum" @click="removeComent(postdetail.num,cmt)">X</span>
                                    <br>                 
                            </div>
                          
                            <div style="width:30%; float:right; margin-right:5px; margin-top:17px">
                                <button style="height:30px;" class="comment-ok" @click="addcomment(postdetail.num)"
                                :disabled="!isSubmit"
                                :class="{disabled : !isSubmit}"
                                >게시</button>
                            </div>
                            <div style="margin-left:5px; width:60%;">
                                <v-text-field style="color:blue; width:90%" label="댓글입력" v-model="newcomment" id="newcomment" hide-details="auto">
                                </v-text-field>
                            </div> 
                        </div> 
                 

                    </v-card>
                        <v-divider></v-divider>
                        <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn style="float:right"
                            color="primary"
                        text
                        @click="close()">
                        닫기
                        </v-btn>
                    </v-card-actions>
                </v-card>
             </v-dialog>
        </div>
</div>
</v-app>

</template>

<script>
    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'
    import '../../assets/css/search.scss'
    import UserApi from '../../apis/UserApi'
    import {mapState} from 'vuex';
    import Axios from "axios";
    import http from '../../../http-common'
    import moment from 'moment'
    import {fireDB} from '../../main'
    import firebase from 'firebase'

    export default {
        components : {
        },
        created() {
            if(this.$store.state.userinfo!=null) {
                this.email = this.$store.state.userinfo.email;
                this.myNick = this.$store.state.userinfo.nickName;
                this.mynum = this.$store.state.userinfo.num;
            }
            this.GetAllSearch();
        },
        updated() {
            this.email = this.$store.state.userinfo.email;
            this.myNick = this.$store.state.userinfo.nickName;
        },
        computed : {
            ...mapState(['userinfo']),
        },
        watch: {
            NewName: function(v) {
                this.checkNewName();
            }
        },
        methods: {
            updateAlarmToFirebase(email) {       
                fireDB.collection('Alarm').doc(email)
                .update({
                    count : firebase.firestore.FieldValue.increment(1)
                })
            },
            checkNewName() {
                if(this.NewName.length!=0) {
                    this.canClick = true;
                } else {
                    this.canClick = false;
                }
            },
            getUserInfo(otherEmail) {
                let form = new FormData()
                form.append('myEmail', this.email)
                form.append('otherEmail', otherEmail)
                http.get("/user/userinfo?myEmail=" + this.email + '&otherEmail=' + otherEmail)
                .then(Response => {

                })
                .catch(Error => {
                    console.log(Error)
                })
            },
                getUserInfoByNickname(nick) {
                let form = new FormData()
                form.append('nickname', nick)
                http.get("/user/userinfo/{nickname}?nickname=" + nick)
                .then(Response => {
                    this.otherEmail = Response.data.email;
                    this.getUserInfo(this.otherEmail);
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            GetAllSearch: function() {
                let data = {email : this.email};

                UserApi.requestGetAllSearch(data, res => {
                    if (res != null) {
                        this.history = res;
                        console.log(this.history)
                    } else {
                        alert("데이터 없음");
                    }
                    this.isSubmit = true;
                }, error => {
                    var router = this.$router;
                    router.push({
                        name: "ErrorPage",
                        params: {
                            "email": this.email,
                            "route": this.$route.name
                        }
                    });
                })
            },
            AddName: function () {
                this.isSearch = 'search';

                let data = {nickName : this.NewName};
                
                let search = this.NewName
                UserApi.requestSearchNickname(data, res => {
                    console.log(res)
                    this.searchResult = res;
                    if(res.length!=0) {
                        for (let index = 0; index < this.searchResult.length; index++) {
                            this.$set(this.searchAlias, index, this.searchResult[index].nickname.replace(search,'<span style="background-color:yellow">'+search+'</span>'));
                        }
                    }
                    this.isSubmit = true;
                }, error => {
                    var router = this.$router;
                    router.push({
                        name: "ErrorPage",
                        params: {
                            "email": this.email,
                            "route": this.$route.name
                        }
                    });
                })

                this.NewName = ''
            },
            RemoveName(nickName) {
                let data = {myNick : this.myNick, otherNick : nickName};

                UserApi.requestDeleteSearch(data, res => {
                    if (res == 'success') {
                        this.GetAllSearch();
                    }
                }, error => {
                    var router = this.$router;
                    router.push({
                        name: "ErrorPage",
                        params: {
                            "email": this.email,
                            "route": this.$route.name
                        }
                    });
                })
            },
            goBack() {
                var router = this.$router;
                router.go(-1)
            },
            goProfileByNickname(nick) {
                this.getUserInfoByNickname(nick);
                this.$emit('child', nick)
            },
            checkchange(value){
        
                if(value=='n'){

                    if(this.ncheck==false){
                        this.ncheck = true;
                        this.fcheck = false;
                    }
                }else{
                    if(this.fcheck==false){
                        this.ncheck = false;
                        this.fcheck = true;
                    }
                }
            },
            serachFeed(){
                console.log('serachFeed')
                if(this.searchContents!=''){
                    http.get("/search/feed?keyword=" + this.searchContents +'&email=' + this.email)
                    .then(Response => {
                        this.post=[]
                        console.log(Response)
                        if(Response.data.object!=null){

                            this.post=Response.data.object;

                            for (let index = 0; index < this.post.length; index++) {
                                this.post[index].title = this.post[index].title.replace(this.searchContents,'<span style="background-color:yellow">'+this.searchContents+'</span>');
                                this.post[index].content = this.post[index].content.replace(this.searchContents,'<span style="background-color:yellow">'+this.searchContents+'</span>');
                                this.post[index].address = this.post[index].address.replace(this.searchContents,'<span style="background-color:yellow">'+this.searchContents+'</span>');
                                this.$set(this.post, index, this.post[index]);
                            }
                        }
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                }else{
                    alert("검색어을 입력해주세요!")
                }
            },
            detail(num){
                this.postdetail = this.post[num];

                console.log(this.postdetail)
                this.postdetail.content = this.postdetail.content.replace(/(?:\r\n|\r|\n)/g, '<br/>');
  
                if(!this.dialog){
                    this.dialog = true;
                }
            },
             getTime(time) {
                moment.locale('ko')
                return moment(time).fromNow();
            },
            toggleadd(num, index) { //좋아요를 클릭할때
                
                //좋아요 서버로 전송하기
                let form = new FormData()
                form.append('postnum', num)
                form.append('email', this.email)

                let modalEmail = null;

                http.get("/user/userinfo/{nickname}?nickname="+this.postdetail.nickname)
                .then(Response => {
                    modalEmail = Response.data.email
                })
                .catch(Error => {
                    console.log(Error)
                })
                http.post('/postlike/like',form)
                .then(response => {
                    http.get("/post/post/{postnum}?num=" + num +'&email=' + this.email)
                    .then(Response => {
                        this.postdetail = Response.data.object
                        if(this.mynum!=this.postdetail.author) {
                            this.updateAlarmToFirebase(modalEmail)
                        }
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            toggledelete(num, index) { //좋아요를 해제할때

                http.delete("/postlike/unlike?postnum="+num + '&email=' + this.email)
                .then(response => {

                     http.get("/post/post/{postnum}?num=" + num +'&email=' + this.email)
                    .then(Response => {
                        this.postdetail = Response.data.object
                      
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                })
                .catch(Error => {
                     console.log(Error)
                })
            },
            close(){
                this.dialog = false
                this.todolist = ''
                this.commentcheck = false
                http.get("/search/feed?keyword=" + this.searchContents +'&email=' + this.email)
                    .then(Response => {
                        this.post=[]
                        if(Response.data.object!=null){
                            this.post=Response.data.object

                            for (let index = 0; index < this.post.length; index++) {
                                this.post[index].title = this.post[index].title.replace(this.searchContents,'<span style="background-color:yellow">'+this.searchContents+'</span>');
                                this.post[index].content = this.post[index].content.replace(this.searchContents,'<span style="background-color:yellow">'+this.searchContents+'</span>');
                                this.post[index].address = this.post[index].address.replace(this.searchContents,'<span style="background-color:yellow">'+this.searchContents+'</span>');
                                this.$set(this.post, index, this.post[index]);
                            }
                        }
                      
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
            },
            commentview(num){ //댓글 버튼 누를 때
            //댓글 불러오기
            this.newcomment=''

            if(this.commentcheck==false){
                this.commentcheck = true;
 
                http.get('/comment/comment?postnum='+num)
                .then(response => {
                    if(response.data.object!=null){
                        this.todolist = response.data.object
                    }     
                })
                .catch(Error => {
                    console.log(Error)
                })

            // //댓글 숨기기
             }
            else if(this.commentcheck==true){
                    this.todolist='';
                    this.commentcheck = false;
                   
                }    
            },
            addcomment(num) {
                let form = new FormData()
                form.append('comment', this.newcomment)
                form.append('email', this.$store.state.userinfo.email)
                form.append('postnum', num)
                http.post("/comment/comment", form)
                .then(response => {
                   
                    //댓글 등록
                    this.todolist = response.data.object

                    //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                        this.postdetail.count_comment = Response.data
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                    
                    //댓글 초기화
                    this.newcomment=''
                })
                
            },
             removeComent(num, cmt){
                http.delete("/comment/comment?postnum=" + num + "&num="+ cmt.num + "&nickname=" + cmt.nickname + "&date=" + cmt.date)
                .then(response => {
                    //댓글 삭제(갱신까지)
                    this.todolist = response.data.object
                    
                    //댓글 수 갱신
                    http.get("/comment/count?postnum="+num)
                    .then(Response => {
                        
                        this.postdetail.count_comment = Response.data
                        
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                })
                .catch(Error =>{
                })
 
                
            },
            modal(num){
                this.modalnum = num;
            },
            scrapfeed(num,title,content) {
                this.dialog= false
                let form = new FormData()
                form.append('postnum', num)
                form.append('title',title)
                form.append('content',content)
                form.append('num',this.$store.state.userinfo.num)
                this.searchtitle = ''
                this.searchcontent = ''
                alert('스크랩 되었습니다.')
                http.post("/post/scrap", form)
            },
         

        },
        
        data: () => {
            return {
                mynum:'',
                email : '',
                otherEmail:'',
                myNick: '',
                NewName: '',
                isSearch : 'history',
                history: [],
                searchResult : [],
                searchAlias : [],
                canClick:false,
                searchedUser : {
                    nickname:null,
                    email:null,
                    intro:null,
                    auth:null,
                    post:[],
                },
                ncheck:true,
                fcheck:false,
                ncheckname:"n",
                fcheckname:"f",
                post:[],
                items:[],
                searchContents:'',
                dialog: false,
                postdetail:'',
                newcomment:'',
                commentcheck:false,
                todolist:'',
                modalnum: 0,
                searchtitle:'',
                searchcontent:'',
                searchdialog: false,
            }
        },
}
</script>
