<template>
    <div class="contents_center" style="margin-top:65px; margin-bottom:65px">
    <v-tabs v-model="tab" grow slider-color="#F7A937">
        <v-tab
        v-for="item in items"
        :key="item"
      >
        {{ item }}
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item
        v-for="item in items"
        :key="item"
      >
        <Alarm v-if="item==='알림'"></Alarm>
        <Request v-if="item==='요청'"></Request>
      </v-tab-item>
    </v-tabs-items>
    </div>
</template>

<script>
    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'
    import UserApi from '../../apis/UserApi'
    import Alarm from './Alarm'
    import Request from './Request'

    


    export default {
        components : {
          Alarm,
          Request
        },
        data : () => {
            return {
                component: this,
                tab: null,
                items: ['알림', '요청'],
            }
        },
        
    }
</script>