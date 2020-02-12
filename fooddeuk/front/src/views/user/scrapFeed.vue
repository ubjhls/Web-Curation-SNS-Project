<template>

         <div style="margin-top:200px; float:right; height:30px; position:relaive; z-index:1000" class="mynum">
            <dropdown>
            <input id="toggle2" type="checkbox">
            <label for="toggle2" class="animate"><i class="fa fa-bars float-right"></i></label>
            <ul class="animate">
                <li class="animate">수정</li>
                <li class="animate">삭제</li>
                
            </ul>
            </dropdown>
        <!-- <button style="float:right" @click="updateFeed(item.num,item.title,item.content,item.count_star,item.address,item.image)">수정</button>
        <button style="float:right" @click="removeFeed(item.num)">삭제</button> -->
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
<style lang="scss" scoped>

p {
    margin-left:5px;
    color: gray;
    font-size:12px;
}

.fa{
 font-size: .8em;
  line-height: 22px !important;
}

dropdown label, dropdown ul li{
  display: block;
  width: 60px;
  background: #ECF0F1;
  padding: 15px 20px;
}

dropdown input{
  display: none;
}
dropdown input ~ ul{
  position: relative;
  visibility: hidden;
  opacity: 0;
  top: -20px;
  z-index: 1;
}
dropdown input:checked + label{
  background: rgb(0, 0, 0);
  color: white;
}

dropdown input:checked ~ ul{
  visibility: visible;
  opacity: 1;
  top: 0;
}

.animate{
  -webkit-transition: all .3s;
  -moz-transition: all .3s;
  -ms-transition: all .3s;
  -ms-transition: all .3s;
  transition: all .3s;  
  backface-visibility:hidden;
  -webkit-backface-visibility:hidden; /* Chrome and Safari */
}
</style>