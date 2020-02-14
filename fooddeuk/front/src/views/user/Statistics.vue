<template>
    <div class="user" id="login">
        <v-app>
        <div class="header" style="width:100%; height:40px; background-color:white;">
            <div style="float:left;">
            <button v-on:click="goBack">
                <img src="../../assets/images/backIcon.png" style="width:35px;">
            </button>
            </div>
            <p style="vertical-align: middle;padding: 8px 5px;float:left;">통계</p>
        </div>
            <div class="wrapC" style="height:100%;">
                <div style="height:15%; margin-top:5%;">
                    <div class="countList">게시글 수 : {{allCountList.post_count}}</div>
                    <div class="countList">스크랩 횟수 : {{allCountList.scrap_count}}</div>
                    <div class="countList">좋아요 획득 : {{allCountList.like_count}}</div>
                    <div class="countList">좋아요 누름 : {{allCountList.likeClick_count}}</div>
                    <div class="countList">받은 댓글 : {{allCountList.comment_count}}</div>
                    <div class="countList">작성한 댓글 : {{allCountList.commentWrite_count}}</div>

                    <!-- <v-btn class="ma-2" outlined color="orange" samll @click="getPostGraph">게시글 수 : {{allCountList.post_count}}</v-btn>
                    <v-btn class="ma-2" outlined color="indigo" samll @click="getScrapGraph">스크랩 횟수 : {{allCountList.scrap_count}}</v-btn>
                    <v-btn class="ma-2" outlined color="indigo" samll @click="getLikeGraph">좋아요 획득 : {{allCountList.like_count}}</v-btn>
                    <v-btn class="ma-2" outlined color="indigo" samll @click="getLikeClickGraph">좋아요 누름 : {{allCountList.likeClick_count}}</v-btn>
                    <v-btn class="ma-2" outlined color="indigo" samll @click="getCommentGraph">받은 댓글 : {{allCountList.comment_count}}</v-btn>
                    <v-btn class="ma-2" outlined color="indigo" samll @click="getCommentWriteGraph">작성한 댓글 : {{allCountList.commentWrite_count}}</v-btn> -->
                </div>

                <br style="clear : both;">
                <hr style="margin-top:3%; margin-bottom:3%">

                <div style="height:10%;">
                    <v-select
                    v-model="selectedYear"
                    :items="years"
                    label="YEAR"
                    solo style="width:48%; float:left; margin:1%;"
                    ></v-select>

                    <v-select
                    v-model="selectedMonth"
                    :items="months"
                    label="MONTH"
                    solo style="width:48%; float:left; margin:1%;"
                    ></v-select>
                </div>
                <br>
                <div id='chart' style="width:100%;">
                    <div v-if="message!=null" style="text-align:center; font-size:large;">{{message}}</div>
                    <GChart v-if="isChart" type="BarChart" :data="chartData" :options="chartOptions"></GChart>
                </div>
            </div>
        </v-app>
    </div>
</template>

<script>
    import '../../assets/css/style.scss'
    import '../../assets/css/user.scss'
    import {mapState} from 'vuex';
    import http from '../../../http-common'
    import moment from 'moment'
    import { GChart } from 'vue-google-charts'

    export default {
        components: {
            GChart
        },
        created() {
            this.component = this;
            if(this.$store.state.userinfo!=null){
                this.nickname = this.$store.state.userinfo.nickName;
            }
            this.getUserByNickname(this.nickname);
        },
        watch: {
            selectedYear : function(v) {
                if(this.selectedYear!==0 && this.selectedMonth===0) {
                    this.message = "월을 선택해주세요"
                }
                else if(this.selectedYear!==0 && this.selectedMonth!==0) {
                    this.message = null;
                }
            },
            selectedMonth : function(v) {
                console.log(this.selectedMonth)
                
                if(this.selectedYear===0) {
                    this.message = "년도를 선택해주세요"
                }
                else if(this.selectedYear!==0 && this.selectedMonth!==0) {
                    this.message = null;
                    this.getGraphData();
                }
            },
        },
        methods: {
            goBack() {
                this.$router.go(-1);
            },
            getUserByNickname(nick) {
                let form = new FormData()
                form.append('nickname', nick)
                http.get("/user/userinfo/{nickname}?nickname="+nick)
                .then(Response => {
                    this.num = Response.data.num;
                    this.email = Response.data.email;
                    this.signUpDate = Response.data.date;
                    this.setYearTerm();
                    this.getAllCount();
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            getAllCount() {
                http.get("/stats/{num}?num="+ this.num)
                .then(Response => {
                    // console.log(Response.data.object)
                    this.allCountList = Response.data.object;
                })
                .catch(Error => {
                    console.log(Error)
                })
            },
            setYearTerm() {
                this.years = [];
                
                for (let year = moment(this.signUpDate).year(); year <= moment().year(); year++) {
                    this.years.push(year);
                }
            },
            getGraphData() {
                http.get("/stats/date/{num}?num="+ this.num + "&year="+this.selectedYear + "&month=" + this.selectedMonth)
                .then(Response => {
                    var res = Response.data.object;
                    console.log(res)
                    this.chartData = [
                        ['title', 'count'],
                        ['게시글 수', res.post_count],
                        ['스크랩 횟수', res.scrap_count],
                        ['좋아요 획득', res.like_count],
                        ['좋아요 누름', res.likeClick_count],
                        ['받은 댓글', res.comment_count],
                        ['작성한 댓글', res.commentWrite_count]
                    ]
                    this.isChart = true;

                    return true;
                })
                .catch(Error => {
                    console.log(Error)
                })
                return true;
            },
        },
        data: () => {
            return {
                num : null,
                signUpDate : null,
                email : '',
                nickname : '',
                chartData : [
                    // ['Year', 'Sales', 'Expenses', 'Profit'],
                    // ['2014', 1000, 400, 200],
                    // ['2015', 1170, 460, 250],
                    // ['2016', 660, 1120, 300],
                    // ['2017', 1030, 540, 350]
                ],
                chartOptions : {
                    width :'100%',
                    legend: { position: "top" },
                },
                allCountList : [],
                selectedYear : 0,
                selectedMonth : 0,
                isChart : false,
                years: [],
                months : ['1','2','3','4','5','6','7','8','9','10','11','12'],
                message : '년도와 월을 선택해주세요.',
            }
        },
        computed : {
            ...mapState(['userinfo']),
        },
    }
</script>