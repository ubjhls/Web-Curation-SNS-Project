<template>
<v-app>
    <div class="user" id="follow">
        <div class="header" style="width:100%; height:auto">
            <div style="width:35px; float:left;">
            <button v-on:click="goNewsFeeds">
                <img src="../../assets/images/backIcon.png" style="width:100%;">
            </button>
            </div>
            <p style="vertical-align: middle;padding: 3px 5px;float:left; font-size:25px">팔로워 리스트</p>
        </div>
        <v-card
    max-width="500"
    class="mx-auto"
  >
    

    <v-list subheader style="margin-top:50px">
      <v-subheader>팔로워 리스트</v-subheader>

      <v-list-item
        v-for="item in items"
        :key="item.title"
      >
        <v-list-item-avatar>
          <v-img :src="item.avatar"></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          닉네임:<v-list-item-title v-text="item.title"></v-list-item-title>
        </v-list-item-content>

        <v-list-item-content>
          이메일:<v-list-item-subtitle v-text="item.subtitle"></v-list-item-subtitle>
        </v-list-item-content>

        <div>
        <v-btn small color="primary" @click="follow">팔로우</v-btn>
      </div>
      </v-list-item>
    </v-list>

    <v-divider></v-divider>

    <v-list subheader>
      <v-subheader>아직 수락하지 않은 사람들</v-subheader>

      <v-list-item
        v-for="item in items2"
        :key="item.title"
      >
        <v-list-item-avatar>
          <v-img :src="item.avatar"></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          닉네임:<v-list-item-title v-text="item.title"></v-list-item-title>
        </v-list-item-content>

        <v-list-item-content>
          이메일:<v-list-item-subtitle v-text="item.subtitle"></v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-card>
    </div>
</v-app>

</template>




<script>

    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'
import UserApi from '../../apis/UserApi';

    export default {
        components: {},
        created() {},
        watch: {},
        methods: {
            goNewsFeeds() {
                var router = this.$router;

                router.push({
                    name: "MainPage"
                });
            },
            follow(){
              console.log(this.items)
              let {nickName} = this;
              let data = {
                nickName
              }
              UserApi.requestFollow(data, res =>{
                console.log(res)
              })
            }
        },
    data: () => ({
      items: [
        { active: true, title: 'Jason Oner', avatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg' },
        { active: true, title: 'Ranee Carlson', avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg' },
        { title: 'Cindy Baker', avatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg' },
        { title: 'Ali Connors', avatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg' },
      ],
      items2: [
        { title: 'Travis Howard', avatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg' },
      ],
    }),
    }
</script>