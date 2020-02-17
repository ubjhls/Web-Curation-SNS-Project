
<template>
    <div class="user" id="login">
        <div class="wrapC" style="text-align:center">
                <h1>404</h1>
                <h1>Not Found</h1>
                <h2>해당 페이지를 찾을 수 없습니다.</h2>
                <button @click="backgo" class="btn btn--cancel" style="background-color: black;">
                    뒤로가기
                </button>
        </div>
    </div>
</template>

<script>
    export default {
        data: () => {
            return {
                name:"",
                intro:"",
                nickName:"",
                email:"",
                password:"",
            }
        },
        created(){
        },
        watch: {
          
        },
        methods: {
            backgo(){
                var router = this.$router;
                router.go(-1);
            }
        }
    }
</script>