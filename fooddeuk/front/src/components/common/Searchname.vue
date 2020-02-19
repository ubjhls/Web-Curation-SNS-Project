<template>
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
                <li class="nick-list" v-for="item in history" :key="item">
                    <div class="profile-card-search" style="margin-left:-20px">
                        <img src="data:image/jpeg;base64,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PqrOr1Z3WJUMsatMOX4eThmL43G4gGVlJLE0kAvY2Ise5o6gOsD5laSsuxfB21JjkbJJDPCbwVMZtJGeo8C09QdDqurhbj6QTx0le+CTmScqCuhI5Uk405MzP5uU6eRvos7PimtpnxK/gyxasR5ho6IigWBERAREQEREBERAREQEREArFeNfh/UUXOdSMklo6mqElRSQMPaGsLe8xpB1jLg3QC+vkSdqRexMxw8mNsei/licNbBg87W7Zp5YYA0W0u25cVIUvAmLz61FfS0wv8tPE6V9vAvlsAfMBaiiltnyT5RxhpHhDcKcOxYVT8iJ8j7vfI+R+Uvkkebuc7KAPAbbAKZRFClEREBERAREQUXFPhrFLPUTwV9bTvqJDJIxhidDzCBmdy3N3NrnVRM/BuNU36ito6kDdssb4HkeRYS2/qFqCLuuS1eJcWpW3MMUxLHamla6PEKWpoy8FrakBssLXHQFsrAW366hQfC+DS4ni0bQ5sscEkclZVU5ijbI0AyU7pG3I5nMaQS0ag9N1/QksbXtLXNDmuFnNIBBHgQVy4XhFNRtc2mpoIWuOZzY42RhzvEhoC6vmteNS5pirWdw7URFElEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQf//Z" alt="profile card">
                        <p @click="goProfileByNickname(item)" style="float:left; width:100px; height:20px; margin-top:15px; margin-right:90px">{{ item }}</p>
                        <button style="margin-top:18px" @click="RemoveName(item)">
                        X</button>
                    </div>
                    <br>
                </li>
            </ul>
        </div>

        <div v-if="searchResult && isSearch=='search'" style="padding-top:10px">
            <ul>
                <li class="nick-list" v-for="(item,index) in searchResult" :key="item.nickName">
                    <div class="profile-card-search" style="margin-left:-20px">
                        <a @click="goProfileByNickname(item.nickname)"><img src="data:image/jpeg;base64,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PqrOr1Z3WJUMsatMOX4eThmL43G4gGVlJLE0kAvY2Ise5o6gOsD5laSsuxfB21JjkbJJDPCbwVMZtJGeo8C09QdDqurhbj6QTx0le+CTmScqCuhI5Uk405MzP5uU6eRvos7PimtpnxK/gyxasR5ho6IigWBERAREQEREBERAREQEREArFeNfh/UUXOdSMklo6mqElRSQMPaGsLe8xpB1jLg3QC+vkSdqRexMxw8mNsei/licNbBg87W7Zp5YYA0W0u25cVIUvAmLz61FfS0wv8tPE6V9vAvlsAfMBaiiltnyT5RxhpHhDcKcOxYVT8iJ8j7vfI+R+Uvkkebuc7KAPAbbAKZRFClEREBERAREQUXFPhrFLPUTwV9bTvqJDJIxhidDzCBmdy3N3NrnVRM/BuNU36ito6kDdssb4HkeRYS2/qFqCLuuS1eJcWpW3MMUxLHamla6PEKWpoy8FrakBssLXHQFsrAW366hQfC+DS4ni0bQ5sscEkclZVU5ijbI0AyU7pG3I5nMaQS0ag9N1/QksbXtLXNDmuFnNIBBHgQVy4XhFNRtc2mpoIWuOZzY42RhzvEhoC6vmteNS5pirWdw7URFElEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQf//Z" alt="profile card">
                        <p style="float:left; width:100px; color:gray; height:20px; margin-top:15px; margin-right:90px" v-html="searchResult[index]"></p></a>
                        <button style="margin-top:18px" @click="RemoveName(item)">
                        X</button>
                    </div>
                    <br>
                </li>
            </ul>
        </div>
    </div>
    <div v-if="fcheck">
        <div class="field" style="float:left; width:80%;">
            <input v-model="searchContents" type="text" class="input-search" @keyup.enter="AddName">
        </div>
         <div style="width:20%; float:left">
                <button class="check-button" @click="serachFeed()">검색</button>
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
                        <v-img src="https://cdn.vuetifyjs.com/images/lists/1.jpg"></v-img>
                    </v-list-item-avatar>
                    <v-list-item-content>
                    <v-list-item-title v-html="item.title"></v-list-item-title>
                    <v-list-item-subtitle v-html="item.content"></v-list-item-subtitle>
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
                            News Feed
                        </v-card-title>

                         <v-card
                            max-width="100%"
                            class="mx-auto"
                            style="position:relative"
                    >
                    <v-list-item>
                        <v-list-item-avatar style="height:50px; width:50px"><img src="../../assets/images/profile_default.png"></v-list-item-avatar>
                        <v-list-item-content style="padding-left:5%">
                        <v-list-item-title style="margin-left:5px; margin-top:5px; font-size:15px;">{{postdetail.title}}
                        </v-list-item-title>
                        <v-list-item-subtitle style="width:50px; margin-left:5px">{{postdetail.nickname}} <br>
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
                            주소 : {{postdetail.address}} 
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

                    </div>
                    <br>
                    </div>
                    <br>
                    
                        <div v-if="postdetail.islike==1">
                            <p v-if="postdetail.islike == 1 && postdetail.count_like == 1 ">
                                {{myNick}}님<span>이 좋아합니다.</span>
                            </p>
                            <p v-else>
                                {{myNick}}님 외  {{ postdetail.count_like - 1 }} 명이 좋아합니다
                            </p>
                        </div>
                    
                        <div v-if="postdetail.islike==0">
                            <p>
                                {{ postdetail.count_like }} 명이 좋아합니다
                            </p>
                        </div>
                        
                        <p>
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
                        <v-btn
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
                    
                    this.searchResult = res;
                    for (let index = 0; index < this.searchResult.length; index++) {
                        this.searchResult[index] = this.searchResult[index].nickname.replace(search,'<span style="color:red">'+search+'</span>');
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
                if(this.searchContents!=''){
                    http.get("/search/feed?keyword=" + this.searchContents +'&email=' + this.email)
                    .then(Response => {
                        this.post=[]
                        if(Response.data.object!=null){

                            this.post=Response.data.object
                            
                        }
                      
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                }else{
                    alert("검색을 입력해주세요!")
                }
            },
            detail(num){
                this.postdetail = this.post[num];
                console.log(this.postdetail)
                this.postdetail.content = this.postdetail.content.replace(/(?:\r\n|\r|\n)/g, '<br/>')
  
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

                http.post('/postlike/like',form)
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
                        console.log(response.data.object)
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
            }
        },
}
</script>