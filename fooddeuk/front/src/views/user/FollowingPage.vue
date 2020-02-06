<template>
        <v-app>

    <div class="user" id="following">

        <div class="header" style="width:100%; height:auto">
            <div style="width:35px; float:left;">
            <button v-on:click="goNewsFeeds">
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
        v-for="item in items"
        :key="item.title"
      >

        <v-list-item-content>
          {{item.nickname}}
          <v-list-item-subtitle v-text="item.email"></v-list-item-subtitle>
        </v-list-item-content>

        <div class="profile-card-ctr" v-if="isfollow==0">  
            <button class="profile-card__button button--orange" @click="followgo()">Follow</button>
        </div>
        <div class="profile-card-ctr" v-if="isfollow==1">
          
            <button class="profile-card__button button--gray" @click="unfollowgo()">UnFollow</button>
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
    export default {
        components: {},
        created() {
          this.nickname = this.$store.state.userinfo.nickName;
          this.getUserByNickname(this.nickname);
        },
        watch: {},
        methods: {
            goNewsFeeds() {
                var router = this.$router;

                router.push({
                    name: "MainPage"
                });
            },
            getFollowing(num){
              http.get("follow/getFollowing/{num}?num="+ num)
              .then(Response => {
                console.log(Response)
                this.items = Response.data.object;
              })
            },
            getUserByNickname(nick) {
                let form = new FormData()
                form.append('nickname', nick)
                http.get("/user/userinfo/{nickname}?nickname=" + nick)
                .then(Response => {
                    console.log(Response)
                    this.num = Response.data.num;
                    this.getFollowing(this.num);
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            followgo(){
                let myn  = this.$store.state.userinfo.nickName;
                http.post("/follow/follow?mynickname=" + myn + "&nickname=" + this.nickname)
                .then(Response => {
                    this.isfollow = 1;
                    // console.log(Response.data)
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            unfollowgo(){
                let form = new FormData()
                let myn  = this.$store.state.userinfo.nickName;
                form.append('mynickname', myn)
                form.append('nickname',this.nickname)
                http.post("/follow/unFollow?mynickname=" + myn + "&nickname=" + this.nickname)
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
        },
        data: () => ({
      items: [],
      email:'',
      intro:'',
      num:0,
      nickname:'',
      isfollow:1,
    
    }),
    }
</script>