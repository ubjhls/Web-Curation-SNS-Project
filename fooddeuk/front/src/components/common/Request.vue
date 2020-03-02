<template>
  <v-card
    class="mx-auto"
  >
    <div v-if="items.length==0">
      요청이 없습니다.
    </div>
    <v-list v-if="items.length!=0" three-line>
      <template  v-for="(item, index) in items">
        <v-subheader
          v-if="item.header"
          :key="item.header"
          v-text="item.header"
        ></v-subheader>

        <v-divider
          v-else-if="item.divider"
          :key="index"
          :inset="item.inset"
        ></v-divider>

        <v-list-item
          v-else
          :key="item.title"
        >
          <v-list-item-avatar>
            <v-img :src="item.picture"></v-img>
          </v-list-item-avatar>

          <v-list-item-content style="margin-left:20px;">
            <v-list-item-title>"{{item.sender}}"님이 팔로우 신청을 했습니다.</v-list-item-title>
            <v-list-item-subtitle>{{getTime(item.date)}}</v-list-item-subtitle>
            <div>

            <div style="width:20px; float:left">
            <v-btn small dark style="margin-right:20px; background-color:#a4c2f4ff; color:#ffffff" @click="accept(item.num, item.sender)">승인</v-btn>
            </div>
            <div style="width:20px; float:left; margin-left:40px">
            <v-btn small dark style="background-color:#e06666ff; color:#ffffff" @click="deny(item.num, item.sender)">거절</v-btn>
            </div>
            </div>
          </v-list-item-content>
        </v-list-item>
      </template>
    </v-list>
  </v-card>
</template>

<script>
  import {mapState} from 'vuex';
  import http from '../../../http-common'
  import Vue from 'vue'
  import moment from 'moment'
  import VueMomentJS from 'vue-momentjs'
  import { fireDB } from '../../main';
  import EventBus from '../../EventBus'
  import firebase from 'firebase'

  Vue.use(VueMomentJS, moment)


  export default {
    created()  {
      moment.locale('ko');
      if(this.$store.state.userinfo!=null) {
        this.email = this.$store.state.userinfo.email;
        this.nickname = this.$store.state.userinfo.nickName;
      }
      this.getAlarms();
      this.watchAlarmFromFirebase();
    },
    mounted : function() {
      this.emitRequest();
    },
    methods : {
      emitRequest() {
        let whoami = this;
        EventBus.$on('emitRequest', function() {
          whoami.getAlarms();
        })
      },
      setAlarm(alarm) {
        this.alarmCount = alarm;
      },
      upAlarmToFirebase(email) {
        fireDB.collection('Alarm').doc(email)
        .update({
          count : firebase.firestore.FieldValue.increment(1)
        })
      },
      downAlarmToFirebase(email) {
        fireDB.collection('Alarm').doc(email)
        .update({
          count : firebase.firestore.FieldValue.increment(-1)
        })
      },
      watchAlarmFromFirebase() {
          let whoami = this;
          let count=0;
          fireDB.collection('Alarm').doc(this.email).onSnapshot( {
              includeMetadataChanges: true    
          },function(doc) {
              count = doc.data().count;
              whoami.getAlarms();
              whoami.setAlarm(count);
          })
      },
      getTime(time) {
        return moment(time).fromNow();
      },
      getAlarms() { 
        http.get("/follow/requestlist?mynickname=" + this.nickname)
        .then(Response => {
          this.items = Response.data
        })
        .catch(Error => {
            console.log(Error)
        })
      },
      accept(num, otherNickname) {
        let form = new FormData();
          
        form.append('num', num)
        form.append('nickname', this.nickname)
        form.append('mynickname', otherNickname)
        form.append('agree', '1')
        http.post("/follow/followagree", form)
        .then(Response => {
          this.isfollow = 1;
          this.getAlarms();
        })
        .catch(Error => {
            console.log(Error)
        })
      },
      deny(num, otherNickname) {
        let form = new FormData();
          
        form.append('num', num)
        form.append('nickname', this.nickname)
        form.append('mynickname', otherNickname)

        var otherEmail;
        http.get('/user/userinfo/nickname?nickname=' + otherNickname)
        .then(Response => {
          otherEmail = Response.data.email;
        })
        .catch(Error => {
          console.log(Error)
        })

        form.append('agree', '0')
        http.post("/follow/followagree", form)
        .then(Response => {
          this.getAlarms();
          this.downAlarmToFirebase(this.email);
          this.upAlarmToFirebase(otherEmail);
        })
        .catch(Error => {
            console.log(Error)
        })
      }
    },
    data: () => ({
      email : '',
      nickname:'',
      items: [],
      alarmCount : 0,
    }),
    computed : {
        ...mapState(['userinfo']),
    }
  }
</script>