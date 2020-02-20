<template>
        <v-app>

    <div class="user" id="following">

        <div class="header" style="width:100%; height:auto">
            <div style="width:35px; float:left;">
            <button v-on:click="goBack">
                <img src="../../assets/images/backIcon.png" style="width:100%;">
            </button>
            </div>
            <p style="vertical-align: middle;padding: 3px 5px;float:left; font-size:25px">팔로잉 리스트</p>
        </div>
        <v-card
    max-width="500"
    class="mx-auto"
  >

    <v-list subheader style="margin-top:50px">
      <v-subheader>팔로잉 리스트</v-subheader>

     <v-list-item
        v-for="(item, index) in items"
        :key="item.title"
      >

        <v-list-item-content>
            <button @click="GoUserFeed(item.nickname)">
            {{item.nickname}}
            </button>
          <v-list-item-subtitle v-text="item.email"></v-list-item-subtitle>
        </v-list-item-content>

        <div class="profile-card-ctr" v-if="isfollow[index]==0">  
            <button class="profile-card__button button--orange" @click="followgo(index)">Follow</button>
        </div>
        <div class="profile-card-ctr" v-if="isfollow[index]==1">
          
            <button class="profile-card__button button--orange" style="background:gray" @click="unfollowgo(index)">UnFollow</button>
        </div>
      </v-list-item>
    </v-list>

    <v-divider></v-divider>

    
  </v-card>

    </div>
      </v-app>
    
</template>


<script>

    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'
    import UserApi from '../../apis/UserApi'
    import http from '../../../http-common'
    import {fireDB} from '../../main'
    import firebase from 'firebase'
    
    export default {
        components: {},
        created() {
            setTimeout(() => { 
                this.nickname = this.$store.state.userinfo.nickName;
                this.getUserByNickname(this.nickname);
            }, 200);
        },
        watch: {},
        methods: {
            goNewsFeeds() {
                var router = this.$router;

                router.push({
                    name: "MainPage"
                });
            },
            goBack() {
                this.$router.go(-1);
            },
            getFollowing(num){
              http.get("follow/getFollowing/{num}?num="+ num)
              .then(Response => {
                console.log(Response)
                this.items = Response.data.object;
                this.isfollow = Response.data.object2;

              })
            },
            getUserByNickname(nick) {
                let form = new FormData()
                form.append('nickname', nick)
                http.get("/user/userinfo/{nickname}?nickname=" + nick)
                .then(Response => {
                    this.num = Response.data.num;
                    this.email = Response.data.email;
                    this.getFollowing(this.num);
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            updateAlarmToFirebase(email) {
                fireDB.collection('Alarm').doc(email)
                .update({
                    count : firebase.firestore.FieldValue.increment(1)
                })
            },
            followgo(index){
                if(this.items[index].auth==0) {
                    let form = new FormData();
                    let myn  = this.$store.state.userinfo.nickName;
                    form.append('mynickname', myn)
                    form.append('nickname',this.items[index].nickname)
                    http.post("/follow/follow", form)
                    .then(Response => {
                        this.$set(this.isfollow,index,1)
                        this.updateAlarmToFirebase(this.items[index].email);
                        // console.log(Response.data)
                    })
                    .catch(Error => {
                        console.log(Error)
                    })
                }
                else if(this.items[index].auth==1) {
                    let form = new FormData();
                    let myn  = this.$store.state.userinfo.nickName;
                    form.append('mynickname', myn)
                    form.append('nickname',this.items[index].nickname)
                    
                    http.post("/follow/nonfollow", form)
                    .then(Response => {
                        console.log(Response)
                        this.updateAlarmToFirebase(this.items[index].email);
                        if(Response.data==='success') {
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
            unfollowgo(index){
                let form = new FormData()
                let myn  = this.$store.state.userinfo.nickName;
                form.append('mynickname', myn)
                form.append('nickname',this.items[index].nickname)
                http.post("/follow/unFollow", form)
                .then(Response => {
                  this.$set(this.isfollow,index,0)
                    console.log(this.isfollow)
                    // console.log(Response.data)
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            GoUserFeed(nick){
                var router = this.$router
                 router.push({
                    name: "MainPage",
                    params: {
                        "nickname": nick,
                    }
                });
            }
        },
        data: () => ({
      items: [],
      isfollow:[],
      email:'',
      intro:'',
      num:0,
      nickname:'',
    
    }),
    }
</script>