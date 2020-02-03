import Vue from 'vue'
import App from './App.vue'
import router from './routes'
import store from './vuex/store'
import axios from 'axios'
import vuetify from './plugins/vuetify';

Vue.prototype.$Axios = axios;
Vue.config.productionTip = false

new Vue({
    router,
    store,
    vuetify,
    beforeCreate() {
        this.$store.dispatch('getMemberInfo')
    },
    render: h => h(App)
}).$mount('#app');