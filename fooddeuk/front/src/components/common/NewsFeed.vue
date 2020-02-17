<template>
    <v-app>
        
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
    import http from '../../../http-common'
    

    export default {
        created () {
           this.component = this;
            if(this.$store.state.userinfo!=null){
                this.email = this.$store.state.userinfo.email;
                this.nickname = this.$store.state.userinfo.nickName;
            }
        },
        watch : {
           
        },
        computed : {
            ...mapState(['userinfo']),
        },
        methods: {

           
        },
        data: () => {
            return {
               
            }
        }
    }
</script>
